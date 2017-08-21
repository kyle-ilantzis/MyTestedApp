package com.mytestedapp;


import android.support.test.InstrumentationRegistry;

import com.mytestedapp.ui.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class MainRobot {

    public MainRobot() {
        assertEquals(MainActivity.class, Testing.sActivity.getClass());
    }

    public MainRobot isProfile(String firstName, String lastName) {
        String greeting = InstrumentationRegistry.getTargetContext().getString(R.string.greeting, firstName, lastName);
        onView(withText(greeting)).check(matches(isDisplayed()));
        return this;
    }

    public MainRobot isTotalRidesTaken(String totalRides) {
        onView(withText(totalRides)).check(matches(isDisplayed()));
        return this;
    }

    public MainRobot ok() {
        onView(withId(R.id.button_main_ok)).perform(click());
        return this;
    }

    public MainRobot isNetworkError() {
        onView(withText(R.string.network_error))
                .inRoot(isDialog()).check(matches(isDisplayed()));
        return this;
    }

    public LoginRobot isLoginScreen() {
        return new LoginRobot();
    }
}
