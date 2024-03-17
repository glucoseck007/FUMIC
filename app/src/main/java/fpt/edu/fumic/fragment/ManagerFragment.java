package fpt.edu.fumic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpt.edu.fumic.R;
import fpt.edu.fumic.ui.BookListActivity;
import fpt.edu.fumic.ui.BookListActivity2;
import fpt.edu.fumic.ui.CategoryListActivity;
import fpt.edu.fumic.ui.ManageActivity;
import fpt.edu.fumic.ui.ManageUserActivity;
import fpt.edu.fumic.utils.MyToast;

public class ManagerFragment extends Fragment implements View.OnClickListener{
    private ImageView ivBook, ivUser, ivCategory, ivNotification;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivBook = view.findViewById(R.id.iv_book);
        ivUser = view.findViewById(R.id.iv_user);
        ivCategory = view.findViewById(R.id.iv_category);
        ivNotification = view.findViewById(R.id.iv_notification);

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
            intent = new Intent(getActivity(), BookListActivity2.class);
        } else if (viewId == R.id.iv_user) {
            intent = new Intent(getActivity(), ManageUserActivity.class);
        }
        else if (viewId == R.id.iv_category) {
            MyToast.confusingToast(getContext(), "This function is coming soon!");
            intent = new Intent(getActivity(), CategoryListActivity.class);
        } else if (viewId == R.id.iv_notification) {
            MyToast.confusingToast(getContext(), "This function is coming soon!");
//            intent = new Intent(ManageActivity.this, NotificationActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
