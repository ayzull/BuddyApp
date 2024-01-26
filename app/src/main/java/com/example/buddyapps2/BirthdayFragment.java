package com.example.buddyapps2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BirthdayFragment extends Fragment {

    private ListView birthdayListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_birthday, container, false);
        birthdayListView = view.findViewById(R.id.birthdayListView);

        setBirthdayAdapter();

        birthdayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend selectedFriend = Friend.nonDeletedFriends().get(position);
                // Check if the selected friend has a mobile number
                if (selectedFriend.getMobile() != null && !selectedFriend.getMobile().isEmpty()) {
                    // Start BirthdayActivity and pass the mobile number
                    Intent intent = new Intent(requireContext(), BirthdayActivity.class);
                    intent.putExtra("selectedMobile", selectedFriend.getMobile());
                    intent.putExtra("selectedName", selectedFriend.getFriend_name());
                    intent.putExtra("selectedFriend", selectedFriend);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
    private void setBirthdayAdapter() {
        List<String> birthdayList = new ArrayList<>();
        for (Friend friend : Friend.nonDeletedFriends()) {
            if (friend.getDob() != null) {
                String birthdayString = friend.getFriend_name() + " - " + friend.getBirthdayString();
                birthdayList.add(birthdayString);
            }
        }
        ArrayAdapter<String> birthdayAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, birthdayList);

        birthdayListView.setAdapter(birthdayAdapter);
    }
}