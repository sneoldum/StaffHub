package com.emplomanage.staffhub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emplomanage.staffhub.model.User;
import com.emplomanage.staffhub.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        loginButton.setOnClickListener(v -> authenticateUser());
        registerButton.setOnClickListener(v -> registerUser());
    }

    private void authenticateUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty()) {
            usernameEditText.setError("Username is required");
            return;
        }

        if (password.length()<5) {
            passwordEditText.setError("Password is must be greater than 5 characters");
            return;
        }

        userViewModel.getUserByUsername(username).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null && user.getPassword().equals(password)) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    navigateToMainActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty()) {
            usernameEditText.setError("Username is required!");
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required!");
            return;
        }


        User user = new User(username, password);
        userViewModel.insertUser(user);

        Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // Bu, LoginActivity'yi kapatır ve geri düğmesine basıldığında tekrar dönülmesini önler
    }
}
