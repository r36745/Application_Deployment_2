package com.rosemak.winemarkv101;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by stevierose on 3/13/15.
 */
public class DetailFragment extends Fragment {
    public static final String TAG = "DetailFragment.TAG";
    ImageView mImgView;
    Uri mImgUri;
    private DetailListener mListener;


    public interface DetailListener{

        public String place();
        public String notes();
        public Uri getUri();
        public float getFloat();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailListener) {
            mListener = (DetailListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView reviewerPlace = (TextView) getActivity().findViewById(R.id.placeDetail);
        TextView reviewerNotes = (TextView) getActivity().findViewById(R.id.placeNotes);
        RatingBar rating = (RatingBar) getActivity().findViewById(R.id.rating);
        mImgView = (ImageView) getActivity().findViewById(R.id.detailImage);

        reviewerPlace.setText(mListener.place());
        reviewerNotes.setText(mListener.notes());
        rating.setRating(mListener.getFloat());
        mImgView.setImageBitmap(BitmapFactory.decodeFile(mListener.getUri().getPath()));
    }
}
