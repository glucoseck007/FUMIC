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

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.UserDAO;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.utils.LoadingDialog;
import fpt.edu.fumic.utils.MyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ACTION_LOGIN = "action login"
            , STATUS_LOGIN = "status login"
            , STATUS_LOGIN_SUCCESS = "login successful"
            , STATUS_LOGIN_FAILED = "login failed"
            , STATUS_LOGIN_ERROR = "login error"
            , KEY_LOGIN_USER = "keyUser";
    TextInputLayout til_username, til_password;
    CheckBox check_box_remember;
    Button bt_login;
    TextView tv_Register;
    LoadingDialog loadingDialog;
    IntentFilter intentFilter;
    UserRepository userDAO;
    boolean rememberMe = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Connect elements
        initActivity();
        //Init intent filter
        initIntentFilter();
        //Connect DAO database

        //Setup function buttons of activity
        bt_login.setOnClickListener(this);
        tv_Register.setOnClickListener(this);

        getPreferencesMemory();

        loadingDialog = new LoadingDialog(LoginActivity.this);
    }

    private void initActivity(){
        til_username = findViewById(R.id.til_username);
        til_password = findViewById(R.id.til_password);
        check_box_remember = findViewById(R.id.chkbox_remember_me);
        bt_login = findViewById(R.id.bt_login);
        tv_Register = findViewById(R.id.tv_register);
    }

    private void initIntentFilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_LOGIN);
    }


    private void setPreferencesMemory(){
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", getText(til_username));
        editor.putString("password", getText(til_password));
        editor.putBoolean("remember_me", true); // Lưu trạng thái "Remember Me"
        editor.apply();
    }
    private void getPreferencesMemory(){
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", null);
        String savedPassword = sharedPreferences.getString("password", null);
        if(savedUsername != null && savedPassword != null){
            Objects.requireNonNull(til_username.getEditText()).setText(savedUsername);
            Objects.requireNonNull(til_password.getEditText()).setText(savedPassword);
            check_box_remember.setChecked(true);
            loginSystem();
        }
    }
    private void sendLoginStatusToBoardcast(String statusLoginStr) {
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
        if(check_box_remember.isChecked()){
            setPreferencesMemory();
        }else {
            SharedPreferences sharedPreferences = getSharedPreferences("login_info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
        if(!username.isEmpty() && !password.isEmpty()){
            UserEntity user = userDAO.getUserById(username);
            if(user != null){
                if (user.getPassword().equals(password)){
                    statusLogin = STATUS_LOGIN_SUCCESS;
                    toUserProfileActivity(username);
                } else {
                    statusLogin = STATUS_LOGIN_FAILED;
                }
            }else {
                statusLogin = STATUS_LOGIN_FAILED;
            }
        }
        sendLoginStatusToBoardcast(statusLogin);
    }
    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }
    private void toUserProfileActivity(String username){
        Intent intentUserProfile = new Intent(LoginActivity.this, UserProfileActivity.class);
        intentUserProfile.putExtra(KEY_LOGIN_USER, username);
        startActivity(intentUserProfile);
    }
    private void toRegisterActivity() {
        Intent intentRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intentRegisterActivity);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bt_login){
            loginSystem();
        } else if (view.getId() == R.id.tv_register) {
            toRegisterActivity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(loginBroadcastReceiver, intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(loginBroadcastReceiver);
    }
}