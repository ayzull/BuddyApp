package com.example.buddyapps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    DBHelper db;
    EditText editTextUsername, editTextPass;
    Button loginButton, signUp;
    ToggleButton showPasswordToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        db = new DBHelper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPass = findViewById(R.id.editTextPass);
        loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUp);
        showPasswordToggleButton = findViewById(R.id.toggleButtonShowPassword);

        showPasswordToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toggle password visibility when the button is checked or unchecked
                if (isChecked) {
                    editTextPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    editTextPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                // Move the cursor to the end of the text to maintain the correct position
                editTextPass.setSelection(editTextPass.getText().length());
            }
        });
    }

    public void onClick(View view){
        boolean check;
        String namauser, pwd;
        Intent intent;
        int id;

        id = view.getId();

        if (id==R.id.loginButton){
            namauser = editTextUsername.getText().toString().trim();
            pwd = editTextPass.getText().toString().trim();

            if(namauser.equals("")||pwd.equals("")){
                Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
            }
            else {
                check = db.checkLogin(namauser, pwd);
                if (check){
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (id==R.id.signUp) { //Signup
            Toast.makeText(getApplicationContext(), "Sign Up", Toast.LENGTH_SHORT).show();
            intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }
    }
}