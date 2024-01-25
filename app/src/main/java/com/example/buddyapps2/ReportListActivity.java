package com.example.buddyapps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReportListActivity extends AppCompatActivity {

    ListView listView;
    List<Reports> list;
    ReportsAdapter reportsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlist);

        listView = findViewById(R.id.report_list);

        listShow();
        reportsAdapter = new ReportsAdapter(this, list);
        listView.setAdapter(reportsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent(ReportListActivity.this, FriendGenderActivity.class);
                    startActivity(intent);
                } else if (position == 1){
                    Intent intent = new Intent(ReportListActivity.this, FriendBirthdayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void listShow(){
        list = new ArrayList<>();

        list.add(new Reports(R.drawable.ic_gender, "Gender Report", "Report for Friend's Gender"));
        list.add(new Reports(R.drawable.ic_birthday, "Birthday Report", "Report for Friend's Birthday"));
    }
}