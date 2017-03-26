package com.example.austin.inthemood;

import android.graphics.Color;

import java.util.Date;


/**Basic implementation of Mood class
  Will later be expanded on to include Picture/Location storage
  Also will later become abstract where moodName & Color are set by
  the subclass on its creation.
 */
public class Mood {
    Date moodDate;
    String moodDescription;
//    Color moodColor;
    String moodName;
    String ownerName;

    /**
     * Instantiates a new Mood.
     *
     * @param moodDate        the mood date
     * @param moodDescription the mood description
     */
    public Mood(Date moodDate, String moodDescription, String ownerName) {
        this.moodDate = moodDate;
        this.moodDescription = moodDescription;
        this.ownerName = ownerName;
    }

    /**
     * Instantiates a new Mood.
     */
    public Mood(String ownerName) {
        this.moodDate = new Date();
        this.moodDescription = "Test Description";
    }

    /**
     * Gets mood name.
     *
     * @return the mood name
     */
    public String getMoodName() {
        return moodName;
    }

    /**
     * Sets mood name.
     *
     * @param moodName the mood name
     */
    public void setMoodName(String moodName) {

        this.moodName = moodName;
    }

    public String getOwnerName(){
        return ownerName;
    }

    /**
     * Gets mood date.
     *
     * @return the mood date
     */
    public Date getMoodDate() {
        return moodDate;
    }

    /**
     * Sets mood date.
     *
     * @param moodDate the mood date
     */
    public void setMoodDate(Date moodDate) {

        this.moodDate = moodDate;
    }

    /**
     * Gets mood description.
     *
     * @return the mood description
     */
    public String getMoodDescription() {

        return moodDescription;
    }

    /**
     * Sets mood description.
     *
     * @param moodDescription the mood description
     */
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
