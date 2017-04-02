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
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
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
    private String triggerFilter;
    private String emotionFilter;
    private int lastWeekFilter;
    private ArrayList<Mood> moodList = new ArrayList<>();
    private String launchedFrom;

    private HashMap<String, Float> hexColorToHUE = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        buildHashMap();
        loadFromFile();
        moodList = controller.getCurrentUser().getMyMoodsList();
        filterMoods();

        triggerFilter = getIntent().getStringExtra("trigger");
        emotionFilter = getIntent().getStringExtra("emotion");
        lastWeekFilter = getIntent().getIntExtra("lastweek", -1);
        launchedFrom = getIntent().getStringExtra("activity");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Take the Mood hex colors and have a color for MakerOption objects
     * that are place in a GoogleMap
     *
     * TODO change the mood colour with #cecece to some orange colour
     */
    private void buildHashMap() {

        hexColorToHUE.put("#f0391c", BitmapDescriptorFactory.HUE_RED);
        hexColorToHUE.put("#cecece", BitmapDescriptorFactory.HUE_ORANGE);
        hexColorToHUE.put("#9ae343", BitmapDescriptorFactory.HUE_GREEN);
        hexColorToHUE.put("#8383a9", BitmapDescriptorFactory.HUE_AZURE);
        hexColorToHUE.put("#e8ef02", BitmapDescriptorFactory.HUE_YELLOW);
        hexColorToHUE.put("#03acca", BitmapDescriptorFactory.HUE_CYAN);
        hexColorToHUE.put("#cd00ff", BitmapDescriptorFactory.HUE_MAGENTA);
        hexColorToHUE.put("#ff006c", BitmapDescriptorFactory.HUE_ROSE);


    }

    /**
     * check the filtering flags use the data controller to give back a filtered list.
     */
    private void filterMoods() {
        // TODO get and filter moods
        // then send to makeMarkers

        if (emotionFilter != null)
            moodList = controller.filterByMood(emotionFilter, moodList);

        if (lastWeekFilter != -1)
            moodList = controller.filterByWeek(moodList);

        if (triggerFilter != null)
            moodList = controller.filterByTrigger(triggerFilter, moodList);
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

    private void addMarkers(ArrayList<MarkerOptions> list, GoogleMap googleMap) {
        mMap = googleMap;

        for (MarkerOptions option : list) {
            mMap.addMarker(option);
        }

        // set camera to last mood
        int lastIndex = list.size() - 1;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(lastIndex).getPosition()));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        addMarkers(makeMarkers(moodList), googleMap);
    }

    //Load data controller
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type objectType = new TypeToken<dataControler>() {}.getType();
            controller = gson.fromJson(in, objectType);

        } catch (FileNotFoundException e) {
            User firstUser = new User("admin", "admin");
            controller = new dataControler(firstUser);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
