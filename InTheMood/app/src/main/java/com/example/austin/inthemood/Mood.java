package com.example.austin.inthemood;

import android.graphics.Color;

import java.util.Date;

/**
 * Created by samue_000 on 2017-02-26.
 */

/*Basic implementation of Mood class
  Will later be expanded on to include Picture/Location storage
  Also will later become abstract where moodName & Color are set by
  the subclass on its creation.
 */
public class Mood {
    Date moodDate;
    String moodDescription;
    //Color moodColor;
    String moodName;

    public Mood(Date moodDate, String moodDescription) {
        this.moodDate = moodDate;
        this.moodDescription = moodDescription;
    }
    public Mood() {
        this.moodDate = new Date();
        this.moodDescription = "Test Description";
    }

    public String getMoodName() {
        return moodName;
    }

    public void setMoodName(String moodName) {

        this.moodName = moodName;
    }

    public Date getMoodDate() {
        return moodDate;
    }

    public void setMoodDate(Date moodDate) {

        this.moodDate = moodDate;
    }

    public String getMoodDescription() {

        return moodDescription;
    }

    public void setMoodDescription(String moodDescription) {
        if (moodDescription.length() >= 20)
        {
            this.moodDescription = moodDescription.substring(0,20);
        }else{
            this.moodDescription = moodDescription;
        }

    }

    public String toString(){
        return  "Mood on " + this.moodDate + " was " + this.moodName + " because " +
                this.moodDescription;
    }

}
