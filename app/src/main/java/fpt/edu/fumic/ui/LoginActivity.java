package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import fpt.edu.fumic.R;

public class LoginActivity extends AppCompatActivity {
    EditText edit_username, edit_password;
    CheckBox check_box_remember;
    Button bt_login, bt_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Connect elements
        edit_username = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        check_box_remember = findViewById(R.id.check_box_remember);
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edit_username.getText().toString();
                String password = edit_password.getText().toString();
                if (username.length() == 0) {
                    edit_username.setError("This field can not empty!");
                } else if(password.length() == 0) {
                    edit_password.setError("This field can not empty!");
                }else {
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_register);
                Toast.makeText(LoginActivity.this, "Register page", Toast.LENGTH_SHORT).show();
            }
        });
    }
}