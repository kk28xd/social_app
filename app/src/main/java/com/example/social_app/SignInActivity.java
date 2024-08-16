package com.example.social_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPassword;

    Button login;
    TextView text_signUp;

    MyDatabase myDatabase;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_Password);
        login = findViewById(R.id.login_button);
        text_signUp = findViewById(R.id.text_signUp);
        myDatabase = new MyDatabase(this);

        text_signUp.setOnClickListener(v -> {
            loginEmail.setText("");
            loginPassword.setText("");
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        login.setOnClickListener(v -> {
            if (loginEmail.getText().length() > 0 && loginPassword.getText().length() > 0) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                boolean userFound = false;
                ArrayList<User> arrayList = myDatabase.getAllUsers();
                for (int i = 0; i < arrayList.size(); i++) {
                    String arr_email = arrayList.get(i).getUserEmailAddress();
                    String arr_password = arrayList.get(i).getUserPassword();
                    if (email.equals(arr_email) && password.equals(arr_password)) {

                        userFound = true;


                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email).apply();


                        intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                if (!userFound) {
                    Toast.makeText(this, "user not found", Toast.LENGTH_SHORT).show();
                }
            }
            if (loginEmail.getText().length() == 0) {
                Toast.makeText(this, "empty email!", Toast.LENGTH_SHORT).show();
            }
            if (loginPassword.getText().length() == 0) {
                Toast.makeText(this, "empty password!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}