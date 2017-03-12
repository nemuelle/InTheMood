package com.example.austin.inthemood;

import android.app.Application;

/**
 * Created by olivier on 2017-03-12.
 */

/**
 * pulled from http://stackoverflow.com/questions/8288405/android-persist-object-across-activities on March 12, 2017
 *
 * class used to share data controller between activites
 */
public class MyApp extends Application{
    private dataControler controller;

    public dataControler getController(){
        return controller;
    }

}
