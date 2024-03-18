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
import fpt.edu.fumic.ui.LoginActivity;
import fpt.edu.fumic.ui.UserDetailActivity;
import fpt.edu.fumic.ui.UserProfileActivity;
import fpt.edu.fumic.utils.MyToast;
import fpt.edu.fumic.utils.UserInformation;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private View viewInformation, viewChangePassword, viewBrowseBooks, viewHistories, viewFavourite, viewLogout;
    private UserRepository userRepository;
    private UserEntity userEntity;

    private TextView tvName, tvEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActivity(view);
        userRepository = new UserRepository(requireActivity());
        loadView();
        viewInformation.setOnClickListener(this);
        viewChangePassword.setOnClickListener(this);
        viewBrowseBooks.setOnClickListener(this);
        viewHistories.setOnClickListener(this);
        viewFavourite.setOnClickListener(this);
        viewLogout.setOnClickListener(this);
    }

    private void loadUser() {
        userEntity = UserInformation.getInstance().getUser();
        loadView();
    }

    ActivityResultLauncher<Intent> mStartForStoryResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    loadUser();
                }
            });

    private void loadView() {
        userEntity = UserInformation.getInstance().getUser();
        if (userEntity == null){
            return;
        }
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
        viewLogout = view.findViewById(R.id.viewLogout);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
    }
    private void logout(){
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        UserInformation.getInstance().setUser(null);
        startActivity(intent);
        requireActivity().finish();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.viewInformation) {
            Intent intent = new Intent(requireActivity(), UserDetailActivity.class);
            mStartForStoryResult.launch(intent);
        } else if (view.getId() == R.id.viewChangePassword) {
            Intent intent = new Intent(requireActivity(), ChangePasswordActivity.class);
            mStartForStoryResult.launch(intent);
        } else if (view.getId() == R.id.viewBrowseBooks) {
            startActivity(new Intent(requireActivity(), BrowseBookActivity.class));
        } else if (view.getId() == R.id.viewFavourite) {
           startActivity(new Intent(requireActivity(), FavouriteActivity.class));
        } else if (view.getId() == R.id.viewHistories) {
            MyToast.confusingToast(getContext(), "This function is coming soon!");
//            startActivity(new Intent(requireActivity(), HistoriesActivity.class));
        } else if (view.getId() == R.id.viewLogout) {
            logout();
        }
    }
}