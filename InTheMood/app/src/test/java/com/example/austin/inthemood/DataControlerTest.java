package com.example.austin.inthemood;

/**
 * Created by olivier on 2017-03-11.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class DataControlerTest {
    /**
     * Test get userList.
     */
    @Test
    public void testGetUserList(){
        User user1 = new User("user1", "p1");
        dataControler controler = new dataControler(user1);
        assertEquals(1, controler.getUserList().size());
        assertEquals(user1, controler.getUserList().get(0));
        assertEquals("user1", controler.getUserList().get(0).getName());
    }
    /**
     * Test addToUserList.
     */
    @Test
    public void testAddToUserList(){
        User user1 = new User("user1", "p1");
        User user2 = new User("user2", "p2");
        dataControler controler = new dataControler(user1);
        controler.addToUserList(user2);
        assertEquals(2, controler.getUserList().size());
        assertEquals(user1, controler.getUserList().get(0));
        assertEquals(user2, controler.getUserList().get(1));
        assertEquals("user1", controler.getUserList().get(0).getName());
        assertEquals("user2", controler.getUserList().get(1).getName());
    }
    /**
     * Test verifyLogIn
     */
    @Test
    public void testVerifyLogIn(){
        User user1 = new User("user1", "p1");
        User user2 = new User("user2", "p2");
        dataControler controler = new dataControler(user1);
        controler.addToUserList(user2);
        //userName and password correct
        assertEquals(controler.verifyLogIn("user1", "p1"), user1);
        //userName incorrect and function returns null
        assertEquals(controler.verifyLogIn("user3", "p3"), null);
        //userName correct but password incorrect so funtion returns null
        assertEquals(controler.verifyLogIn("user1", "p2"), null);
    }
    /**
     * Test requestToFollow
     */
    @Test
    public void testRequestToFollow(){
        User user1 = new User("user1", "p1");
        User user2 = new User("user2", "p2");
        dataControler controler = new dataControler(user1);
        controler.addToUserList(user2);
        controler.requestToFollow(user1, user2.getName());
        //check user1's followRequests
        assertEquals(user1.getMyFollowRequests().get(0), user2);
        //check user2's followerRequests
        assertEquals(user2.getMyFollowerRequests().get(0), user1);
    }

    /**
     * Test grantFollowPermission
     */
    @Test
    public void testGrantFollowPermission(){
        User user1 = new User("user1", "p1");
        User user2 = new User("user2", "p2");
        dataControler controler = new dataControler(user1);
        controler.addToUserList(user2);
        controler.requestToFollow(user1, user2.getName());
        controler.grantFollowPermission(user2, user1.getName());
        assertEquals(user1.getMyFollowingList().get(0), user2);
        assertEquals(user2.getMyFollowersList().get(0), user1);
        assertEquals(user1.getMyFollowRequests().size(), 0);
        assertEquals(user2.getMyFollowerRequests().size(), 0);
    }
    /**
     * Test denyFollowPermission
     */
    @Test
    public void testDenyFollowPermission(){
        User user1 = new User("user1", "p1");
        User user2 = new User("user2", "p2");
        dataControler controler = new dataControler(user1);
        controler.addToUserList(user2);
        controler.requestToFollow(user1, user2.getName());
        controler.denyFollowPermission(user2, user1.getName());
        assertEquals(user2.getMyFollowerRequests().size(), 0);
        assertEquals(user1.getMyFollowRequests().size(), 0);
    }
    /**
     * Test searchForUserByName
     */
    @Test
    public void testSearchForUserByName(){
        User user1 = new User("user1", "p1");
        dataControler controler = new dataControler(user1);
        assertEquals(user1, controler.searchForUserByName("user1"));
        assertEquals(controler.searchForUserByName("user2"), null);
    }
    /**
     * Test sortMoodsByDate
     */
    @Test
    public void testSortMoodsByDate(){
        User user1 = new User("user1", "p1");
        dataControler controler = new dataControler(user1);
        Mood mood1 = new Mood("user1");
        Mood mood2 = new Mood("user1");
        Date date = new Date(97, 1, 23);
        mood2.setMoodDate(date);
        user1.addMood(mood1);
        user1.addMood(mood2);
        assertEquals(user1.getMyMoodsList().get(0), mood1);
        assertEquals(user1.getMyMoodsList().get(1), mood2);
        ArrayList<Mood> filteredMoods = controler.sortMoodsByDate(user1.getMyMoodsList());
        assertEquals(filteredMoods.get(0), mood2);
        assertEquals(filteredMoods.get(1), mood1);
    }

    /**
     * Test filterByMood
     */
    @Test
    public void testFilterByMood(){
        User user1 = new User("user1", "p1");
        dataControler controler = new dataControler(user1);
        Mood mood1 = new Mood("user1");
        Mood mood2 = new Mood("user1");
        mood1.setMoodName("happy");
        mood2.setMoodName("grumpy");
        user1.addMood(mood1);
        user1.addMood(mood2);
        ArrayList<Mood> filteredMoodList = controler.filterByMood("happy", user1.getMyMoodsList());
        assertEquals(mood1, filteredMoodList.get(0));
        assertEquals(filteredMoodList.size(), 1);
    }
    /**
     * Test filterByWeek method and that moods are sorted by date
     */
    @Test
    public void testFilterByWeek(){
        User user1 = new User("user1", "p1");
        dataControler controler = new dataControler(user1);
        Mood mood1 = new Mood("user1");
        Mood mood3 = new Mood("user1");
        Date dateOld = new Date(98, 3, 8);
        mood1.setMoodDate(dateOld);
        user1.addMood(mood3);
        user1.addMood(mood1);
        assertEquals(user1.getMyMoodsList().size(), 2);
        ArrayList<Mood> filteredList = controler.filterByWeek(user1.getMyMoodsList());
        assertEquals(filteredList.size(), 1);
        assertEquals(filteredList.get(0), mood3);
    }

    /**
     * Test filterByTrigger
     */
    @Test
    public void testFilterByTrigger(){
        User user1 = new User("user1", "p1");
        dataControler controler = new dataControler(user1);
        Mood mood1 = new Mood("user1");
        Mood mood2 = new Mood("user1");
        mood2.setMoodDescription("grumpy");
        user1.addMood(mood1);
        user1.addMood(mood2);
        ArrayList<Mood> filteredMoodList = controler.filterByTrigger("Test Description", user1.getMyMoodsList());
        assertEquals(mood1, filteredMoodList.get(0));
        assertEquals(filteredMoodList.size(), 1);
    }

}
