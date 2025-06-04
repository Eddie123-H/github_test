package com.example.eats365test.data.api;

import android.os.Build;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private String GITHUB_BASE_URL = "https://api.github.com";
    private static NetworkManager networkManager = null;
    private Retrofit retrofit = null;
    private GitHubService gitHubService = null;

    private OkHttpClient okHttpClient(String token) {
        OkHttpClient.Builder build = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .headers(Headers.of("Accept", "application/vnd.github+json",
                                "Authorization", "Bearer " + token,
                                "X-GitHub-Api-Version", "2022-11-28"))
                        .build();

                return chain.proceed(request);
            }
        });

        build.addInterceptor(new HttpLoggingInterceptor());

        return build.build();
    }

    private NetworkManager(String token){
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GITHUB_BASE_URL)
                .client(okHttpClient(token))
                .build();
    }

    public static NetworkManager getInstance(String token){
        if (networkManager == null) {
            synchronized (NetworkManager.class) {
                if (networkManager == null) {
                    networkManager = new NetworkManager(token);
                }
            }
        }
        return networkManager;
    }

    public GitHubService getGitHubService() {
        if (gitHubService == null) {
            gitHubService = retrofit.create(GitHubService.class);
        }
        return gitHubService;
    }
}
