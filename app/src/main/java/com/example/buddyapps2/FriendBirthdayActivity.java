// FriendBirthdayActivity.java
package com.example.buddyapps2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FriendBirthdayActivity extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_birthday);

        pieChart = findViewById(R.id.BirthdayPieChart);
        populatePieChart();
    }

    private void populatePieChart() {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.populateFriendListArray();

        // Get friend birth months and count
        HashMap<String, Integer> birthMonthCount = new HashMap<>();
        for (Friend friend : Friend.friendArrayList) {
            String month = getMonthFromDate(friend.getDob());
            birthMonthCount.put(month, birthMonthCount.getOrDefault(month, 0) + 1);
        }

        // Create entries for the pie chart
        List<PieEntry> entries = new ArrayList<>();
        for (String month : birthMonthCount.keySet()) {
            entries.add(new PieEntry(birthMonthCount.get(month), month));
        }

        // Create a dataset and set properties
        PieDataSet dataSet = new PieDataSet(entries, "Birth Months");
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
    }

    private String getMonthFromDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
            return dateFormat.format(date);
        }
        return "";
    }
}
