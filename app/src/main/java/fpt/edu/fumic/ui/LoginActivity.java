package fpt.edu.fumic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.fumic.R;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout til_username, til_password;
    CheckBox check_box_remember;
    Button bt_login, bt_register;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Connect elements

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Register page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(){
        check_box_remember = findViewById(R.id.chkbox_remember_me);
        bt_register = findViewById(R.id.bt_register);
        til_username = findViewById(R.id.til_username);
        til_password = findViewById(R.id.til_password);
    }

    private void initIntentFilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("action login");
    }
    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }
}