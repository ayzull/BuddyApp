// FriendBirthdayActivity.java
package com.example.buddyapps2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

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

        // Set different colors for each entry
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        // Create a dataset and set properties
        PieDataSet dataSet = new PieDataSet(entries, "Birth Months");
        dataSet.setColors(colors); // Set the colors for the dataset
        dataSet.setValueTextSize(16f); // Set the font size for the values
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(16f); // Set the global font size for all values
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();

        // Show legend inside a box
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setFormSize(12f);
        pieChart.getLegend().setForm(Legend.LegendForm.SQUARE);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pieChart.getLegend().setDrawInside(false);
    }

    private String getMonthFromDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
            return dateFormat.format(date);
        }
        return "";
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return true;
    }
}
