package com.example.austin.inthemood;

/**
 * Created by Austin on 2017-02-26.
 */


import java.util.ArrayList;

/**
 * The type User.
 */
public class User{
    private String name;
    private String password;
    private boolean loggedIn;
    private ArrayList<Mood> myMoodsList;
    private int myMoodCount;

    /**
     * Instantiates a new User.
     *
     * @param name the name
     */
    public User(String name, String password) {

        this.name = name;
        this.password = password;
        }
    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {

        return name;
        }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {

        this.name = name;
        }
    /**
     * Gets user's login password
     *
     * @return login password
     */
    public String getPassword() {

        return password;
    }

    /**
     * Sets user's login password
     *
     * @param password user's login password
     */

    public void setPassword(String password){

        this.password = password;
    }

    /**
     * log user in
     */
    public void logIn(){
        loggedIn = true;
    }

    /**
     * log user out
     */
    public void logOut(){
        loggedIn = false;
    }

    /**
     * Checks if user is logged in and returns True or false
     * @return loggedIn a boolean
     */

    public boolean isLoggedIn(){

        return loggedIn;
    }

    /**
     * gets array list of user's moods
     *
     * @return array list of moods
     */
    public ArrayList<Mood> getMyMoodsList(){
        return myMoodsList;
    }

    /**
     * adds mood to user's myMoodList
     *
     * @param mood a mood to add to list
     */
    public void addMood(Mood mood){
        myMoodsList.add(mood);
        myMoodCount += 1;
    }
}

