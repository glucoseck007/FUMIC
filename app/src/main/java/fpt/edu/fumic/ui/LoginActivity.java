package fpt.edu.fumic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.fumic.R;
import fpt.edu.fumic.dao.UserDAO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ACTION_LOGIN = "action login",
                        STATUS_LOGIN = "status login";
    TextInputLayout til_username, til_password;
    CheckBox check_box_remember;
    Button bt_login;
    TextView tv_Register;
    IntentFilter intentFilter;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Connect elements
        initActivity();
        //Connect DAO database
        userDAO = new UserDAO(this);
    }

    private final BroadcastReceiver loginBroadcastReceiver = new BroadcastReceiver() {
        String statusLogin = getIntent().getStringExtra(STATUS_LOGIN);
        @Override

        public void onReceive(Context context, Intent intent) {

        }
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
    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }
}