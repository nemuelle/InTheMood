package com.example.austin.inthemood;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.action.Action;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by jyurick on 2017-03-18.
 */

public class ElasticSearchController {
    private static JestDroidClient client;
    private static String index = "cmput301w17t15";
    private static String user_type = "user";
    private static String mood_type = "mood";




    // TODO we need a function which adds moods to elastic search
    public static class AddMoodTask extends AsyncTask<Mood, Void, Void> {
        /*
        USAGE:
        Mood test = new Mood();
        ElasticSearchController.AddMoodTask addMoods = new ElasticSearchController.AddMoodsTask();
        addMoods.execute(test);
         */


        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();

            for (Mood mood : moods) {
                try {
                    // where is the client?
                    DocumentResult result = client.execute(new Index.Builder(mood).index(index).type(mood_type).build());
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



    public static class AddUserTask extends AsyncTask<User, Void, String> {
        /*
        USAGE:
        User test = new User("Test","test");
        ElasticSearchController.AddUserTask addUser = new ElasticSearchController.AddUserTask();
        addUser.execute(test);
         */

        /**
         * Adds user to Server
         * @param users the user you want to add to the server
         * @return returns a string containing the unique id generated for the user by the server
         */
        @Override
        protected String doInBackground(User... users) {
            verifySettings();
            String userID = new String();
            for (User user : users) {
                try {
                    // where is the client?
                    DocumentResult result = client.execute(new Index.Builder(user).index(index).type(user_type).build());
                    Log.i("Error", "We sent the user");
                    if (result.isSucceeded() == false) {
                        Log.i("Error", "Elastic search couldn't add mood");
                    }else {
                        userID = result.getId();
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the moods");
                }
            }
            return userID;
        }
    }

    // TODO we need a function which gets moods for a given user from elastic search
    public static class GetMoodsForUser extends AsyncTask<String, Void, ArrayList<Mood>> {
        /*
        USAGE:
        String userName = "test";
        ElasticSearchController.GetMoodsForUser getMoodsTask = new ElasticSearchController.GetMoodsForUser();
        getMoodsTask.execute(userName);

        try {
            SortedMoodList = getMoodsTask.get();
        } catch (Exception e) {
            Log.i("Error","Failed to get Moods from async controller");
        }
         */

        /**
         * Gets moods for a user by their username
         * @param username
         * @return list of moods logged by the user
         */
        @Override
        protected ArrayList<Mood> doInBackground(String... username) {
            verifySettings();
            ArrayList<Mood> moods = new ArrayList<Mood>();
            // TODO Build the query
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match\" : {\n" +
                    "            \"ownerName\" : \n" +
                    "                \""+username[0]+"\"\n" +
                    "            }\n" +
                    "    }\n" +
                    "}";

            System.out.print(query);
            Search search = new Search.Builder(query)
                    .addIndex(index)
                    .addType(mood_type)
                    .build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<Mood> foundMoods = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundMoods);
                }
                else {
                    Log.i("Error", "The search query failed to find any moods that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return moods;
        }
    }

    public static class GetUserByName extends AsyncTask<String, Void, User> {
        /*
        USAGE:
        String userName = "test";
        ElasticSearchController.GetUserByName getUserTask = new ElasticSearchController.GetUserByName();
        getUserTask.execute(userName);

        try {
            SortedMoodList = getMoodsTask.get();
        } catch (Exception e) {
            Log.i("Error","Failed to get Moods from async controller");
        }
         */

        /**
         * Gets the User object from server by their name
         * @param username
         * @return User object
         */
        @Override
        protected User doInBackground(String... username) {
            verifySettings();
            //ArrayList<User> users = new ArrayList<User>();
            User user = new User("","");
            // TODO Build the query
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match\" : {\n" +
                    "            \"name\" : \n" +
                    "                \""+username[0]+"\"\n" +
                    "            }\n" +
                    "    }\n" +
                    "}";

            // TODO Build the query
            if (username[0] == ""){
                query = "";
            }
            System.out.print(query);
            Search search = new Search.Builder(query)
                    .addIndex(index)
                    .addType(user_type)
                    .build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    ///List<User> foundUsers = result.getSourceAsObjectList(User.class);
                    //users.addAll(foundUsers);
                    user = result.getSourceAsObject(User.class);
                    Log.i("Error", "We got the user!");
                }
                else {
                    Log.i("Error", "The search query failed to find any users that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            //user = users.get(0);
            return user;
        }
    }

    public static class SyncUserTask extends AsyncTask<User, Void, Boolean> {
        /*
        USAGE:
        Mood test = new Mood();
        ElasticSearchController.AddMoodTask addMoods = new ElasticSearchController.AddMoodsTask();
        addMoods.execute(test);
         */
        @Override
        protected Boolean doInBackground(User... users) {
            verifySettings();
            Boolean wasSuccess = new Boolean(false);
            for (User user : users) {
                try {
                    // where is the client?
                    String userID = user.getElasticSearchID();
                    DocumentResult result = client.execute(new Index.Builder(user).index(index).type(user_type).id(userID).build());
                    wasSuccess = result.isSucceeded();
                    Log.i("Error", "We synced the user!");
                    if (result.isSucceeded() == false) {
                        Log.i("Error", "Elastic search couldn't sync the user");
                    }
                    return result.isSucceeded();
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to sync the user");
                    return false;
                }
            }
            return wasSuccess;
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
