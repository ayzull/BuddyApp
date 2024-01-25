package com.example.buddyapps2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ReportFragment extends Fragment {

    private ListView listView;
    private List<Reports> list;
    private ReportsAdapter reportsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        initWidgets(view);
        listShow();
        reportsAdapter = new ReportsAdapter(requireContext(), list);
        listView.setAdapter(reportsAdapter);
        setOnClickListener();
        return view;
    }

    private void initWidgets(View view) {
        listView = view.findViewById(R.id.report_list);
    }

    private void setOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(requireContext(), FriendGenderActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(requireContext(), FriendBirthdayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void listShow() {
        list = new ArrayList<>();
        list.add(new Reports(R.drawable.ic_launcher_background, "Gender Report", "Report for Friend's Gender"));
        list.add(new Reports(R.drawable.ic_launcher_background, "Birthday Report", "Report for Friend's Birthday"));
    }

}