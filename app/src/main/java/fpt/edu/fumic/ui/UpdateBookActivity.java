package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.DataGenerator;
import fpt.edu.fumic.database.converter.ImageToByte;
import fpt.edu.fumic.database.entity.AuthorEntity;
import fpt.edu.fumic.database.entity.BookEntity;
import fpt.edu.fumic.database.entity.ChapterEntity;
import fpt.edu.fumic.database.entity.OwnEntity;
import fpt.edu.fumic.repository.BookRepository;
import fpt.edu.fumic.utils.MyToast;

public class UpdateBookActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_FILE = 2;
    int flag = 0;
    boolean pickImageStatus = true;
    int noti = 0, defaultPosition = 0;
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
        setContentView(R.layout.activity_update_book);

        initView();
        String title = Objects.requireNonNull(getIntent().getExtras()).getString("title");
        repository = new BookRepository(getApplicationContext());

        BookEntity book = repository.getBookByTitle(title);
        String author = repository.getAuthorById(repository.getAuthorIdWhoOwn(book.getId()));

        tilTitle.getEditText().setText(book.getTitle());
        tilAuthor.getEditText().setText(author);
        tilDescription.getEditText().setText(book.getDescription());
        defaultPosition = book.getCategoryId() - 1;
        ivUpload.setImageBitmap(ImageToByte.getBitmapFromByteArray(book.getImage()));

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
        spinnerCategory.setSelection(defaultPosition);
    }

    private void updateBook() {
        if (!pickImageStatus) {
            MyToast.errorToast(UpdateBookActivity.this, "Please choose an image");
        } else {
            String title = Objects.requireNonNull(tilTitle.getEditText()).getText().toString().trim();
            String author = Objects.requireNonNull(tilAuthor.getEditText()).getText().toString().trim();
            String description = Objects.requireNonNull(tilDescription.getEditText()).getText().toString().trim();
            String content = Objects.requireNonNull(btnAddContent.getText().toString());
            contentURI = Uri.parse(content);

            if (title.isEmpty() || author.isEmpty() || description.isEmpty()) {
                MyToast.errorToast(UpdateBookActivity.this, "Please fill in all attributes");
            } else {
                new UpdateBookActivity.UpdateBookTask().execute(title, author, description, content);
            }
        }
    }

    private class UpdateBookTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return bookTableHandler(params[0], params[1], params[2], params[3]) && chapterContentHandler(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                MyToast.successfulToast(UpdateBookActivity.this, "Update successfully!");
                onBackPressed();
            } else {
                MyToast.errorToast(UpdateBookActivity.this, "Add unsuccessful, cannot read data from file!");
            }
        }
    }

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
        defaultPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBack) {
            finish();
        } else if (view.getId() == R.id.btnAdd) {
            updateBook();
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
                    MyToast.errorToast(UpdateBookActivity.this, "Please enter the URL again to load the image");
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
            repository.insertAuthor(repository.getLatestAuthorId() + 1, name);
            flag = 1;
            return repository.getLatestAuthorId() + 1;
        }
    }

    private int getExistBook(String title) {
        return repository.getExistBook(title);
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/csv");  // You can restrict the type of files you want to choose here
        startActivityForResult(intent, PICK_FILE);
    }

    private boolean bookTableHandler(String title, String author, String description, String contentURI) {
        int authorId = getExistAuthor(author);
        BookEntity book = repository.getBookByTitle(title);
        int bookId = getExistBook(title.toUpperCase());
        OwnEntity own = new OwnEntity();
        book.setTitle(title);
        book.setDescription(description);
        book.setContentURI(contentURI);
        book.setCategoryId(defaultPosition + 1);

        own.setAuthorId(authorId);
        own.setBookId(bookId);
        if (flag == 1) repository.insertRelationship(authorId, bookId);
        boolean isUpdatedBook = repository.updateBook(book) > 0;
        boolean isUpdatedOwn = repository.updateOwn(own) > 0;
        return isUpdatedBook || isUpdatedOwn;
    }

    private boolean chapterContentHandler(String title) {
        if (contentURI.toString().isEmpty()) return true;
        List<ChapterEntity> list = DataGenerator.readContent(getApplicationContext(), contentURI, getExistBook(title.toUpperCase()));
        return repository.insertChapterContent(list) > 0;
    }

}