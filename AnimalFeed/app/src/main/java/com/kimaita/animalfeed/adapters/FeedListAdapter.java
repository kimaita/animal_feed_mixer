package com.kimaita.animalfeed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.kimaita.animalfeed.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> animals;
    private Map<String, List<String>> animalFeeds;

    public FeedListAdapter(Context c, Map<String, List<String>> animalFeeds) {
        context = c;
        this.animals = new ArrayList<>(animalFeeds.keySet());
        this.animalFeeds = animalFeeds;
    }

    @Override
    public Object getGroup(int listPosition) {
        return animals.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return animals.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        String animal = animals.get(listPosition);
        return animalFeeds.get(animal).size();
    }

    @Override
    public Object getChild(int animalListPosition, int feedListPosition) {
        String animal = animals.get(animalListPosition);
        return animalFeeds.get(animal).get(feedListPosition);
    }

    @Override
    public long getChildId(int animalListPosition, int feedListPosition) {
        return feedListPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String animal = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_animalfeeds_animal, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.text_animal_name);
        listTitleTextView.setText(animal);

        return convertView;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_animalfeeds_feed, null);
        }

        String feed = (String) getChild(listPosition, expandedListPosition);
        TextView feedTextView = convertView.findViewById(R.id.text_animal_feed);
        feedTextView.setText(feed);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}