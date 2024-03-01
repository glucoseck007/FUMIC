package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import fpt.edu.fumic.R;
import fpt.edu.fumic.dao.UserDAO;
import fpt.edu.fumic.database.FumicDB;
import fpt.edu.fumic.model.User;

public class ChangePasswordActivity extends AppCompatActivity {
    private UserDAO userDAO ;
    private User user;
    private ImageButton btnBack;
    private MaterialButton btnSave;
    private EditText edtPasswordOld;
    private EditText edtPasswordNew;
    private EditText edtPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        userDAO = new UserDAO(this);
        String uid = getIntent().getStringExtra("uid");
        user = userDAO.getUser(uid);

        initView();

        initEvent();

    }

    private void initEvent() {
        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> checkData());
    }

    private void checkData() {
        String old = edtPasswordOld.getText().toString().trim();
        String newPass = edtPasswordNew.getText().toString().trim();
        String confirm = edtPasswordConfirm.getText().toString().trim();
        if (old.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Please fill in all information", Toast.LENGTH_SHORT).show();
        } else {
            if (old.equals(user.getPassword())) {
                if (newPass.equals(confirm)) {
                    boolean isChange = userDAO.changePassword(user.getId(), newPass);
                    if (isChange) {
                        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(android.app.Activity.RESULT_OK, intent);
                        finish();

                    } else {
                        Toast.makeText(this, "Unsuccessful!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "New passwords do not match!", Toast.LENGTH_SHORT).show();
                    edtPasswordConfirm.requestFocus();
                }
            } else {
                Toast.makeText(this, "Password entered is incorrect!", Toast.LENGTH_SHORT).show();
                edtPasswordOld.requestFocus();
            }
        }
    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        edtPasswordNew = findViewById(R.id.editPasswordNew);
        edtPasswordOld = findViewById(R.id.editPasswordOld);
        edtPasswordConfirm = findViewById(R.id.editPasswordConfirm);
    }
}