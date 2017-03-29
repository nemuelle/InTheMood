package com.example.austin.inthemood;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by annaholowaychuk on 2017-03-26.
 */

public class ElasticSearchControllerTest extends ActivityInstrumentationTestCase2 {

    public ElasticSearchControllerTest() {
        super(com.example.austin.inthemood.ExistingUserLogin.class);
    }

    @Test
    public void TestAddUserGetUser() {
        User testman = new User("testman", "testpassword");
        ElasticSearchController.AddUserTask addUser = new ElasticSearchController.AddUserTask();
        addUser.execute(testman);

        ElasticSearchController.GetUserByName getUser = new ElasticSearchController.GetUserByName();
        getUser.execute(testman.getName());
        User newman = null;
        try {
            newman = getUser.get().get(0);
            assert(true);
        } catch (Exception e) {
            Log.i("Error", "Failed to get user by name");
        }
        assertEquals(testman.getName(), newman.getName());


    }

}
