package com.example.buddyapps2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BirthdayFragment extends Fragment {

    private ListView birthdayListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_birthday, container, false);
        birthdayListView = view.findViewById(R.id.birthdayListView);
        Button sendBirthdayWishButton = view.findViewById(R.id.sendBirthdayWishButton);

        setBirthdayAdapter();

        sendBirthdayWishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), BirthdayActivity.class);
                startActivity(intent);
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