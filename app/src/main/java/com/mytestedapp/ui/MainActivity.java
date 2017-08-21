package com.mytestedapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mytestedapp.R;
import com.mytestedapp.Testing;

public class MainActivity extends AppCompatActivity {

    public static void start(Context ctx) {
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Testing.sActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Testing.sActivity = null;
    }
}
