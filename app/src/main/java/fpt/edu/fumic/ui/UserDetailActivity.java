package fpt.edu.fumic.ui;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.Toast;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.database.model.User;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.utils.DateConverterStrDate;
import fpt.edu.fumic.utils.UserInformation;

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private UserEntity userEntity;
    private boolean isChange;
    private TextView tvNameTitle, tvName, tvEmail, tvDob, tvPhone, tvRole, tvGender, viewInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        initActivity();
        Intent intent = getIntent();
        isChange = false;
        if (intent != null) {
            if (intent.hasExtra("userEntity")) {
                userEntity = (UserEntity) intent.getSerializableExtra("userEntity");
                if (userEntity != null) {
                    loadView();
                } else {
                    Toast.makeText(this, "Error: User not found or not authorized", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                // If no specific user is passed, load the current user
                userEntity = UserInformation.getInstance().getUser();
                if (userEntity != null) {
                    loadView();
                } else {
                    Toast.makeText(this, "Error: Current user not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        } else {
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
            finish();
        }
        ivBack.setOnClickListener(this);
        viewInformation.setOnClickListener(this);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleFinish();
            }
        });


    }

    private void loadUser() {
        userEntity = UserInformation.getInstance().getUser();
        loadView();
    }

    private void loadView() {
        tvName.setText(userEntity.getName());
        tvNameTitle.setText(userEntity.getName());
        tvEmail.setText(userEntity.getEmail());
        tvPhone.setText(userEntity.getPhone());
        tvDob.setText(DateConverterStrDate.dateToString(userEntity.getDob()));

        switch (userEntity.getGender()) {
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
        switch (userEntity.getRole()) {
            case 0: {
                tvRole.setText("Admin");
                break;
            }
            case 1: {
                tvRole.setText("Mod");
                break;
            }
            default: {
                tvRole.setText("Normal");
                break;

            }
        }

    }

    private void initActivity() {

        ivBack = findViewById(R.id.ivBack);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvNameTitle = findViewById(R.id.tvNameTitle);
        tvDob = findViewById(R.id.tvDob);
        tvPhone = findViewById(R.id.tvPhone);
        tvRole = findViewById(R.id.tvRole);
        tvGender = findViewById(R.id.tvGender);
        viewInformation = findViewById(R.id.viewInformation);
    }
    ActivityResultLauncher<Intent> mStartForStoryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    isChange = true;
                    loadUser();
                }
            });

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBack) {
            handleFinish();
        } else if (view.getId() == R.id.viewInformation) {
            Intent intent = new Intent(UserDetailActivity.this, EditProfileActivity.class);
            mStartForStoryResult.launch(intent);
        }
    }

    private void handleFinish() {
        if (isChange) {
            isChange = false;
            Intent intent = new Intent();
            setResult(android.app.Activity.RESULT_OK, intent);
        }
        finish();
    }


}
