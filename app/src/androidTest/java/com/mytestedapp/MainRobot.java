package com.mytestedapp;


import com.mytestedapp.ui.MainActivity;

import static org.junit.Assert.assertEquals;

public class MainRobot {

    public MainRobot() {
        assertEquals(MainActivity.class, Testing.sActivity.getClass());
    }
}
