package com.example.eats365test.data;

import com.example.eats365test.data.api.GitHubService;
import com.example.eats365test.data.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final GitHubService gitHubService;

    public UserRepository(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    public void getUsers(int perPage, final UserListCallback callback) {
        gitHubService.getUsers(perPage).enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("error : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                callback.onError("error : " + t.getMessage());
            }
        });
    }

    public interface UserListCallback {
        void onSuccess(List<UserInfo> users);
        void onError(String errorMessage);
    }
}
