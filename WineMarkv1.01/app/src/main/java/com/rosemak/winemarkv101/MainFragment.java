package com.rosemak.winemarkv101;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

/**
 * Created by stevierose on 3/5/15.
 */
public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment.TAG";
    private GoogleMap googleMap;
    private ArrayList<Reviewer> mArrayList;
    private static final String ALL_REVIEWS = "reviews";
    private static final String MAINFRAG = "maingFrag.Log";
    private ListView reviewListView;
    private ReviewerArrayAdapter mArrayAdapter;
    private OnListClickListener mListener;


    public interface OnListClickListener{
        public void reviewerPos(int pos);
        public void reviewer(Reviewer reviewer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnListClickListener) {
            mListener = (OnListClickListener) activity;
        }
    }

    public static MainFragment newInstance(ArrayList<Reviewer> reviewers) {
        MainFragment mainFragment = new MainFragment();
        Log.d(MAINFRAG, "Frag List= " + reviewers.get(0).getRating());
        Bundle args = new Bundle();
        args.putSerializable(ALL_REVIEWS, reviewers);
        mainFragment.setArguments(args);

        return mainFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        reviewListView = (ListView) getActivity().findViewById(R.id.wineList);


        Bundle args = getArguments();
        if (args != null && args.containsKey(ALL_REVIEWS)) {
            mArrayList = (ArrayList<Reviewer>) args.getSerializable(ALL_REVIEWS);
            mArrayAdapter = new ReviewerArrayAdapter(getActivity(), mArrayList);
            reviewListView.setAdapter(mArrayAdapter);


        }
        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.reviewerPos(position);
                mListener.reviewer(mArrayList.get(position));
            }
        });
    }
}
