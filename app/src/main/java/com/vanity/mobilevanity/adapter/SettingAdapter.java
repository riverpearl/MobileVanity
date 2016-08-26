package com.vanity.mobilevanity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vanity.mobilevanity.view.SettingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class SettingAdapter extends BaseAdapter {
    List<String> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(String item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<String> items) {
        items.addAll(items);
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
        SettingView view = new SettingView(parent.getContext());
        view.setTitleView(items.get(position));
        return view;
    }
}
