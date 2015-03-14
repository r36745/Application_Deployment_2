package com.rosemak.winemarkv101;

import java.io.Serializable;

/**
 * Created by stevierose on 3/9/15.
 */
public class Reviewer implements Serializable {

    public String place;
    public String notes;
    public float rating;
    public double mLat;
    public double mLng;
    public String mImg;

    public Reviewer(String _place, float _rating) {

        place = _place;
        rating = _rating;
    }

    public float getRating() {
        return rating;
    }

    public String getNotes() {
        return notes;
    }

    public String getPlace() {
        return place;
    }

    public double getmLng() {
        return mLng;
    }

    public double getmLat() {
        return mLat;
    }

    public String getmImg() {
        return mImg;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setmLng(double mLng) {
        this.mLng = mLng;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }

    @Override
    public String toString() {
        return place;
    }
}
