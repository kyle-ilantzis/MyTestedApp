package com.mytestedapp;


import com.mytestedapp.ui.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class LoginRobot {

    public LoginRobot() {
        assertEquals(LoginActivity.class, Testing.sActivity.getClass());
    }

    public LoginRobot username(String username) {
        onView(withId(R.id.edit_text_login_username))
                .perform(typeText(username));
        return this;
    }

    public LoginRobot password(String password) {
        onView(withId(R.id.edit_text_login_password))
                .perform(typeText(password));
        return this;
    }

    public LoginRobot login() {
        onView(withId(R.id.button_login_signin))
                .perform(click());
        return this;
    }

    public LoginRobot isInvalidUsernamePassword() {
        onView(withText(R.string.invalid_username_password))
                .inRoot(isDialog()).check(matches(isDisplayed()));
        return this;
    }

    public LoginRobot isNetworkError() {
        onView(withText(R.string.network_error))
                .inRoot(isDialog()).check(matches(isDisplayed()));
        return this;
    }

    public MainRobot isMainScreen() {
        return new MainRobot();
    }
}
