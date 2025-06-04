package com.example.eats365test.ui.viewmodel;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.eats365test.data.UserRepository;
import com.example.eats365test.data.model.UserInfo;

import java.util.List;

public class UsersViewModel extends ViewModel {
    private UserRepository repository;

    private final MutableLiveData<List<UserInfo>> _users = new MutableLiveData<>();
    public final LiveData<List<UserInfo>> users = _users;

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public final LiveData<String> errorMessage = _errorMessage;

    public UsersViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public void getUsers(int perPage) {
        repository.getUsers(perPage, new UserRepository.UserListCallback() {
            @Override
            public void onSuccess(List<UserInfo> users) {
                Log.d("message", users.toString());
                _users.setValue(users);
            }

            @Override
            public void onError(String errorMessage) {
                _errorMessage.setValue(errorMessage);
            }
        });
    }

    static public class UserViewModelFactory implements ViewModelProvider.Factory {
        private final UserRepository repository;

        public UserViewModelFactory(UserRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UsersViewModel(repository);
        }
    }
}
