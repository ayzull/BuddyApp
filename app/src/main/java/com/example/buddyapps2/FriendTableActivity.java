package com.example.buddyapps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FriendTableActivity extends AppCompatActivity {
    TableLayout tableLayout;
    TableRow headerRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_table);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Friend Report");

        tableLayout = findViewById(R.id.tableLayout);
        headerRow = new TableRow(this);

        populateDataRows(tableLayout);
    }

    private void populateDataRows(TableLayout tableLayout) {
        for (Friend friend : Friend.friendArrayList) {
            TableRow dataRow = new TableRow(this);


            // Add columns for data
            addDataColumn(dataRow, friend.getFriend_name());
            addDataColumn(dataRow, friend.getMobile());
            addDataColumn(dataRow, friend.getGender());
            addDataColumn(dataRow, friend.getFriend_email());
            addDataColumn(dataRow, friend.getDob());
            // Add more columns for other fields

            tableLayout.addView(dataRow);
        }
    }
    private void addDataColumn(TableRow row, Date date) {
        TextView column = new TextView(this);

        // Format the date using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(date);

        column.setText(formattedDate);
        //column.setPadding(8, 8, 8, 8);


        row.addView(column);
    }


    private void addDataColumn(TableRow row, String data) {
        TextView column = new TextView(this);

        //column.setPadding(8, 8, 8, 8);

        column.setText(data);

        row.addView(column);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return true;
    }

}