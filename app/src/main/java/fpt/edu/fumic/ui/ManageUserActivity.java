package fpt.edu.fumic.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpt.edu.fumic.R;
import fpt.edu.fumic.adapters.UserAdapter;
import fpt.edu.fumic.repository.UserRepository;

//delete user
    /*
    Date : 5/3/2024
    */
public class ManageUserActivity extends AppCompatActivity implements  View.OnClickListener {
//public class ManageUserActivity extends AppCompatActivity implements View.OnClickListener {

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

        userAdapter = new UserAdapter();
        recyclerViewUsers.setAdapter(userAdapter);
        userRepository = new UserRepository(this);

        userRepository.getAllUsers().observe(this, userEntities -> {
            textNoOfUser= findViewById(R.id.text_no_users);
            if (userEntities.isEmpty()) {
                recyclerViewUsers.setVisibility(View.GONE);
                textNoOfUser.setVisibility(View.VISIBLE);
            } else {
                recyclerViewUsers.setVisibility(View.VISIBLE);
                textNoOfUser.setVisibility(View.GONE);
                userAdapter.setUserList(userEntities);
            }
            userAdapter.setUserList(userEntities);
        });
    }

//    @Override
//    public void onUserClick(UserEntity user) {
//        AlertDialog1.showConfirmationDialog(this, "Confirm Deletion", "Are you sure want to delete that book?",
//                new AlertDialog1.OnConfirmationListener() {
//                    @Override
//                    public void onConfirm() {
//                        userRepository.deleteUser(user);
//                    }
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//    }

    @Override
    public void onClick(View v) {

    }
}
