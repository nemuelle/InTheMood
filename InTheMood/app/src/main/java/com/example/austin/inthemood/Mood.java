package com.example.austin.inthemood;

import android.graphics.Bitmap;
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
    String colorHexCode;
    String moodScenario;
    String moodName;
    String ownerName;
    Bitmap moodImg;

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
        this.inferMoodColor();
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

    public String getMoodScenario() {
        return moodScenario;
    }

    public void setmoodScenario(String scenario) {
        moodScenario = scenario;
    }
    public String getColorHexCode() {
        return colorHexCode;
    }


    public Bitmap getMoodImg() {
        return moodImg;
    }

    public void setMoodImg(Bitmap moodImg) {
        this.moodImg = moodImg;
    }


    public String toString(){
        return  "Mood on " + this.moodDate + " was " + this.moodName + " because " +
                this.moodDescription;
    }

    private void inferMoodColor(){
        if (this.moodName.equals("Anger")){
            this.colorHexCode = "#f0391c";

        }
        else if(this.moodName.equals("Confusion")) {
            this.colorHexCode = "#cecece";

        }
        else if (this.moodName.equals("Disgust")){
            this.colorHexCode = "#9ae343";
        }
        else if (this.moodName.equals("Fear")){
            this.colorHexCode = "#8383a9";
        }
        else if (this.moodName.equals("Happiness")){
            this.colorHexCode = "#e8ef02";
        }
        else if (this.moodName.equals("Sadness")){
            this.colorHexCode = "#03acca";
        }
        else if (this.moodName.equals("Shame")){
            this.colorHexCode = "#cd00ff";

        }
        else if (this.moodName.equals("Surprise")){
            this.colorHexCode = "#ff006c";

        }

    }
}
