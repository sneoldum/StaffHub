package com.emplomanage.staffhub.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.emplomanage.staffhub.database.AppDatabase;
import com.emplomanage.staffhub.database.UserDao;
import com.emplomanage.staffhub.model.User;

public class UserRepository {
    private UserDao userDao;
    private LiveData<User> user;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public void insertUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insertUser(user);
        });
    }
}
