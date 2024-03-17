package fpt.edu.fumic.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.fumic.MainActivity;
import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.utils.LoadingDialog;
import fpt.edu.fumic.utils.MyToast;
import fpt.edu.fumic.utils.UserInformation;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ACTION_LOGIN = "action login"
            , STATUS_LOGIN = "status login"
            , STATUS_LOGIN_SUCCESS = "login successful"
            , STATUS_LOGIN_FAILED = "login failed"
            , STATUS_LOGIN_ERROR = "login error"
            , KEY_USER = "keyUser";
    TextInputLayout til_username, til_password;
    CheckBox check_box_remember;
    Button bt_login;
    TextView tv_Register, tv_forget_password;
    LoadingDialog loadingDialog;
    IntentFilter intentFilter;
    UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Connect elements
        initActivity();
        //Init intent filter
        initIntentFilter();
        //Connect DAO database
        userRepository = new UserRepository(getApplicationContext());
        //Setup function buttons of activity
        bt_login.setOnClickListener(this);
        tv_Register.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        loadingDialog = new LoadingDialog(LoginActivity.this);
        //Get login saved information
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        if(username != null && password != null){
            Objects.requireNonNull(til_username.getEditText()).setText(username);
            Objects.requireNonNull(til_password.getEditText()).setText(password);
            check_box_remember.setChecked(true);
        }else {
            check_box_remember.setChecked(false);
        }
    }

    private void initActivity(){
        til_username = findViewById(R.id.til_username);
        til_password = findViewById(R.id.til_password);
        check_box_remember = findViewById(R.id.chkbox_remember_me);
        bt_login = findViewById(R.id.bt_login);
        tv_Register = findViewById(R.id.tv_register);
        tv_forget_password = findViewById(R.id.tv_forget_password);
    }
    private void setPreferencesMemory(){
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", getText(til_username));
        editor.putString("password", getText(til_password));
        editor.putBoolean("remember_me", true); // Lưu trạng thái "Remember Me"
        editor.apply();
    }
    private void initIntentFilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_LOGIN);
    }
    private void sendLoginStatusToBroadcast(String statusLoginStr) {
        Intent loginIntent = new Intent();
        loginIntent.setAction(ACTION_LOGIN);
        loginIntent.putExtra(STATUS_LOGIN, statusLoginStr);
        sendBroadcast(loginIntent);
    }
    private final BroadcastReceiver loginBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String statusLogin = intent.getStringExtra(STATUS_LOGIN);
            switch (Objects.requireNonNull(statusLogin)) {
                case STATUS_LOGIN_SUCCESS:
                    MyToast.successfulToast(LoginActivity.this, "Login Successfully!");
                    loadingDialog.stopLoadingDialog();
                    break;
                case STATUS_LOGIN_FAILED:
                    MyToast.warningToast(LoginActivity.this, "Login Failed! Check login information and try again!");
                    loadingDialog.stopLoadingDialog();
                    break;
                case STATUS_LOGIN_ERROR:
                    MyToast.errorToast(LoginActivity.this, "All Field must be filled!");
                    loadingDialog.stopLoadingDialog();
                    break;
            }
        }
    };

    private void loginSystem() {
        loadingDialog.startLoadingDialog();
        String username = getText(til_username);
        String password = getText(til_password);
        String statusLogin = STATUS_LOGIN_ERROR;

        if(!username.isEmpty() && !password.isEmpty()){
            UserEntity user = userRepository.getUserById(username);
            if(user != null){
                if (user.getPassword().equals(password)){
                    statusLogin = STATUS_LOGIN_SUCCESS;
                    UserInformation.getInstance().setUser(user);
                    toMainPage();
                    if(check_box_remember.isChecked()){
                        setPreferencesMemory();
                    }else {
                        SharedPreferences sharedPreferences = getSharedPreferences("login_info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                    }
                } else {
                    statusLogin = STATUS_LOGIN_FAILED;
                }
            }else {
                statusLogin = STATUS_LOGIN_FAILED;
            }
        }
        sendLoginStatusToBroadcast(statusLogin);
    }
    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }
    private void toMainPage(){
        Intent intentMainPage = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intentMainPage);
        finish();
    }
    private void toRegisterActivity() {
        Intent intentRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intentRegisterActivity);
    }

    private void toForgetPasswordActivity() {
        Intent intentForgetPassword = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intentForgetPassword);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bt_login){
            loginSystem();
        } else if (view.getId() == R.id.tv_register) {
            toRegisterActivity();
        } else if (view.getId() == R.id.tv_forget_password) {
            toForgetPasswordActivity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(loginBroadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(loginBroadcastReceiver);
    }
}