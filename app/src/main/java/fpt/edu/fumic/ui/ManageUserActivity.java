package fpt.edu.fumic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.UserAdapter;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;

public class ManageUserActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private UserRepository userRepository;

    private TextView textNoOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        recyclerViewUsers = findViewById(R.id.recycler_view_users);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(this); // Pass the click listener
        recyclerViewUsers.setAdapter(userAdapter);
        userRepository = new UserRepository(this);

        userRepository.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                textNoOfUser = findViewById(R.id.text_no_users);
                if (userEntities.isEmpty()) {
                    recyclerViewUsers.setVisibility(View.GONE);
                    textNoOfUser.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewUsers.setVisibility(View.VISIBLE);
                    textNoOfUser.setVisibility(View.GONE);
                    userAdapter.setUserList(userEntities);
                }
            }
        });
    }

    @Override
    public void onUserClick(UserEntity user) {
        // Handle user item click
        Intent intent = new Intent(ManageUserActivity.this, UserDetailActivity.class);
        intent.putExtra("userEntity", user);
        startActivity(intent);
    }


}
