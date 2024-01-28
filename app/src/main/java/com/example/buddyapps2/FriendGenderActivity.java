// FriendGenderActivity.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendGenderActivity extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_gender);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Gender Chart");

        pieChart = findViewById(R.id.GenderPieChart);
        populateGenderPieChart();
    }

    private void populateGenderPieChart() {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.populateFriendListArray();

        // Get friend genders and count
        HashMap<String, Integer> genderCount = new HashMap<>();
        for (Friend friend : Friend.friendArrayList) {
            String gender = friend.getGender();
            genderCount.put(gender, genderCount.getOrDefault(gender, 0) + 1);
        }

        // Create entries for the pie chart
        List<PieEntry> entries = new ArrayList<>();
        for (String gender : genderCount.keySet()) {
            entries.add(new PieEntry(genderCount.get(gender), gender));
        }

        // Set different colors for each entry
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int color = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
            colors.add(color);
        }

        int colorBlack = Color.parseColor("#000000");

        // Create a dataset and set properties
        PieDataSet dataSet = new PieDataSet(entries, "Friend Genders");
        dataSet.setColors(colors); // Set the colors for the dataset
        dataSet.setValueTextSize(16f); // Set the font size for the values
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(16f); // Set the global font size for all values
        pieChart.setEntryLabelColor(colorBlack);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();

        // Show legend inside a box
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setFormSize(20f);
        pieChart.getLegend().setForm(Legend.LegendForm.SQUARE);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pieChart.getLegend().setDrawInside(false);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return true;
    }
}
