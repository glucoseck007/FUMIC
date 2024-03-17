package fpt.edu.fumic.ui;

import static fpt.edu.fumic.R.id.btnAdd;
import static fpt.edu.fumic.database.DataGenerator.StringToDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.DataGenerator;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.entity.AuthorEntity;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.database.entity.OwnEntity;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.utils.MyToast;
import fpt.edu.fumic.utils.UserInformation;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_FILE = 2;
    boolean pickImageStatus = false;
    int noti = 0;
    ImageView ivBack, ivUpload;
    TextInputLayout tilTitle, tilAuthor, tilDescription;
    Button btnAdd, btnAddContent;
    Spinner spinnerCategory;
    BookRepository repository;
    String selectedCategory;
    Uri contentURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initView();

        repository = new BookRepository(getApplicationContext());

        btnAddContent.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivUpload.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        spinnerCategory.setOnItemSelectedListener(this);
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        ivUpload = findViewById(R.id.ivUpload);
        tilTitle = findViewById(R.id.til_Title);
        tilAuthor = findViewById(R.id.til_Author);
        tilDescription = findViewById(R.id.til_Description);
        btnAddContent = findViewById(R.id.btnAddContent);
        btnAdd = findViewById(R.id.btnAdd);
        spinnerCategory = findViewById(R.id.spinner_Category);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Category,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
    }

    private void addBook() {
        if (!pickImageStatus) {
            MyToast.errorToast(AddBookActivity.this, "Please choose an image");
        } else {
            String title = Objects.requireNonNull(tilTitle.getEditText()).getText().toString().trim();
            String author = Objects.requireNonNull(tilAuthor.getEditText()).getText().toString().trim();
            String description = Objects.requireNonNull(tilDescription.getEditText()).getText().toString().trim();
            String content = Objects.requireNonNull(btnAddContent.getText().toString());
            contentURI = Uri.parse(content);

            if (title.isEmpty() || author.isEmpty() || description.isEmpty()) {
                MyToast.errorToast(AddBookActivity.this, "Please fill in all attributes");
            } else {
                new AddBookTask().execute(title, author, description, content);
            }
        }
    }

    private class AddBookTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            if (bookTableHandler(params[0], params[1], params[2], params[3]) && chapterContentHandler(params[0])) {
                return true;
            } else {
                repository.deleteBook(getExistBook(params[0].toUpperCase()));
                repository.deleteAuthor(getExistAuthor(params[1].toUpperCase()));
                noti = 1;
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                MyToast.successfulToast(AddBookActivity.this, "Added successfully!");
                tilTitle.getEditText().setText("");
                tilAuthor.getEditText().setText("");
                tilDescription.getEditText().setText("");
                ivUpload.setImageResource(R.drawable.ic_add_photo);
                pickImageStatus = false;
            } else {
                if (noti == 0)
                    MyToast.errorToast(AddBookActivity.this, "Add unsuccessfully, book exist!");
                if (noti == 1)
                    MyToast.errorToast(AddBookActivity.this, "Add unsuccessfully, cannot read data from file!");
                if (noti == 2)
                    MyToast.successfulToast(AddBookActivity.this, "Request successfully, waiting for confirm!");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                Uri uriImage = data.getData();
                try {
                    assert uriImage != null;
                    InputStream stream = getApplicationContext().getContentResolver().openInputStream(uriImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    pickImageStatus = true;
                    ivUpload.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            if (requestCode == PICK_FILE) {
                Uri fileUri = data.getData();
                assert fileUri != null;
                btnAddContent.setText(fileUri.toString());

            }
        }
    }

    private void showUploadOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Image");
        builder.setItems(new CharSequence[]{"Input URL", "Choose from Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Input URL option clicked
                        showInputUrlDialog();
                        break;
                    case 1:
                        // Choose from Gallery option clicked
                        // Start gallery intent
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_IMAGE);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBack) {
            finish();
        } else if (view.getId() == R.id.btnAdd) {
            addBook();
        } else if (view.getId() == R.id.ivUpload) {
            showUploadOptionsDialog();
        } else if (view.getId() == R.id.btnAddContent) {
            openFilePicker();
        }
    }

    private void showInputUrlDialog() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image URL");
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = input.getText().toString().trim();
                if (!TextUtils.isEmpty(url)) {
                    Picasso.get().load(url).into(ivUpload);
                    pickImageStatus = true;
                } else {
                    MyToast.errorToast(AddBookActivity.this, "Please enter the URL again to load the image");
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private int getExistAuthor(String name) {
        Integer id = repository.getExistAuthor(name);
        if (id != null) {
            return id;
        } else {
            return repository.getLatestAuthorId() + 1;
        }
    }

    private int getExistBook(String title) {
        Integer id = repository.getExistBook(title);
        if (id != null) {
            return id;
        } else {
            return repository.getLatestBookId() + 1;
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");  // You can restrict the type of files you want to choose here
        startActivityForResult(intent, PICK_FILE);
    }

    private boolean bookTableHandler(String title, String author, String description, String contentURI) {
        int bookId = getExistBook(title.toUpperCase());
        int authorId = getExistAuthor(author.toUpperCase());
        int categoryId = repository.getCategoryId(selectedCategory);
        int relationship = repository.getRelationship(bookId, authorId);
        if (relationship > 0) {
            return false;
        } else {
            bookId = repository.getLatestBookId() + 1;
        }

        OwnEntity ownEntity = new OwnEntity();
        ownEntity.setBookId(bookId);
        ownEntity.setAuthorId(authorId);

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(authorId);
        authorEntity.setName(author.toUpperCase());

        BookEntity book = new BookEntity();
        book.setId(bookId);
        book.setTitle(title.toUpperCase());
        book.setDescription(description);
        book.setCategoryId(categoryId);
        book.setDateUpload(Date.from(Instant.now()));
        if (UserInformation.getInstance().getUser().getRole() == 0 || UserInformation.getInstance().getUser().getRole() == 1) {
            book.setStatus(1);
        }
        if (UserInformation.getInstance().getUser().getRole() == 2) {
            book.setStatus(2);
            noti = 2;
        }
        book.setImage(ImageToByte.imageViewToByte(ivUpload, 210, 297));
        book.setContentURI(contentURI);

        boolean bookInserted = repository.createBook(book) != -1;
        boolean authorInserted = repository.insertAuthor(authorId, author.toUpperCase()) != -1;
        boolean relationshipInserted = repository.insertRelationship(authorId, bookId) != -1;

        return bookInserted && authorInserted && relationshipInserted;
    }

    private boolean chapterContentHandler(String title) {
        List<ChapterEntity> list = DataGenerator.readContent(getApplicationContext(), contentURI, getExistBook(title.toUpperCase()));
        return repository.insertChapterContent(list) > 0;
    }

}