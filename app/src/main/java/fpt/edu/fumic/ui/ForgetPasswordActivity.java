package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import fpt.edu.fumic.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    TextInputLayout til_username, til_password, til_rePassword;
    TextView tv_ivBack;
    Button bt_recovery_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    void initActivity() {
        til_username = findViewById(R.id.til_username);
        til_password = findViewById(R.id.til_password);
        til_rePassword = findViewById(R.id.til_re_password);
        tv_ivBack = findViewById(R.id.ivBack);
        bt_recovery_password = findViewById(R.id.bt_recovery_password);
    }
}