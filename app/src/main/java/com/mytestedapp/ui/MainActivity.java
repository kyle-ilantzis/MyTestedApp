package com.mytestedapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mytestedapp.R;

public class MainActivity extends AppCompatActivity {

    public static void start(Context ctx) {
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
