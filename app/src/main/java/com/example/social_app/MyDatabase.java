package com.example.social_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_USERS =
                "CREATE TABLE " + TABLE_USERS + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_PHONE + " TEXT,"
                        + COLUMN_EMAIL + " TEXT,"
                        + COLUMN_PASSWORD + " TEXT"
                        + ")";
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getUserName());
        contentValues.put(COLUMN_PHONE, user.getUserPhoneNumber());
        contentValues.put(COLUMN_EMAIL, user.getUserEmailAddress());
        contentValues.put(COLUMN_PASSWORD, user.getUserPassword());
        
        return db.insert(TABLE_USERS, null, contentValues);
    }

    public long updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, user.getUserName());
        contentValues.put(COLUMN_PHONE, user.getUserPhoneNumber());
        contentValues.put(COLUMN_EMAIL, user.getUserEmailAddress());
        return db.update(TABLE_USERS, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    public int deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("users", "email = ?", new String[]{String.valueOf(user.getUserEmailAddress())});
        db.close();
        return rowsDeleted;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        // To check if there is data or not
        if (c != null && c.moveToFirst()) {
            do {
                //long id = c.getInt(0);
                @SuppressLint("Range") long id = c.getInt(c.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String name = c.getString(c.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String phoneNumber = c.getString(c.getColumnIndex(COLUMN_PHONE));
                @SuppressLint("Range") String emailAddress = c.getString(c.getColumnIndex(COLUMN_EMAIL));
                @SuppressLint("Range") String password = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
                users.add(new User(name, phoneNumber, emailAddress, password));
            } while (c.moveToNext());
        }
        c.close();
        return users;
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_PASSWORD},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
