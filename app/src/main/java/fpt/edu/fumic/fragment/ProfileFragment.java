package fpt.edu.fumic.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.UserEntity;
import fpt.edu.fumic.repository.UserRepository;
import fpt.edu.fumic.ui.BrowseBookActivity;
import fpt.edu.fumic.ui.ChangePasswordActivity;
import fpt.edu.fumic.ui.FavouriteActivity;
import fpt.edu.fumic.ui.HistoriesActivity;
import fpt.edu.fumic.ui.UserDetailActivity;
import fpt.edu.fumic.ui.UserProfileActivity;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private View viewInformation, viewChangePassword, viewBrowseBooks, viewHistories, viewFavourite;
    private ImageView ivBack;
    private UserRepository userRepository;
    private UserEntity userEntity;

    private TextView tvName, tvEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initActivity(view);


        userRepository = new UserRepository(requireActivity());
        loadUser();
        viewInformation.setOnClickListener(this);
        viewChangePassword.setOnClickListener(this);
        viewBrowseBooks.setOnClickListener(this);
        viewHistories.setOnClickListener(this);
        viewFavourite.setOnClickListener(this);



    }

    private void loadUser() {




        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username != null) {
            userEntity = userRepository.getUserById(username);
            if (userEntity == null) {
                return;
            }
            loadView();
        }


    }

    ActivityResultLauncher<Intent> mStartForStoryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    loadUser();
                }
            });


    private void loadView() {
        tvName.setText(userEntity.getName());
        tvEmail.setText(userEntity.getEmail());

        if (userEntity.getRole() == 0 || userEntity.getRole() == 1) {
            viewBrowseBooks.setVisibility(View.VISIBLE);
        } else {
            viewBrowseBooks.setVisibility(View.GONE);
        }

    }


    private void initActivity(View view) {
        viewInformation = view.findViewById(R.id.viewInformation);
        viewChangePassword = view.findViewById(R.id.viewChangePassword);
        viewBrowseBooks = view.findViewById(R.id.viewBrowseBooks);
        viewHistories = view.findViewById(R.id.viewHistories);
        viewFavourite = view.findViewById(R.id.viewFavourite);
        ivBack = view.findViewById(R.id.ivBack);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.viewInformation) {
            Intent intent = new Intent(requireActivity(), UserDetailActivity.class);
            intent.putExtra("uid", userEntity.getId());
            mStartForStoryResult.launch(intent);
        } else if (view.getId() == R.id.viewChangePassword) {
            Intent intent = new Intent(requireActivity(), ChangePasswordActivity.class);
            intent.putExtra("uid", userEntity.getId());
            mStartForStoryResult.launch(intent);
        } else if (view.getId() == R.id.viewBrowseBooks) {
            startActivity(new Intent(requireActivity(), BrowseBookActivity.class));
        } else if (view.getId() == R.id.viewFavourite) {
            startActivity(new Intent(requireActivity(), FavouriteActivity.class));
        } else if (view.getId() == R.id.viewHistories) {
            startActivity(new Intent(requireActivity(), HistoriesActivity.class));
        }
    }
}