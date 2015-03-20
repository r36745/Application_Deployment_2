package com.rosemak.winemarkv101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends Activity implements ReviewerFragment.OnButtonClickListener, MainFragment.OnListClickListener {
    public ArrayList<Reviewer> mArrayList;
    public static final String NAME_LIST = "arrayList";
    public static final String FILE_NAME = "newReviewFile.txt";
    private static final String MAIN = "mainActivity.Log";
    private Reviewer mReviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();

        Intent intent = getIntent();

        if (savedInstanceState == null) {


            //check to see if arrayList is empty
            mArrayList = new ArrayList<>();
            intent.putExtra(NAME_LIST, mArrayList);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment, MainFragment.TAG)
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_wine_form) {

            ReviewerFragment fragment = new ReviewerFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, ReviewerFragment.TAG)
                    .commit();

            return true;
        } /*else if (id == R.id.action_wine_search) {
            startActivity(new Intent(MainActivity.this, DetailActivity.class));
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void review(Reviewer review) {
        Log.d(MAIN, "Main Activity Rating= " + review.getRating());
        if (mArrayList.contains(review)){
            Log.d("Flag", "Ted Video");
        }
        mArrayList.add(review);
        writeToFile(this, FILE_NAME, mArrayList);
        MainFragment mainFragment = MainFragment.newInstance(mArrayList);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mainFragment, MainFragment.TAG)
                .commit();
    }

    @Override
    public void reviewerPos(int pos) {

        /*Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("position", mArrayList.get(pos));
        startActivity(detailIntent);*/
    }

    @Override
    public void reviewer(Reviewer reviewer) {
        Log.d("Flag", "Reviewed= " + reviewer.getRating());


        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("reviewer", reviewer);
        startActivity(detailIntent);
    }

    @Override
    public void arrayReviewer(ArrayList<Reviewer> arrayReviewer) {
        Log.d("Flag","ME Array= " +arrayReviewer.get(0).getPlace());
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("arrayReviewer", arrayReviewer);
        startActivity(detailIntent);
    }


    public void writeToFile(Activity main, String _fileName, ArrayList<Reviewer> reviewer) {


        File external = main.getExternalFilesDir(null);


        File file = new File(external, _fileName);


        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            Log.d("FLAG", "I have data");
            os.writeObject(reviewer);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
