package com.example.austin.inthemood;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.Index;

/**
 * Created by jyurick on 2017-03-18.
 */

public class ElasticSearchController {
    private static JestDroidClient client;

    // TODO we need a function which adds tweets to elastic search
    public static class AddTweetsTask extends AsyncTask<Mood, Void, Void> {

        @Override
        protected Void doInBackground(Mood... moods) {
            //verifySettings();

            for (Mood mood : moods) {
                Index index = new Index.Builder(mood).index("testing").type("moods").build();

                try {
                    // where is the client?
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the moods");
                }

            }
            return null;
        }
    }

    // TODO we need a function which gets tweets from elastic search
/*    public static class GetMoodsTask extends AsyncTask<String, Void, ArrayList<Moods>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
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
    }*/




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
