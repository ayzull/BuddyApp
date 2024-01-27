package com.example.buddyapps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SignUpActivity extends AppCompatActivity {

    EditText signUpName, signUpUsername, pass1, pass2;
    Button signupButton;
    DBHelper db;
    ToggleButton showPassword1, showPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Sign Up");

        signUpName = findViewById(R.id.signUpName);
        signUpUsername = findViewById(R.id.signUpUsername);
        pass1 = findViewById(R.id.pass1);
        pass2 = findViewById(R.id.pass2);
        signupButton = findViewById(R.id.signupButton);
        showPassword1 = findViewById(R.id.showPassword1);
        showPassword2 = findViewById(R.id.showPassword2);

        showPassword1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglePasswordVisibility(pass1, showPassword1.isChecked());
            }
        });

        showPassword2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                togglePasswordVisibility(pass2, showPassword2.isChecked());
            }
        });

        db = new DBHelper(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama, namauser, pwd1, pwd2;
                boolean check, ins;
                Intent intent;

                nama = signUpName.getText().toString().trim();
                namauser = signUpUsername.getText().toString().trim();
                pwd1 = pass1.getText().toString().trim();
                pwd2 = pass2.getText().toString().trim();

                if (nama.equals("") || namauser.equals("") || pwd1.equals("") || pwd2.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (pwd1.equals(pwd2)) {
                        check = db.checkUsername(namauser);
                        if (check) {
                            ins = db.insert(namauser, pwd1, nama);
                            if (ins) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the Up button press (Back to Login)
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void togglePasswordVisibility(EditText editText, boolean showPassword) {
        if (showPassword) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        // Move the cursor to the end of the text to maintain the correct position
        editText.setSelection(editText.getText().length());
    }
}
