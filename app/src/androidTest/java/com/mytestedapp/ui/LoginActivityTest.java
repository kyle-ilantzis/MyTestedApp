package com.mytestedapp.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytestedapp.InjectionRobot;
import com.mytestedapp.LoginRobot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class, false, false);

    @Test
    public void it_should_login_with_correct_username_password() {

        new InjectionRobot()
                .setup()
                .setUsernamePassword("admin", "password");

        mActivityTestRule.launchActivity(new Intent(Intent.ACTION_MAIN));

        new LoginRobot()
                .username("admin")
                .password("password")
                .login()
                .isMainScreen();
    }

    @Test
    public void it_should_show_invalid_username_password_message() {

        new InjectionRobot()
                .setup()
                .setUsernamePassword("admin", "password");

        mActivityTestRule.launchActivity(new Intent(Intent.ACTION_MAIN));

        new LoginRobot()
                .username("admin")
                .password("badPassword")
                .login()
                .isInvalidUsernamePassword();
    }

    @Test
    public void it_should_show_network_error_message() {

        new InjectionRobot()
                .setup()
                .setUsernamePasswordNetworkError();

        mActivityTestRule.launchActivity(new Intent(Intent.ACTION_MAIN));

        new LoginRobot()
                .username("admin")
                .password("badPassword")
                .login()
                .isNetworkError();
    }
}