package com.rosemak.winemarkv101;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    private Reviewer mReviewer;

    public interface OnListClickListener{
        public void reviewerPos(int pos);
        public void reviewer(Reviewer reviewer);
        public void arrayReviewer(Reviewer arrayReviewer);
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

            reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.reviewerPos(position);
                    mListener.reviewer(mArrayList.get(position));
                }
            });

        } else {
            Log.d("FLAG", "HERE");
            readFromFile(getActivity(), MainActivity.FILE_NAME);
        }

    }

    public String readFromFile(Context mContext, String _fileName) {
        File external = mContext.getExternalFilesDir(null);
        final File file = new File(external, _fileName);
        if (!file.exists()) {
            return null;
        }
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));


            //Reviewer reviewer = (Reviewer) is.readObject();
            //ArrayList<Reviewer>  reviewers = new ArrayList<>();
            final ArrayList<Reviewer> reviewers = (ArrayList<Reviewer>) is.readObject();
            is.close();

            if (reviewers != null) {

                mArrayAdapter = new ReviewerArrayAdapter(getActivity(), reviewers);
                reviewListView.setAdapter(mArrayAdapter);
                reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Log.d("Flagged", "Reviewer= " +reviewers.get(position).getPlace());
                        Log.d("FLAG", "Touched");
                        mListener.arrayReviewer(reviewers.get(position));
                                /*for (int i=0; i < reviewers.size(); i++) {
                                     reviewers.get(i).getPlace();
                                     reviewers.get(i).getNotes();
                                     reviewers.get(i).getRating();
                                     reviewers.get(i).getmLat();
                                     reviewers.get(i).getmLng();

                                }*/
                    }
                });


                Log.d("FLAG", "Read Data");
                //mArrayList = new ArrayList<>();

            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
