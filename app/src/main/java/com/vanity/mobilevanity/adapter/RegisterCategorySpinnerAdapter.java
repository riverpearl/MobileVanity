package com.vanity.mobilevanity.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vanity.mobilevanity.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tacademy on 2016-09-19.
 */
public class RegisterCategorySpinnerAdapter extends BaseAdapter {
    List<String> items = new ArrayList<>();

    public void addAll(String[] items) {
        this.items.addAll(Arrays.asList(items));
        notifyDataSetChanged();
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_spinner_item, parent, false);
            view.setTextColor(Color.BLACK);
        } else {
            view = (TextView) convertView;
        }
        view.setBackgroundColor(Color.WHITE);
        view.setText(items.get(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = (TextView) convertView;
        }
        view.setBackgroundColor(Color.WHITE);
        view.setText(items.get(position));
        return view;
    }
}

