package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import fpt.edu.fumic.R;
import fpt.edu.fumic.dao.UserDAO;
import fpt.edu.fumic.utils.LoadingDialog;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout tilFullname, tilAge, tilEmail, tilPhone, tilUsername, tilPassword, tilRepassword;
    RadioGroup rdgGender;
    LoadingDialog loadingDialog;
    IntentFilter intentFilter;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void initActivity(){

    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {

    }
}