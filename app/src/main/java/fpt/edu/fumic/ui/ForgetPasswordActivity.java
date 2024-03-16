package fpt.edu.fumic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.utils.LoadingDialog;
import fpt.edu.fumic.utils.MyToast;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String ACTION_RECOVERY = "action recovery password"
            , STATUS_RECOVERY = "status login"
            , STATUS_RECOVERY_SUCCESS = "login successful"
            , STATUS_RECOVERY_FAILED = "recovery password failed"
            , STATUS_RECOVERY_ERROR = "recovery password error"
            , EMPTY_FIELD_WARNING = "This field can not empty!";
    TextInputLayout til_username, til_password, til_rePassword;
    ImageView tv_ivBack;
    Button bt_recovery_password;
    LoadingDialog loadingDialog;
    UserRepository userRepository;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //Init phase
        initActivity();
        //Connect DAO database
        userRepository = new UserRepository(getApplicationContext());
        //Setup button
        tv_ivBack.setOnClickListener(this);
        bt_recovery_password.setOnClickListener(this);
    }

    void initActivity() {
        //Init elements
        til_username = findViewById(R.id.til_username);
        til_password = findViewById(R.id.til_password);
        til_rePassword = findViewById(R.id.til_re_password);
        tv_ivBack = findViewById(R.id.ivBack);
        bt_recovery_password = findViewById(R.id.bt_recovery_password);
        //Init loading dialog
        loadingDialog = new LoadingDialog(ForgetPasswordActivity.this);
        //Init intent filter
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_RECOVERY);
    }


    private final BroadcastReceiver forgetPasswordBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String statusLogin = intent.getStringExtra(STATUS_RECOVERY);
            switch (Objects.requireNonNull(statusLogin)) {
                case STATUS_RECOVERY_SUCCESS:
                    MyToast.successfulToast(ForgetPasswordActivity.this, "Password changed successfully, login now!");
                    loadingDialog.stopLoadingDialog();
                    break;
                case STATUS_RECOVERY_FAILED:
                    MyToast.warningToast(ForgetPasswordActivity.this, "Recovery failed! Check login information and try again!");
                    loadingDialog.stopLoadingDialog();
                    break;
                case STATUS_RECOVERY_ERROR:
                    MyToast.errorToast(ForgetPasswordActivity.this, "All Field must be filled!");
                    loadingDialog.stopLoadingDialog();
                    break;
            }
        }
    };
    private void sendRecoveryStatusToBoardcast(String statusRecoveryStr) {
        Intent recoveryIntent = new Intent();
        recoveryIntent.setAction(ACTION_RECOVERY);
        recoveryIntent.putExtra(STATUS_RECOVERY, statusRecoveryStr);
        sendBroadcast(recoveryIntent);
    }

    private void forgetPasswordSystem() {
        loadingDialog.startLoadingDialog();
        String username = getText(til_username);
        String password = getText(til_password);
        String rePasword = getText(til_rePassword);
        String statusRecovery = STATUS_RECOVERY_ERROR;

        if(username.isEmpty() || password.isEmpty()){
            if(username.isEmpty()){
                til_username.setError(EMPTY_FIELD_WARNING);
            }
            if (password.isEmpty()) {
                til_password.setError(EMPTY_FIELD_WARNING);
            }
            if (rePasword.isEmpty()) {
                til_rePassword.setError(EMPTY_FIELD_WARNING);
            }
        } else {
            UserEntity user = userRepository.getUserById(username);
            if(user != null){
                if(password.equals(rePasword)) {
                    statusRecovery = STATUS_RECOVERY_SUCCESS;
                    user.setPassword(password);
                    userRepository.updateUser(user);
                    toLoginActivity();
                } else {
                    statusRecovery = STATUS_RECOVERY_FAILED;
                    til_rePassword.setError("Password not match!");
                }
            } else {
                statusRecovery = STATUS_RECOVERY_FAILED;
                til_username.setError("No user found!");
            }
        }
        sendRecoveryStatusToBoardcast(statusRecovery);
    }
    private void toLoginActivity(){
        Intent intentLoginActivity = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(intentLoginActivity);
        finish();
    }
    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bt_recovery_password){
            forgetPasswordSystem();
        } else if (view.getId() == R.id.ivBack) {
            finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(forgetPasswordBroadcastReceiver, intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(forgetPasswordBroadcastReceiver);
    }
}