package com.example.eats365test.ui;


import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.eats365test.R;
import com.example.eats365test.data.UserRepository;
import com.example.eats365test.data.api.GitHubService;
import com.example.eats365test.data.api.NetworkManager;
import com.example.eats365test.data.model.UserInfo;
import com.example.eats365test.databinding.ActivityUsersBinding;
import com.example.eats365test.ui.viewmodel.UsersViewModel;

import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private UsersViewModel viewModel;
    private ActivityUsersBinding binding;
    private UserListAdapter userAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NetworkManager manager = NetworkManager.getInstance(getString(R.string.github_token));
        GitHubService gitHubService = manager.getGitHubService();
        UserRepository repository = new UserRepository(gitHubService);

        viewModel = new ViewModelProvider(this, new UsersViewModel.UserViewModelFactory(repository))
                .get(UsersViewModel.class);

        viewModel.getUsers(100);

        userAdapter = new UserListAdapter();
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUsers.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvUsers.setAdapter(userAdapter);

        //
        viewModel.users.observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfos) {
                userAdapter.updateUsers(userInfos);
            }
        });

        viewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(UsersActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
