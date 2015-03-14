package com.rosemak.winemarkv101;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by stevierose on 3/13/15.
 */
public class DetailActivity extends Activity implements DetailFragment.DetailListener {
    public static final String TAG = "DetailActivity.TAG";
    private ArrayList<Reviewer> mArrayList;
    private Reviewer mReviewer;
    private Uri mImg;
    private double lat, lng;
    private float ratingFloater;
    private String place, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            DetailFragment detailFragment = new DetailFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, detailFragment, DetailFragment.TAG)
                    .commit();
        }

        Intent getIntent = getIntent();
        if (getIntent.hasExtra("reviewer")) {
            Log.d("DetailFlag", "Position Hit");

            mReviewer = (Reviewer) getIntent.getSerializableExtra("reviewer");
            Log.d("DETAILED Flag", "Reviews= " + mReviewer.getRating());
            place = mReviewer.getPlace();
            notes = mReviewer.getNotes();
            mImg = Uri.parse(mReviewer.getmImg());
            ratingFloater = mReviewer.getRating();

        }


    }

    @Override
    public String place() {
        return place;
    }

    @Override
    public String notes() {
        return notes;
    }

    @Override
    public Uri getUri() {
        return mImg;
    }

    @Override
    public float getFloat() {
        return ratingFloater;
    }
}
