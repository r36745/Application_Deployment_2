package com.rosemak.winemarkv101;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stevierose on 3/10/15.
 */
public class ReviewerFragment extends Fragment implements LocationListener {

    public static final String TAG = "ReviewerFragment.TAG";
    private static final String REVIEWER = "Reviwer Frag Log";
    private OnButtonClickListener mListener;
    EditText place, review;
    RatingBar rating;
    Button saveButton;
    private Reviewer mReviewer;
    private static final int REQUEST_ENABLE_GPS = 0x02001;
    public static final String FILE_NAME = "reviewFile.txt";
    private static final int REQUEST_TAKE_PICTURE = 0x01001;
    ImageView mImageView;
    Uri mImageUri;
    private LocationManager mLocation;
    ImageButton cameraButton;


    public interface OnButtonClickListener{

        public void review(Reviewer review);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnButtonClickListener) {
            mListener = (OnButtonClickListener) activity;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mReviewer = new Reviewer("", 0);
        mLocation = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // mImageView = (ImageView) getActivity().findViewById(R.id.contactImage);
        if(requestCode == REQUEST_TAKE_PICTURE ) {
            if(mImageUri != null) {
                Log.d("FLAG", "Theres an image");
               // mImageView.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
                addImageToGallery(mImageUri);
                // mListener.uriInfo(mImageUri);

            } else {
                mImageView.setImageBitmap((Bitmap)data.getParcelableExtra("data"));
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        place = (EditText) getActivity().findViewById(R.id.place);
        review = (EditText) getActivity().findViewById(R.id.review);
        rating = (RatingBar) getActivity().findViewById(R.id.ratingBar);
        saveButton = (Button) getActivity().findViewById(R.id.saveButton);
        cameraButton = (ImageButton) getActivity().findViewById(R.id.cameraButton);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Log.d(REVIEWER, "Rating= " + rating);
                mReviewer.setRating(rating);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = getImageUri();
                if (mImageUri != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                }
                startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String places = place.getText().toString();
                String reviews = review.getText().toString();
                String myImage = String.valueOf(mImageUri);
                mReviewer.setmImg(myImage);
                mReviewer.setPlace(places);
                mReviewer.setNotes(reviews);
                mListener.review(mReviewer);

            }
        });




    }

    private Uri getImageUri() {

        String imageName = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date(System.currentTimeMillis()));

        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File appDir = new File(imageDir, "CameraExample");
        appDir.mkdirs();

        File image = new File(appDir, imageName + ".jpg");
        try {
            image.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return Uri.fromFile(image);
    }

    private void addImageToGallery(Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        getActivity().sendBroadcast(scanIntent);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void enableGps() {
        if (mLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location loc = mLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double lat = loc.getLatitude();
            double lng = loc.getLongitude();
            mReviewer.setmLat(lat);
            mReviewer.setmLng(lng);

        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("GPS Unavailable")
                    .setMessage("Please enable GPS in the system settings.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }

                    })
                    .show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        enableGps();
    }



    @Override
    public void onPause() {
        super.onPause();

        mLocation.removeUpdates(this);
    }
}
