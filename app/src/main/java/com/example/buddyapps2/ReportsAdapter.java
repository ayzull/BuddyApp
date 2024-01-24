package com.example.buddyapps2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ReportsAdapter extends BaseAdapter {
    Context context;
    List<Reports> listReports;

    public ReportsAdapter(Context context, List<Reports> listReports) {
        this.context = context;
        this.listReports = listReports;
    }

    public int getCount(){
        return listReports.size();
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent){
        view = LayoutInflater.from(context).inflate(R.layout.list_report, parent, false);

        ImageView image = view.findViewById(R.id.image);
        TextView title = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.desc);

        title.setText(listReports.get(position).getTitle());
        description.setText(listReports.get(position).getTitle());
        image.setImageResource(listReports.get(position).getImage());

        return view;
    }
}
