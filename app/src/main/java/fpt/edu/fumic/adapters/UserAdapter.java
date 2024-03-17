package fpt.edu.fumic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import fpt.edu.fumic.R;
import fpt.edu.fumic.database.entity.UserEntity;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserEntity> userList;
    private OnUserClickListener onUserClickListener;

    // Constructor to set the click listener
    public UserAdapter(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    // Method to set user list to the adapter
    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserEntity user = userList.get(position);
        holder.txtUserName.setText(user.getName());
        holder.txtID.setText(String.valueOf(user.getId()));

        // Set click listener for user item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickListener != null) {
                    onUserClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    // Define your UserViewHolder class here
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName, txtID;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txt_user_name);
            txtID = itemView.findViewById(R.id.txt_user_id);
        }
    }

    // Interface to handle click events on user items
    public interface OnUserClickListener {
        void onUserClick(UserEntity user);
    }
}
