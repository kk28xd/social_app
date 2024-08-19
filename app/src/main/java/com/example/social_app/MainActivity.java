package com.example.social_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tv_name;
    TextView tv_phone;
    TextView tv_email;
    TextView tv_password;

    Button log_out;
    Button delete;

    SharedPreferences sharedPreferences;
    Intent intent;

    MyDatabase myDatabase;

    long id;
    String name;

    String phoneNumber;

    String emailAddress;

    String password;

    @SuppressLint({"SetTextI18n", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_name = findViewById(R.id.text_name);
        tv_phone = findViewById(R.id.text_phone);
        tv_email = findViewById(R.id.text_email);
        tv_password = findViewById(R.id.text_password);
        log_out = findViewById(R.id.log_out_button);
        delete = findViewById(R.id.delete_user_button);
        //tv_id = findViewById(R.id.user_id);
        myDatabase = new MyDatabase(this);

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        ArrayList<User> arrayList = myDatabase.getAllUsers();
        for (User u : arrayList) {
            if (u.getUserEmailAddress().equals(sharedPreferences.getString("email", "user_email"))) {
                id = u.getId();
                name = u.getUserName();
                phoneNumber = u.getUserPhoneNumber();
                emailAddress = u.getUserEmailAddress();
                password = u.getUserPassword();
                break;
            }
        }

        //tv_id.setText("your id : " + id);
        tv_name.setText(name);
        tv_phone.setText(phoneNumber);
        tv_email.setText(emailAddress);
        tv_password.setText(password);

        log_out.setOnClickListener(
                v -> {
                    sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();

                    intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
        );

        delete.setOnClickListener(v -> {
            int y = myDatabase.deleteUser(new User(id, name, phoneNumber, emailAddress, password));

            sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();

            Toast.makeText(this, "user deleted", Toast.LENGTH_SHORT).show();

            intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }
}