package com.example.buddyapps2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.*;
import java.util.*;

public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final String DATABASE_NAME ="buddy_database.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_FRIEND="friend";

    private static final String TABLE_USER="user";

    private static final String KEY_FID="friend_id";
    private static final String KEY_NAME="friend_name";
    private static final String KEY_MOBILE="mobile";
    private static final String KEY_GENDER="gender";
    private static final String KEY_F_EMAIL="friend_email";
    private static final String KEY_DOB="dob";
    private static final String KEY_ADDRESS="address";
    private static final String KEY_DELETED="deleted";
    private static final String KEY_UID="user_id";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";
    private static final String KEY_UNAME="name";

    private static final DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder Query_Table, Query_Table2;
        Query_Table = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_FRIEND)
                .append("(")
                .append(KEY_FID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(KEY_NAME)
                .append(" TEXT, ")
                .append(KEY_MOBILE)
                .append(" TEXT, ")
                .append(KEY_GENDER)
                .append(" TEXT, ")
                .append(KEY_F_EMAIL)
                .append(" TEXT, ")
                .append(KEY_DOB)
                .append(" TEXT, ")
                .append(KEY_ADDRESS)
                .append(" TEXT, ")
                .append(KEY_DELETED)
                .append(" TEXT)");

        Query_Table2 = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_USER)
                .append("(")
                .append(KEY_UID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(KEY_USERNAME)
                .append(" TEXT, ")
                .append(KEY_PASSWORD)
                .append(" TEXT, ")
                .append(KEY_UNAME)
                .append(" TEXT)");


        db.execSQL(Query_Table.toString());
        //Create table for user, login
        db.execSQL(Query_Table2.toString());
        //db.execSQL("create table user(username text primary key, password text, name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        //onCreate(db);

        //Login
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    //Login
    public boolean insert(String namauser, String pwd, String nama){
        long ins;
        //SQLiteDatabase db;
        ContentValues values;

        //INSERT
        db = this.getWritableDatabase();
        values = new ContentValues();
//        values.put("username", namauser);
//        values.put("password", pwd);
//        values.put("name", nama);
        values.put(KEY_USERNAME, namauser);
        values.put(KEY_PASSWORD, pwd);
        values.put(KEY_UNAME, nama);

        ins = db.insert(TABLE_USER, null, values);
        db.close();
        return ins != -1;
    }

    //Check username
    public boolean checkUsername(String namauser){
        //SQLiteDatabase db;
        Cursor cursor;
        int count;

        db = this.getReadableDatabase();
        //cursor = db.rawQuery("SELECT * FROM user WHERE username=?", new String[]{namauser});
        cursor = db.rawQuery("SELECT * FROM " +TABLE_USER+ " WHERE " +KEY_USERNAME+ "=?", new String[]{namauser});

        count = cursor.getCount();
        db.close();
        cursor.close();
        return count<=0;
    }

    //Check login
    public boolean checkLogin(String namauser, String pwd){
        //SQLiteDatabase db;
        Cursor cursor;
        int count;

        db = this.getReadableDatabase();
        //cursor = db.rawQuery("SELECT * FROM user WHERE username=? and password=?", new String[]{namauser, pwd});
        cursor = db.rawQuery("SELECT * FROM " +TABLE_USER+ " WHERE " +KEY_USERNAME+ "=? AND " +KEY_PASSWORD+ "=?", new String[]{namauser,pwd});
        count = cursor.getCount();
        db.close();
        cursor.close();
        return count == 1;
    }

    public void insertFriend(Friend friend) {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FID,friend.getFriend_id());
        values.put(KEY_NAME,friend.getFriend_name());
        values.put(KEY_MOBILE,friend.getMobile());
        values.put(KEY_GENDER,friend.getGender());
        values.put(KEY_F_EMAIL,friend.getFriend_email());
        values.put(KEY_DOB, getStringFromDate(friend.getDob()));
        values.put(KEY_ADDRESS,friend.getAddress());
        values.put(KEY_DELETED, getStringFromDate(friend.getDeleted()));

        db.insert(TABLE_FRIEND,null,values);
//        if(FriendAdapter.adapterInstance != null) {
//            FriendAdapter.adapterInstance.notifyDataSetChange();
//        }
        db.close();
    }

    public void populateFriendListArray() {
        db = this.getReadableDatabase();

        Friend.friendArrayList.clear();

        try(Cursor result = db.rawQuery("SELECT * FROM " + TABLE_FRIEND, null)) {
            if(result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String fname = result.getString(1);
                    String mobile = result.getString(2);
                    String gender = result.getString(3);
                    String fEmail = result.getString(4);
                    String dobString = result.getString(5);
                    String address = result.getString(6);
                    String stringDeleted = result.getString(7);
                    Date deleted = getDateFromString(stringDeleted);
                    Date dob = getDateFromString(dobString);
                    Friend friend = new Friend(id, fname, mobile, gender, fEmail, dob, address, deleted);
                    Friend.friendArrayList.add(friend);
                }
            }
        }
        db.close();
    }

    public void updateFriend(Friend friend) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FID, friend.getFriend_id());
        values.put(KEY_NAME,friend.getFriend_name());
        values.put(KEY_MOBILE,friend.getMobile());
        values.put(KEY_GENDER,friend.getGender());
        values.put(KEY_F_EMAIL,friend.getFriend_email());
        values.put(KEY_DOB, getStringFromDate(friend.getDob()));
        values.put(KEY_ADDRESS,friend.getAddress());
        values.put(KEY_DELETED, getStringFromDate(friend.getDeleted()));

        db.update(TABLE_FRIEND, values,KEY_FID + " =?", new String[]{String.valueOf(friend.getFriend_id())});
        db.close();
    }

    public void deleteFriend(int friendId) {
        db = this.getWritableDatabase();

        db.delete(TABLE_FRIEND, KEY_FID + "=?", new String[]{String.valueOf(friendId)});
        db.close();
    }


    // function untuk dapatkan date format
    private String getStringFromDate(Date date) {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e) {
            return null;
        }
    }

}
