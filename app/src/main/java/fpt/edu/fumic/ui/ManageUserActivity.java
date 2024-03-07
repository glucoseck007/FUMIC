package fpt.edu.fumic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.UserAdapter;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//delete user
    /*
    Date : 5/3/2024
    */
public class ManageUserActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener, View.OnClickListener {
//public class ManageUserActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private UserRepository userRepository;
    private ImageView ivBack;
    private TextView textNoOfUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        recyclerViewUsers = findViewById(R.id.recycler_view_users);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        ivBack = findViewById(R.id.ivBack); // Initialize ivBack
        ivBack.setOnClickListener(this); // Set click listener for ivBack

        userAdapter = new UserAdapter(this);
        recyclerViewUsers.setAdapter(userAdapter);

        userRepository = new UserRepository(this);

        // Fetch all users from the database
        userRepository.getAllUsers().observe(this, userEntities -> {
            textNoOfUser= findViewById(R.id.text_no_users);
            // Update the UI with the new list of users
            if (userEntities.isEmpty()) {
                // Show the TextView if there are no users
                recyclerViewUsers.setVisibility(View.GONE);
                textNoOfUser.setVisibility(View.VISIBLE);
            } else {
                // Show the RecyclerView if there are users
                recyclerViewUsers.setVisibility(View.VISIBLE);
                textNoOfUser.setVisibility(View.GONE);
                userAdapter.setUserList(userEntities);
            }
            // Update the UI with the new list of users
            userAdapter.setUserList(userEntities);
        });
    }


    // Handle user click event (e.g., delete user)
    @Override
    public void onUserClick(UserEntity user) {
        // Handle user deletion here
        userRepository.deleteUser(user);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ivBack){
            finish();
        }
    }
}
