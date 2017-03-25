package com.example.austin.inthemood;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;

import io.searchbox.action.Action;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by jyurick on 2017-03-18.
 */

public class ElasticSearchController {
    private static JestDroidClient client;
    private String query;

    // TODO we need a function which adds moods to elastic search
    public static class AddMoodsTask extends AsyncTask<Mood, Void, Void> {



        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();


            for (Mood mood : moods) {


                try {
                    // where is the client?
                    DocumentResult result = client.execute(new Index.Builder(mood).index("testing").type("mood").build());
                    Log.i("Error", "We sent the moods!");
                    if (result.isSucceeded() == false) {
                        Log.i("Error", "Elastic search couldn't add mood");
                    }


                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the moods");
                }
            }

            return null;
        }
    }


    // TODO we need a function which gets moods for a given user from elastic search
    public static class GetMoodsForUser extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... user) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

                // TODO Build the query



            try {
               // TODO get the results of the query
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return moods;
        }
    }




    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }

    }
}
