package com.example.austin.inthemood;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.example.austin.inthemood.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ExistingUserLoginTest {

    @Rule
    public ActivityTestRule<ExistingUserLogin> mActivityTestRule = new ActivityTestRule<>(ExistingUserLogin.class);

    @Test
    public void existingUserLoginTest() {
        ViewInteraction appCompatButton = onView(
allOf(withId(R.id.register), withText("Register"),
withParent(allOf(withId(R.id.activity_existing_user_login),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        appCompatButton.perform(click());
        
        ViewInteraction appCompatEditText = onView(
allOf(withId(R.id.user),
withParent(allOf(withId(R.id.activity_new_user_login),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        appCompatEditText.perform(replaceText("hello"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText2 = onView(
allOf(withId(R.id.password),
withParent(allOf(withId(R.id.activity_new_user_login),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        appCompatEditText2.perform(replaceText("world"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText3 = onView(
allOf(withId(R.id.confirm_password),
withParent(allOf(withId(R.id.activity_new_user_login),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        appCompatEditText3.perform(replaceText("world"), closeSoftKeyboard());
        
        ViewInteraction appCompatButton2 = onView(
allOf(withId(R.id.register), withText("Register"),
withParent(allOf(withId(R.id.activity_new_user_login),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        appCompatButton2.perform(click());
        
        }

    }
