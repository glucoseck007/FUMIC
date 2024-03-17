package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import fpt.edu.fumic.R;

public class ManageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        ImageView ivBook = findViewById(R.id.iv_book);
        ImageView ivUser = findViewById(R.id.iv_user);
        ImageView ivCategory = findViewById(R.id.iv_category);
        ImageView ivNotification = findViewById(R.id.iv_notification);

        ivBook.setOnClickListener(this);
        ivUser.setOnClickListener(this);
        ivCategory.setOnClickListener(this);
        ivNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int viewId = v.getId();
        if (viewId == R.id.iv_book) {
            intent = new Intent(ManageActivity.this, BookListActivity2.class);
        } else if (viewId == R.id.iv_user) {
            intent = new Intent(ManageActivity.this, ManageUserActivity.class);
        }
        else if (viewId == R.id.iv_category) {
            intent = new Intent(ManageActivity.this, CategoryListActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
