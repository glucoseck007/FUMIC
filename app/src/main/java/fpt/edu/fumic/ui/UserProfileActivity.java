package fpt.edu.fumic.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import fpt.edu.fumic.R;
import fpt.edu.fumic.dao.UserDAO;
import fpt.edu.fumic.database.FumicDB;
import fpt.edu.fumic.model.User;

public class UserProfileActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvName, tvPhone, tvEmail, tvAge, tvRole, tvGender;

    private MaterialButton btnEdit, btnChangePassword;

    private User user;

    private UserDAO userDAO ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initView();


        userDAO = new UserDAO(this);
        loadData();

        loadView();

        initEvent();


    }

    private void initEvent() {
        btnBack.setOnClickListener(v -> finish());
        btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, ChangePasswordActivity.class);
            intent.putExtra("uid",user.getId());
            mStartForStoryResult.launch(intent);
        });
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("uid",user.getId());
            mStartForStoryResult.launch(intent);
        });

    }

    ActivityResultLauncher<Intent> mStartForStoryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    loadData();
                    loadView();
                }
            });

    private void loadData() {
        user = userDAO.getUser("user123");
    }


    private void loadView() {
        tvName.setText(user.getName());
        tvPhone.setText(user.getPhone());
        tvAge.setText(String.valueOf(user.getAge()));
        tvEmail.setText(user.getEmail());


        switch (user.getRole()) {
            case 1: {
                tvRole.setText("Staff");
                break;
            }
            case 2: {
                tvRole.setText("Admin");
                break;
            }
            default: {
                tvGender.setText("Unknown");
                break;
            }
        }

        switch (user.getGender()) {
            case 0: {
                tvGender.setText("Male");
                break;
            }
            case 1: {
                tvGender.setText("Female");
                break;
            }
            default: {
                tvGender.setText("Unknown");
                break;
            }
        }
    }

    private void initView() {
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvAge = findViewById(R.id.tvAge);
        tvRole = findViewById(R.id.tvRole);
        tvGender = findViewById(R.id.tvGender);
        btnEdit = findViewById(R.id.btnEdit);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnBack = findViewById(R.id.btnBack);
    }
}