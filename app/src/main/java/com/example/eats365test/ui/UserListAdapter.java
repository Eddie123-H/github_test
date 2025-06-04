package com.example.eats365test.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eats365test.data.model.UserInfo;
import com.example.eats365test.databinding.ItemUserBinding;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<UserInfo> users = new ArrayList<UserInfo>();

    public void updateUsers(List<UserInfo> newUsers) {
        this.users.clear();
        this.users.addAll(newUsers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding userBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(userBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserInfo user = users.get(position);
        holder.bindData(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    //avatar_url”, “login”, and “site_admin
    static class UserViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding userBinding;
        public UserViewHolder(ItemUserBinding userBinding) {
            super(userBinding.getRoot());
            this.userBinding = userBinding;
        }

        public void bindData(UserInfo user) {
            Glide.with(userBinding.getRoot().getContext())
                    .load(user.getAvatarUrl())
                    .into(userBinding.imgUser);
            userBinding.tvUserName.setText(user.getLogin());
            userBinding.tvAdmin.setText(user.getSiteAdmin().toString());
        }
    }
}
