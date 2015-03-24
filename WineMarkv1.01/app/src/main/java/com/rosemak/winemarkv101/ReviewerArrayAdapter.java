package com.rosemak.winemarkv101;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by stevierose on 3/12/15.
 */
public class ReviewerArrayAdapter extends BaseAdapter {
    private static final long ID_CONSTANT = 0x010101010L;
    private ArrayList<Reviewer> mArrayList;
    private Context mContext;

    public ReviewerArrayAdapter(Context _context, ArrayList<Reviewer> _reviewer) {
        mContext = _context;
        mArrayList = _reviewer;
    }
    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Reviewer getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reviewer_list_item, parent, false);
        }
        final Reviewer reviewer = getItem(position);

        TextView winePlace = (TextView) convertView.findViewById(R.id.placeName);
        winePlace.setText(reviewer.getPlace());
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.placeRating);
        ratingBar.setRating(reviewer.getRating());

        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(reviewer.getmMonth() + "/" + reviewer.getmDay() + "/" + reviewer.getmYear());

        return convertView;
    }
}
