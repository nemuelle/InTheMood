package com.example.austin.inthemood;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by annaholowaychuk on 2017-03-26.
 */

public class ElasticSearchTest {

    @Test
    public void TestAddMood(){
        Date today = new Date();
        Mood test = new Mood(today, "new mood test");
        ElasticSearchController.AddMoodsTask addMoods = new ElasticSearchController.AddMoodsTask();
        addMoods.execute(test);
        assertEquals(1,1);

    }

    @Test
    public void TestAddUser(){

    }

    @Test
    public void TestAddUserFollowers(){

    }

    public void TestGetMoodsForUser(){

    }
}
