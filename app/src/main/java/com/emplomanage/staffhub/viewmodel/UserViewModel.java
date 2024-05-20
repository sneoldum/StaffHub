package com.emplomanage.staffhub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emplomanage.staffhub.model.User;
import com.emplomanage.staffhub.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }
}
