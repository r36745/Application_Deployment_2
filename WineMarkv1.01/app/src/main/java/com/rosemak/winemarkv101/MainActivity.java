package com.rosemak.winemarkv101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends Activity implements ReviewerFragment.OnButtonClickListener, MainFragment.OnListClickListener {
    public ArrayList<Reviewer> mArrayList;
    public static final String NAME_LIST = "arrayList";
    private static final String MAIN = "mainActivity.Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        Intent intent = getIntent();

        if (savedInstanceState == null) {
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
        } else if (id == R.id.action_wine_search) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void review(Reviewer review) {
        Log.d(MAIN, "Main Activity Rating= " + review.getRating());
        mArrayList.add(review);
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
        Log.d("Flag", "Reviewed= " +reviewer.getRating());
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("reviewer", reviewer);
        startActivity(detailIntent);
    }
}
