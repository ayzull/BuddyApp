package com.example.buddyapps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BirthdayActivity extends AppCompatActivity {

    private EditText txt_message, txt_number;
    private Button btn_whatsapp;
    private TextView getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Send WhatsApp");

        txt_message = findViewById(R.id.txt_msg);
        txt_number = findViewById(R.id.txt_mobile);
        btn_whatsapp = findViewById(R.id.btn_whatsapp);
        getName = findViewById(R.id.friendname);

        long selectedFriendId = getIntent().getLongExtra("selectedFriendId", -1);
        if (selectedFriendId != -1) {
            // Find the corresponding friend from the list based on the ID
            Friend selectedFriend = findFriendById(selectedFriendId);

            // Set the mobile number and friend name in the UI
            txt_number.setText(selectedFriend.getMobile());
            getName.setText(selectedFriend.getFriend_name());
        }
        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = txt_number.getText().toString();
                String message = txt_message.getText().toString();
                try {
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setAction(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "+60" + mobileNumber + "&text=" + Uri.encode(message)));
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                } catch (Exception e) {
                    // If WhatsApp is not installed or other issues occur
                    Toast.makeText(BirthdayActivity.this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        Friend selectedFriend = (Friend) getIntent().getSerializableExtra("selectedFriend");
        if (selectedFriend != null) {
            // Set the mobile number in the txt_number EditText
            txt_number.setText(selectedFriend.getMobile());
            getName.setText(selectedFriend.getFriend_name());
        }
    }

    private Friend findFriendById(long friendId) {
        // Iterate through the list and find the friend with the specified ID
        for (Friend friend : Friend.nonDeletedFriends()) {
            if (friend.getFriend_id() == friendId) {
                return friend;
            }
        }
        return null; // Return null if the friend is not found
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return true;
    }
}