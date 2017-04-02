package com.example.austin.inthemood;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Display locations on a map
 * Needs a Android Google Maps API key in AndroidManifest.xml
 *
 * TODO: Get moods from a controller depending on the context that the activity is launched
 *
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private final String FILENAME = "file.sav";
    private GoogleMap mMap;
    dataControler controller;
    private String trigger;
    private String emotion;
    private int lastWeek;
    private String launchedFrom;

    private HashMap<String, Float> hexColorToHUE = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        buildHashMap();

        trigger = getIntent().getStringExtra("trigger");
        emotion = getIntent().getStringExtra("emotion");
        lastWeek = getIntent().getIntExtra("lastweek", -1);
        launchedFrom = getIntent().getStringExtra("activity");

        //filterMoods();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Take the Mood hex colors and have a color for MakerOption objects
     * that are place in a GoogleMap
     */
    private void buildHashMap() {
        /**
         * TODO make bitmaps of these colors
         * https://stackoverflow.com/questions/14811579/how-to-create-a-custom-shaped-bitmap-marker-with-android-map-api-v2
         *
         else if(this.moodName.equals("Confusion")) {
         this.colorHexCode = "#cecece";

         else if (this.moodName.equals("Fear")){
         this.colorHexCode = "#8383a9";
         */

        hexColorToHUE.put("#f0391c", Float.valueOf(0.0f));
        //hexColorToHUE.put("#cecece", Float.valueOf(0.0f)) how to get grey
        hexColorToHUE.put("#9ae343", Float.valueOf(120.0f));
        //hexColorToHUE.put("#8383a9", Float.valueOf(120.0f));
        hexColorToHUE.put("#e8ef02", Float.valueOf(60.0f));
        hexColorToHUE.put("#03acca", Float.valueOf(180.0f));
        hexColorToHUE.put("#cd00ff", Float.valueOf(300.0f));
        hexColorToHUE.put("#cd00ff", Float.valueOf(330.0f));


    }

    private void filterMoods() {
        // TODO get and filter moods
        // then send to makeMarkers
    }

    private ArrayList<MarkerOptions> makeMarkers(ArrayList<Mood> moods) {
        ArrayList<MarkerOptions> list = new ArrayList<>();

        for (Mood mood : moods) {
            MarkerOptions markerOption = new MarkerOptions();

            if (mood.getLatLng() != null) {
                markerOption.position(mood.getLatLng());
                continue; // only display moods with locations
            }

            // don't need titles for user moods or moods within 5km
            if (launchedFrom.equals("MyFriends"))
                markerOption.title(mood.getOwnerName());

            float iconColor = hexColorToHUE.get(mood.getColorHexCode());

            markerOption.icon(BitmapDescriptorFactory.defaultMarker(iconColor));

            list.add(markerOption);
        }
        return list;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(sydney)
                .title("Marker")
                .icon(getMarkerIcon("#cecece"));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //a();
    }

    private void a(){
        for (int i =0; i <= 60; i++) {
            LatLng sydney = new LatLng(0, i);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(sydney)
                    .title("Marker")
                    .icon(BitmapDescriptorFactory.defaultMarker((float) i+299));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    private BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    // Save the data controller into the specified file.
    // Taken from: the CMPUT301 lonelyTwitter lab examples
    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(controller, writer);
            Log.i("gsonToJson", gson.toJson(controller));
            writer.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
