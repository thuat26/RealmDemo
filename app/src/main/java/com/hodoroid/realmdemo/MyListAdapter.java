package com.hodoroid.realmdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by billey_b on 19/10/14.
 */
public class MyListAdapter extends BaseAdapter {
    private ArrayList<Person> persons;
    private Context mContext;

    public MyListAdapter(ArrayList<Person> persons, Context mContext) {
        this.persons = persons;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person p = (Person) this.getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_cell, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.mMainLabel);
            holder.city = (TextView) convertView.findViewById(R.id.mCityLabel);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.name.setText(p.getName());
        holder.city.setText(p.getCity());
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView city;

    }
}
