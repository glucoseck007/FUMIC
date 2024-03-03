package fpt.edu.fumic.ui;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.utils.DateConverterStrDate;

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private UserRepository userRepository;
    private UserEntity userEntity;
    private boolean isChange;
    private TextView tvNameTitle, tvName, tvEmail, tvDob, tvPhone, tvRole, tvGender, viewInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        initActivity();
        userRepository = new UserRepository(this);
        loadUser();

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
        String uid = getIntent().getStringExtra("uid");
        if (uid == null || uid.isEmpty()) {
            return;
        }
        userEntity = userRepository.getUserById(uid);
        if (userEntity == null) {
            return;
        }
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
            intent.putExtra("uid", userEntity.getId());
            mStartForStoryResult.launch(intent);
        }
    }

    private void handleFinish() {
        if (isChange) {
            Intent intent = new Intent();
            setResult(android.app.Activity.RESULT_OK, intent);
        }
        finish();
    }


}
