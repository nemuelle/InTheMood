package com.example.austin.inthemood;

import java.util.ArrayList;

/**
 * Created by olivier on 2017-03-10.
 */

public class dataControler {
    private ArrayList<User> userList;
    private int userCount;


    /**
     * adds user to list of registered users (userList)
     * @param user user being added
     */
    public void addToUserList(User user){
        userList.add(user);
        userCount += 1;
    }

    /**
     * checks to see if a user is registered (is in userList) and his corresponding password matches database
     *
     * @param name name being checked for in database
     * @param password corresponding password being checked for in database
     * @return User if login successful or null if unsuccessful
     */
    public User verifyLogIn(String name, String password){
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getName() == name) {
                if (userList.get(i).getPassword() == password) {
                    return userList.get(i);

                }
            }
        }
        return null;
    }

    /**
     * grant user (followerName) follow permission to follow user (owner)
     *
     * @param user user being requested to follow by followerName
     * @param followerName user requesting to follow user (owner)
     */
    public void grantFollowPermission(User user, String followerName){
        user.addToMyFollowersList(searchForUserbyName(followerName));
        user.removeFollowerRequest(searchForUserbyName(followerName));
        searchForUserbyName(followerName).removeFollowRequest(user);
        searchForUserbyName(followerName).addToMyFollowingList(user);
    }

    /**
     * deny user (followerName) requesting follow permission to follow user (owner)
     *
     * @param user user being requested to follow by followerName
     * @param followerName user requesting to follow user (owner)
     */
    public void denyFollowPermission(User user, String followerName){
        user.removeFollowerRequest(searchForUserbyName(followerName));
        searchForUserbyName(followerName).removeFollowRequest(user);
    }

    /**
     * search userList for a user by name, return null if not found
     *
     * @param name of user being searched for
     * @return User with name name, return null if user not in userList
     */
    public User searchForUserbyName(String name) {
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getName() == name){
                return userList.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param UserName
     * @param WordFilter
     * @param DateFilter
     * @param MoodFilter
     * @param Mood
     */
    /**public getMoods(string UserName,Boolean WordFilter,Boolean DateFilter,Boolean MoodFilter,string Mood)**/


}
