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
     * checks to see if a user is registered (is in userList)
     *
     * @param name name of user being checked for
     * @return true or false
     */
    public boolean verifyUserName(String name){
        for (int i = 0; i < userList.size(); i++){
            if (userList.get(i).getName() == name) {
                return true;
            }
        }
        return false;
    }



    /**public getMoods(String name, Boolean WordFilter, Boolean DateFilter, Boolean MoodFilter, String Mood){

    }**/
    /**public boolean grantFollowPermission(String name,String FollowerName){

     }**/

    /**public searchForUsers(String SearchString){

    }**/


}
