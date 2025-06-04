package com.example.eats365test.data.api;

import com.example.eats365test.data.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("users")
    Call<List<UserInfo>> getUsers(@Query("per_page") int perPage);
}
