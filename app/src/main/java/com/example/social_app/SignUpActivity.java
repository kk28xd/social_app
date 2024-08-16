package com.example.social_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText signUp_name;
    EditText signUp_phone;
    EditText signUp_email;
    EditText signUp_password;

    Button signUp;

    TextView textLogin;
    MyDatabase myDatabase;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp_name = findViewById(R.id.signUp_name);
        signUp_phone = findViewById(R.id.signUp_phone);
        signUp_email = findViewById(R.id.signUp_email);
        signUp_password = findViewById(R.id.signUp_password);
        signUp = findViewById(R.id.signUp_button);
        textLogin = findViewById(R.id.text_login);
        myDatabase = new MyDatabase(this);
        //myDatabase.onUpgrade(new SQLiteDatabase(),1,2);

        textLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

        signUp.setOnClickListener(v -> {
            String name = signUp_name.getText().toString();
            String phone = signUp_phone.getText().toString();
            String email = signUp_email.getText().toString();
            String password = signUp_password.getText().toString();
            boolean userFound = false;

            if (name.length() > 0 && phone.length() > 0 && email.length() > 0 && password.length() > 0) {
                if (isValidEmail(email)) {
                    if (isValidPassword(password)) {
                        ArrayList<User> arrayList = myDatabase.getAllUsers();
                        for (int i = 0; i < arrayList.size(); i++) {
                            String arr_email = arrayList.get(i).getUserEmailAddress();
                            if (email.equals(arr_email)) {
                                Toast.makeText(this, "email address is already exist!", Toast.LENGTH_SHORT).show();
                                userFound = true;
                                break;
                            }
                        }
                        if (!userFound) {
                            myDatabase.addUser(new User(name, phone, email, password));

                            Cursor cursor = myDatabase.getUserByEmail(email);

                            if (cursor != null && cursor.moveToFirst()) {
                                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));

                                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email).apply();

                                intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                }
            }
            if (email.length() == 0) {
                Toast.makeText(this, "empty email!", Toast.LENGTH_SHORT).show();
            }
            if (password.length() == 0) {
                Toast.makeText(this, "empty password!", Toast.LENGTH_SHORT).show();
            }
            if (signUp_name.length() == 0) {
                Toast.makeText(this, "empty name!", Toast.LENGTH_SHORT).show();
            }
            if (signUp_phone.length() == 0) {
                Toast.makeText(this, "empty phone!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        Pattern PASSWORD_PATTERN = Pattern.compile(".{8,}$");
        return !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches();
    }
}