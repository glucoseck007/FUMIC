package fpt.edu.fumic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.AppDatabase;
import fpt.edu.fumic.database.dao.UserDAO;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.utils.DateConverterStrDate;
import fpt.edu.fumic.utils.LoadingDialog;
import fpt.edu.fumic.utils.MyToast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ACTION_REGISTER = "action register"
        , STATUS_REGISTER = "status register"
        , STATUS_REGISTER_SUCCESS = "register successful"
        , STATUS_REGISTER_FAILED = "register failed"
        , STATUS_REGISTER_ERROR = "register error"
        , EMPTY_FIELD_WARNING = "This field can not empty!"
        ;
    private TextInputLayout tilFullname, tilEmail, tilPhone, tilUsername, tilPassword, tilRepassword, tilDoB;
    private TextInputEditText tieDate;
    private RadioGroup rdgGender;
    private TextView tvLogin;
    private ImageView ivBack;
    private Button btRegister;
    private IntentFilter intentFilter;
    private LoadingDialog loadingDialog;

    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Connect elements
        initActivity();
        //Init intent filter
        initIntentFilter();
        //Connect DAO database
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        userDAO = appDatabase.userDAO();
        //Setup function buttons of activity
        ivBack.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tieDate.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        loadingDialog = new LoadingDialog(RegisterActivity.this);
    }
    //INITIALIZE PHASE
    private void initActivity(){
        tilFullname = findViewById(R.id.til_fullname);
        tilEmail = findViewById(R.id.til_email);
        tilPhone = findViewById(R.id.til_phone_number);
        tilUsername = findViewById(R.id.til_username);
        tilPassword = findViewById(R.id.til_password);
        tilRepassword = findViewById(R.id.til_re_password);
        tilDoB = findViewById(R.id.til_dob);
        tieDate = findViewById(R.id.tie_dob);
        rdgGender = findViewById(R.id.radio_group_gender);
        btRegister = findViewById(R.id.bt_register);
        ivBack = findViewById(R.id.ivBack);
        tvLogin = findViewById(R.id.tv_login);
    }

    private void initIntentFilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_REGISTER);
    }

    //Intent PROCESS
    private void sendRegisterStatusToBroadcast(String statusRegisterStr) {
        Intent registerIntent = new Intent();
        registerIntent.setAction(ACTION_REGISTER);
        registerIntent.putExtra(STATUS_REGISTER, statusRegisterStr);
        sendBroadcast(registerIntent);
    }
    private final BroadcastReceiver registerBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String statusRegister = intent.getStringExtra(STATUS_REGISTER);
            switch (Objects.requireNonNull(statusRegister)) {
                case STATUS_REGISTER_SUCCESS:
                    MyToast.successfulToast(RegisterActivity.this, "Register Successfully! Login now");
                    loadingDialog.stopLoadingDialog();
                    break;
                case STATUS_REGISTER_FAILED:
                    MyToast.warningToast(RegisterActivity.this, "Register Failed, check your information and try again!");
                    loadingDialog.stopLoadingDialog();
                    break;
                case STATUS_REGISTER_ERROR:
                    MyToast.errorToast(RegisterActivity.this, "All required field must be filled!");
                    loadingDialog.stopLoadingDialog();
                    break;
            }
        }
    };
    //REGISTER PROCESS
    private void registerSystem() throws ParseException {
        loadingDialog.startLoadingDialog();
        String fullName = getText(tilFullname);
        String dob = getText(tilDoB);
        int gender = getGender();
        String email = getText(tilEmail);
        String username = getText(tilUsername);
        String password = getText(tilPassword);
        String rePassword = getText(tilRepassword);
        String phone = getText(tilPhone);

        tilFullname.setError(null);
        tilDoB.setError(null);
        tilEmail.setError(null);
        tilPhone.setError(null);
        tilUsername.setError(null);
        tilPassword.setError(null);
        tilRepassword.setError(null);

        if (fullName.isEmpty() || dob.isEmpty() || username.isEmpty() || password.isEmpty() || rePassword.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            if (fullName.isEmpty()) {
                tilFullname.setError(EMPTY_FIELD_WARNING);
            }
            if (dob.isEmpty()) {
                tilDoB.setError(EMPTY_FIELD_WARNING);
            }
            if (username.isEmpty()) {
                tilUsername.setError(EMPTY_FIELD_WARNING);
            }
            if (password.isEmpty()) {
                tilPassword.setError(EMPTY_FIELD_WARNING);
            }
            if (rePassword.isEmpty()) {
                tilRepassword.setError(EMPTY_FIELD_WARNING);
            }
            if(email.isEmpty()){
                tilEmail.setError(EMPTY_FIELD_WARNING);
            }
            if (phone.isEmpty()) {
                tilPhone.setError(EMPTY_FIELD_WARNING);
            }
            sendRegisterStatusToBroadcast(STATUS_REGISTER_ERROR);
        } else if (userDAO.getUserById(username) != null){
            tilUsername.setError("This username has already exist!");
            sendRegisterStatusToBroadcast(STATUS_REGISTER_FAILED);
        } else if(!isDate(dob) || !isEmail(email)) {
            sendRegisterStatusToBroadcast(STATUS_REGISTER_FAILED);
        } else if (!rePassword.equals(password)) {
            tilRepassword.setError("Password not matched!");
            sendRegisterStatusToBroadcast(STATUS_REGISTER_FAILED);
        } else {
            Date xDob = DateConverterStrDate.stringToDate(dob);
            userDAO.insertUser(new UserEntity(username, password, fullName, xDob, gender, email, phone, 2));
            toLoginActivity();
            sendRegisterStatusToBroadcast(STATUS_REGISTER_SUCCESS);
        }
    }
    // VERIFY INPUT FUNCTIONS
    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        // get current date
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        // Show dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, (datePicker, y, m, d) -> {
            calendar.set(y, m, d);
            tieDate.setText(DateConverterStrDate.dateToString(calendar.getTime()));
        }, year, month, date);

        datePickerDialog.show();
    }

    private boolean isEmail(String email) {
//        if (!email.matches(User.MATCHES_EMAIL)) {
//            tilEmail.setError("Wrong email format!");
//            return false;
//        }
        return true;
    }
    private boolean isDate(String dateStr) {
        try {
            Date date = DateConverterStrDate.stringToDate(dateStr);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            tilDoB.setError("Wrong date format!");
            return false;
        }
    }

    private int getGender() {
        if (rdgGender.getCheckedRadioButtonId() == R.id.radio_male) {
            return 1;
        }
        return 0;
    }

    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }
    //INTENT
//    private void toUserProfileActivity(String username){
//        Intent intentUserProfile = new Intent(RegisterActivity.this, UserProfileActivity.class);
//        intentUserProfile.putExtra(KEY_USER, username);
//        startActivity(intentUserProfile);
//    }

    private void toLoginActivity(){
        Intent intentRegisterActivity = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intentRegisterActivity);
    }

    //Activity Function
    private void clearText() {
        Objects.requireNonNull(tilUsername.getEditText()).setText("");
        Objects.requireNonNull(tilFullname.getEditText()).setText("");
        Objects.requireNonNull(tilDoB.getEditText()).setText("");
        Objects.requireNonNull(tilEmail.getEditText()).setText("");
        Objects.requireNonNull(tilPassword.getEditText()).setText("");
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(registerBroadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(registerBroadcastReceiver);
        clearText();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.ivBack){
            finish();
        } else if (view.getId() == R.id.tie_dob) {
            showDateDialog();
        } else if (view.getId() == R.id.bt_register) {
            try {
                registerSystem();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else if (view.getId() == R.id.tv_login) {
            toLoginActivity();
        }
    }
}