package fpt.edu.fumic.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.database.model.User;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.utils.MyToast;
import fpt.edu.fumic.utils.UserInformation;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private View viewInformation, viewChangePassword, viewBrowseBooks,viewHistories,viewFavourite, viewLogout;
    private ImageView ivBack;
    private UserEntity userEntity;

    private TextView tvName, tvEmail;
    ActivityResultLauncher<Intent> mStartForProfileResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initActivity();
        userEntity = UserInformation.getInstance().getUser();
        viewInformation.setOnClickListener(this);
        viewChangePassword.setOnClickListener(this);
        viewBrowseBooks.setOnClickListener(this);
        viewHistories.setOnClickListener(this);
        viewFavourite.setOnClickListener(this);
        viewLogout.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        mStartForProfileResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK) {
                        loadUser();
                    }
                });
    }


    private void loadUser() {

        userEntity = UserInformation.getInstance().getUser();
        if (userEntity == null) {
            return;
        }
        loadView();
    }

    private void loadView() {
        tvName.setText(userEntity.getName());
        tvEmail.setText(userEntity.getEmail());

        if (userEntity.getRole() == 0 || userEntity.getRole() == 1) {
            viewBrowseBooks.setVisibility(View.VISIBLE);
        } else {
            viewBrowseBooks.setVisibility(View.GONE);
        }
    }

    private void initActivity() {

        viewInformation = findViewById(R.id.viewInformation);
        viewChangePassword = findViewById(R.id.viewChangePassword);
        viewBrowseBooks = findViewById(R.id.viewBrowseBooks);
        viewHistories = findViewById(R.id.viewHistories);
        viewFavourite = findViewById(R.id.viewFavourite);
        viewLogout = findViewById(R.id.viewLogout);
        ivBack = findViewById(R.id.ivBack);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
    }
    private void logout(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        UserInformation.getInstance().setUser(null);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBack) {
            finish();
        } else if (view.getId() == R.id.viewInformation) {
            Intent intent = new Intent(UserProfileActivity.this, UserDetailActivity.class);
            startActivity(intent);
            mStartForProfileResult.launch(intent);
        } else if (view.getId() == R.id.viewChangePassword) {
            Intent intent = new Intent(UserProfileActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.viewBrowseBooks) {
            startActivity(new Intent(UserProfileActivity.this, BrowseBookActivity.class));
        } else if (view.getId() == R.id.viewFavourite) {
            startActivity(new Intent(UserProfileActivity.this, FavouriteActivity.class));
        } else if (view.getId() == R.id.viewHistories) {
            startActivity(new Intent(UserProfileActivity.this, HistoriesActivity.class));
        } else if (view.getId() == R.id.viewLogout) {
            logout();
        }
    }
}