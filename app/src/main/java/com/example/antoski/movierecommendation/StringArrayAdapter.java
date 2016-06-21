package com.example.antoski.movierecommendation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Antoski on 21-Jun-16.
 */
public class StringArrayAdapter extends BaseAdapter {

    ArrayList<String> items;
    Context context;
    LayoutInflater inflater;

    public StringArrayAdapter(ArrayList<String> items, Context context) {
        this.items = items;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView item = (TextView) convertView.findViewById(android.R.id.text1);
        item.setText(items.get(position));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}