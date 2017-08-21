package com.mytestedapp.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytestedapp.InjectionRobot;
import com.mytestedapp.MainRobot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void it_should_load_profile() {

        new InjectionRobot()
                .setup()
                .setProfile("Bart", "Jacobs", "1");

        mActivityTestRule.launchActivity(new Intent(Intent.ACTION_VIEW));

        new MainRobot()
                .isProfile("Bart", "Jacobs")
                .isTotalRidesTaken("1");
    }

    @Test
    public void it_should_network_error_message() {

        new InjectionRobot()
                .setup()
                .setProfileNetworkError();

        mActivityTestRule.launchActivity(new Intent(Intent.ACTION_VIEW));

        new MainRobot()
                .isNetworkError();
    }

}