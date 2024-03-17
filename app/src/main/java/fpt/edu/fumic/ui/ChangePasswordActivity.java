package fpt.edu.fumic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Objects;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.utils.LoadingDialog;
import fpt.edu.fumic.utils.MyToast;
import fpt.edu.fumic.utils.UserInformation;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PASSWORD_ERROR = "Incorrect password", EMPTY_FIELD_WARNING = "This field can not empty!";
    private TextInputLayout tilPassword, tilNewPassword, tilRePassword;

    private ImageView ivBack;
    private Button btSave;
    private UserRepository userRepository;
    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initActivity();

        //Init user repository
        userRepository = new UserRepository(this);
        userEntity = UserInformation.getInstance().getUser();
        if (userEntity == null) {
            return;
        }


        ivBack.setOnClickListener(this);
        btSave.setOnClickListener(this);

    }

    private void updateProfile() {
        String oldPassword = getText(tilPassword);
        String newPassword = getText(tilNewPassword);
        String rePassword = getText(tilRePassword);

        tilPassword.setError(null);
        tilNewPassword.setError(null);
        tilRePassword.setError(null);

        if (oldPassword.isEmpty() || newPassword.isEmpty() || rePassword.isEmpty()) {
            if (oldPassword.isEmpty()) {
                tilPassword.setError(EMPTY_FIELD_WARNING);
            }

            if (newPassword.isEmpty()) {
                tilNewPassword.setError(EMPTY_FIELD_WARNING);
            }
            if (rePassword.isEmpty()) {
                tilRePassword.setError(EMPTY_FIELD_WARNING);
            }

            MyToast.errorToast(ChangePasswordActivity.this, "All required field must be filled!");
        } else if (!oldPassword.equals(userEntity.getPassword())) {
            tilPassword.setError(PASSWORD_ERROR);

            MyToast.errorToast(ChangePasswordActivity.this, "Incorrect password");
        } else if (!newPassword.equals(rePassword)) {

            tilRePassword.setError(PASSWORD_ERROR);

            MyToast.errorToast(ChangePasswordActivity.this, "Incorrect password");
        } else {
            userEntity.setPassword(newPassword);
            userRepository.updateUser(userEntity);

            MyToast.successfulToast(ChangePasswordActivity.this, "Password changed successfully!");
            Intent intent = new Intent();
            setResult(android.app.Activity.RESULT_OK, intent);
            finish();
        }


    }

    private void initActivity() {
        btSave = findViewById(R.id.bt_save);
        ivBack = findViewById(R.id.ivBack);
        tilPassword = findViewById(R.id.til_old_password);
        tilNewPassword = findViewById(R.id.til_new_password);
        tilRePassword = findViewById(R.id.til_re_password);
    }

    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }

    @NonNull
    private void setText(TextInputLayout textInputLayout, String text) {
        Objects.requireNonNull(textInputLayout.getEditText()).setText(text);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBack) {
            finish();
        } else if (view.getId() == R.id.bt_save) {
            updateProfile();
        }
    }
}