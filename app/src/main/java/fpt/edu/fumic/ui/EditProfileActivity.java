package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.dao.UserDAO;
import fpt.edu.fumic.database.FumicDB;
import fpt.edu.fumic.model.User;

public class EditProfileActivity extends AppCompatActivity {
    private Spinner genderSpinner;
    private UserDAO userDAO ;
    private User user;
    private ImageButton btnBack;
    private MaterialButton btnSave;
    private EditText edtName, edtPhone, edtEmail, edtAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userDAO = new UserDAO(this);
        String uid = getIntent().getStringExtra("uid");
        user = userDAO.getUser(uid);
        initView();
        loadView();
        initSpinner();
        initEvent();

    }

    private void initEvent() {
        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    int gender = 0;

    private void checkData() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();



        if (name.isEmpty() || phone.isEmpty() || age.isEmpty() || email.isEmpty() ) {
            Toast.makeText(this, "Please complete all information!", Toast.LENGTH_SHORT).show();
        } else {

            updateData(name, phone, age, email, gender);
        }

    }

    private void updateData(String name, String phone, String age, String email,  int gender) {
        boolean isUpdate = userDAO.updateUser(user.getId(), user.getPassword(), name, Integer.parseInt(age), gender, email, phone, user.getRole(), user.getNotification());
        if (isUpdate){
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(android.app.Activity.RESULT_OK, intent);
            finish();
        }else {
            Toast.makeText(this, "An error occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSpinner() {
        List<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Unknown");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setSelection(user.getGender());
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadView() {
        edtName.setText(user.getName());
        edtPhone.setText(user.getPhone());
        edtAge.setText(String.valueOf(user.getAge()));
        edtEmail.setText(user.getEmail());

    }


    @SuppressLint("WrongViewCast")
    private void initView() {
        genderSpinner = findViewById(R.id.gender_spinner);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtAge = findViewById(R.id.edtAge);


        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
    }
}