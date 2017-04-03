package com.example.austin.inthemood;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by annaholowaychuk on 2017-03-13.
 */

public class AddDeleteMoodTest extends ActivityInstrumentationTestCase2<MyMoods> {
    private Solo solo;
    public AddDeleteMoodTest() {
        super(com.example.austin.inthemood.MyMoods.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testAddDeleteMood() {
        solo.assertCurrentActivity("Wrong Activity", MyMoods.class);
        solo.clickOnButton("New Mood");

        solo.assertCurrentActivity("Wrong Activity", AddEditMood.class);
        solo.enterText((EditText) solo.getView(R.id.addEditMoodsTriggerText), "UniqueTrigger");
        solo.clickOnButton("Save Mood");
        solo.assertCurrentActivity("Wrong Activity", MyMoods.class);
        assertTrue(solo.waitForText("UniqueTrigger"));
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", AddEditMood.class);
        solo.clickOnButton("Delete Mood");
        solo.assertCurrentActivity("Wrong Activity", MyMoods.class);
        assertFalse(solo.waitForText("UniqueTrigger"));


    }


    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
