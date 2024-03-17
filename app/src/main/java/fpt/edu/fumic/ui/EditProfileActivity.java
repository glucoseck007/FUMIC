package fpt.edu.fumic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import fpt.edu.fumic.database.dao.UserDAO;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.utils.DateConverterStrDate;
import fpt.edu.fumic.utils.LoadingDialog;
import fpt.edu.fumic.utils.MyToast;
import fpt.edu.fumic.utils.UserInformation;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ACTION_REGISTER = "action register", STATUS_REGISTER = "status register", STATUS_REGISTER_SUCCESS = "register successful", STATUS_REGISTER_FAILED = "register failed", STATUS_REGISTER_ERROR = "register error", EMPTY_FIELD_WARNING = "This field can not empty!";
    private TextInputLayout tilFullname, tilEmail, tilPhone, tilDoB;
    private TextInputEditText tieDate;
    private RadioGroup rdgGender;

    private ImageView ivBack;
    private Button btSave;

    private LoadingDialog loadingDialog;
    private UserRepository userRepository;
    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initActivity();

        //Init user repository
        userRepository = new UserRepository(this);
        userEntity = UserInformation.getInstance().getUser();
        if (userEntity == null) {
            return;
        }

        //loadView
        loadView();

        ivBack.setOnClickListener(this);
        tieDate.setOnClickListener(this);
        btSave.setOnClickListener(this);
        loadingDialog = new LoadingDialog(EditProfileActivity.this);
    }

    private void loadView() {
        rdgGender.check(getIdGender());
        setText(tilFullname, userEntity.getName());
        setText(tilDoB, DateConverterStrDate.dateToString(userEntity.getDob()));
        setText(tilEmail, userEntity.getEmail());
        setText(tilPhone, userEntity.getPhone());
    }

    private void clearText() {

        Objects.requireNonNull(tilFullname.getEditText()).setText("");
        Objects.requireNonNull(tilDoB.getEditText()).setText("");
        Objects.requireNonNull(tilEmail.getEditText()).setText("");
        Objects.requireNonNull(tilPhone.getEditText()).setText("");

    }

    private void initActivity() {
        tilFullname = findViewById(R.id.til_fullname);
        tilEmail = findViewById(R.id.til_email);
        tilPhone = findViewById(R.id.til_phone_number);

        tilDoB = findViewById(R.id.til_dob);
        tieDate = findViewById(R.id.tie_dob);
        rdgGender = findViewById(R.id.radio_group_gender);
        btSave = findViewById(R.id.bt_save);
        ivBack = findViewById(R.id.ivBack);

    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        // get current date
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        // Show dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, (datePicker, y, m, d) -> {
            calendar.set(y, m, d);
            tieDate.setText(DateConverterStrDate.dateToString(calendar.getTime()));
        }, year, month, date);

        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBack) {
            finish();
        } else if (view.getId() == R.id.tie_dob) {
            showDateDialog();
        } else if (view.getId() == R.id.bt_save) {
            try {
                updateProfile();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }

    @NonNull
    private void setText(TextInputLayout textInputLayout, String text) {
        Objects.requireNonNull(textInputLayout.getEditText()).setText(text);
    }

    private void updateProfile() throws ParseException {
        loadingDialog.startLoadingDialog();
        String fullName = getText(tilFullname);
        String dob = getText(tilDoB);
        int gender = getGender();
        String email = getText(tilEmail);
        String phone = getText(tilPhone);


        tilFullname.setError(null);
        tilDoB.setError(null);
        tilEmail.setError(null);
        tilPhone.setError(null);


        if (fullName.isEmpty() || dob.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            if (fullName.isEmpty()) {
                tilFullname.setError(EMPTY_FIELD_WARNING);
            }
            if (dob.isEmpty()) {
                tilDoB.setError(EMPTY_FIELD_WARNING);
            }


            if (email.isEmpty()) {
                tilEmail.setError(EMPTY_FIELD_WARNING);
            }
            if (phone.isEmpty()) {
                tilPhone.setError(EMPTY_FIELD_WARNING);
            }
            loadingDialog.stopLoadingDialog();
            MyToast.errorToast(EditProfileActivity.this, "All required field must be filled!");
        } else if (!isDate(dob) || !isEmail(email)) {
            loadingDialog.stopLoadingDialog();
            MyToast.warningToast(EditProfileActivity.this, "Register Failed, check your information and try again!");
        } else {
            userEntity.setName(fullName);
            userEntity.setDob(DateConverterStrDate.stringToDate(dob));
            userEntity.setEmail(email);
            userEntity.setGender(gender);
            userEntity.setPhone(phone);
            userRepository.updateUser(userEntity);
            UserInformation.getInstance().setUser(userEntity);
            loadingDialog.stopLoadingDialog();
            MyToast.successfulToast(EditProfileActivity.this, "Successfully updated!");
            Intent intent = new Intent();
            setResult(android.app.Activity.RESULT_OK, intent);
            finish();
        }
    }

    private boolean isEmail(String email) {
        if (!email.matches(UserEntity.MATCHES_EMAIL)) {
            tilEmail.setError("Wrong email format!");
            return false;
        }
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
    private int getIdGender() {
        if (userEntity.getGender() == 1){
            return R.id.radio_male;
        }

        return  R.id.radio_female;
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearText();
    }
}