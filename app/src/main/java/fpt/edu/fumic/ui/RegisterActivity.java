package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import fpt.edu.fumic.R;
import fpt.edu.fumic.dao.UserDAO;
import fpt.edu.fumic.utils.DateConverter;
import fpt.edu.fumic.utils.LoadingDialog;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout tilFullname, tilEmail, tilPhone, tilUsername, tilPassword, tilRepassword;
    TextInputEditText tieDate;
    RadioGroup rdgGender;
    TextView tvLogin;
    ImageView ivBack;
    Button btRegister;
    IntentFilter intentFilter;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initActivity();
        userDAO = new UserDAO(this);

        ivBack.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tieDate.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    private void initActivity(){
        tilFullname = findViewById(R.id.til_fullname);
        tilEmail = findViewById(R.id.til_email);
        tilPhone = findViewById(R.id.til_phone_number);
        tilUsername = findViewById(R.id.til_username);
        tilPassword = findViewById(R.id.til_password);
        tilRepassword = findViewById(R.id.til_re_password);
        tieDate = findViewById(R.id.tie_dob);
        rdgGender = findViewById(R.id.radio_group_gender);
        btRegister = findViewById(R.id.bt_register);
    }
    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        // get current date
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        // Show dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(y, m, d);
                tieDate.setText(DateConverter.dateToString(calendar.getTime()));
            }
        }, year, month, date);

        datePickerDialog.show();
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