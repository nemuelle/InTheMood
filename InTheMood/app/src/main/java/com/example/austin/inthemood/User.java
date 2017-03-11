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
    private ArrayList<Mood> myMoodsList;
    private ArrayList<User> myFollowersList;
    private ArrayList<User> myFollowingList;
    private ArrayList<User> myFollowerRequests;
    private ArrayList<User> myFollowRequests;
    private ArrayList<Mood> myFollowedMoods;
    private int myMoodCount;

    /**
     * Instantiates a new User.
     *
     * @param name the name
     */
    public User(String name, String password) {

        this.name = name;
        this.password = password;
        myMoodsList = new ArrayList<Mood>();
        myFollowersList = new ArrayList<User>();
        myFollowingList = new ArrayList<User>();
        myFollowerRequests = new ArrayList<User>();
        myFollowRequests = new ArrayList<User>();
        myFollowedMoods = new ArrayList<Mood>();
        myMoodCount = 0;

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

    /**
     * removes mood from user's myMoodList
     *
     * @param mood a mood to remove from list
     */
    public void removeMood(Mood mood){
        myMoodsList.remove(mood);
        myMoodCount -= 1;
    }

    /**
     *gets user's (owner's) followers list (people following him)
     *
     * @return list of followers following user (owner)
     */
    public ArrayList<User> getMyFollowersList(){
        return myFollowersList;
    }

    /**
     *adds a follower to user's (owner's) followers list
     *
     * @param follower person following the user (owner)
     */
    public void addToMyFollowersList(User follower){
        myFollowersList.add(follower);
    }

    /**
     *gets user's (owner's) list of people the user (owner) is following
     *
     * @return list of people the user is following
     */
    public ArrayList<User> getMyFollowingList(){
        return myFollowingList;
    }

    /**
     * adds a followed user that the user (owner) is following to user's (owner's) list
     *
     * @param followedUser user being followed by user (owner)
     */
    public void addToMyFollowingList(User followedUser){
        myFollowingList.add(followedUser);
    }

    /**
     *gets the list of user's a user (owner) wishes to follow
     *
     * @return the list of user's a user (owner) is requesting to follow
     */
    public ArrayList<User> getMyFollowRequests(){
        return myFollowRequests;
    }

    /**
     * adds a user being requested to be follow by user (owner) to user's (owner's) list of follow requests
     *
     * @param userBeingRequestedToFollow a user being requested to be followed by user (owner)
     */
    public void addToMyFollowRequests(User userBeingRequestedToFollow) {
        myFollowRequests.add(userBeingRequestedToFollow);
    }

    /**
     * User (owner) removes one of the users he wished to follow from his myFollowRequests list
     *
     * @param userBeingRequestedToFollow user that user (owner) wished to follow
     */
    public void removeFollowRequest(User userBeingRequestedToFollow){
        myFollowRequests.remove(userBeingRequestedToFollow);
    }

    /**
     * gets list of users requesting to follow user (owner)
     *
     * @return the list of users requesting to follow user (owner)
     */
    public ArrayList<User> getMyFollowerRequests(){
        return myFollowerRequests;
    }

    /**
     * adds a requesting follower to user's (owner's) myFollowerRequests list
     *
     * @param requestingUser user requesting to follow user (owner)
     */
    public void addToMyFollowerRequests(User requestingUser){
        myFollowerRequests.add(requestingUser);
    }

    /**
     * remove a user requesting to follow user (owner) from myFollowerRequests list
     *
     * @param requestingUser a user requesting to follow user (owner)
     */
    public void removeFollowerRequest(User requestingUser){
        myFollowerRequests.remove(requestingUser);
    }

    /**
     * searches through a user's (owner's) myFollowingList and finds all tweets of those users and adds to
     * myFollowedMoods. This way it fetches all moods everytime this function is called so myFollowedMoods is always up to date.
     *
     * @return myFollowedMoods a list of moods of people im following
     */
    public ArrayList<Mood> getMyFollowedMoods(){
        myFollowedMoods.clear();
        for (int i = 0; i < myFollowingList.size(); i++){
            for (int k = 0; k < myFollowingList.get(i).getMyMoodsList().size(); k++){
                myFollowedMoods.add(myFollowingList.get(i).getMyMoodsList().get(k));
            }
        }
        return myFollowedMoods;
    }
}

