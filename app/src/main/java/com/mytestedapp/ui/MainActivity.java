package com.mytestedapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mytestedapp.Injection;
import com.mytestedapp.R;
import com.mytestedapp.Testing;
import com.mytestedapp.rest.ProfileResponse;
import com.mytestedapp.rest.RestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RestService mRestService;

    private TextView mGreeting;
    private TextView mTotalRidesTaken;
    private TextView mTotalRides;
    private Button mOkBtn;

    private ProgressDialog mProgressDialog;

    public static void start(Context ctx) {
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRestService = Injection.getInstance().provideRestService();

        setContentView(R.layout.activity_main);

        mGreeting = (TextView) findViewById(R.id.text_main_greeting);
        mTotalRidesTaken = (TextView) findViewById(R.id.text_main_total_rides_taken);
        mTotalRides = (TextView) findViewById(R.id.text_main_total_rides);

        mOkBtn = (Button) findViewById(R.id.button_main_ok);
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getProfile();
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

    private void getProfile() {

        mGreeting.setVisibility(View.INVISIBLE);
        mTotalRidesTaken.setVisibility(View.INVISIBLE);
        mTotalRides.setVisibility(View.INVISIBLE);
        mOkBtn.setVisibility(View.INVISIBLE);

        mProgressDialog = new ProgressDialog(this, R.style.DialogTheme);
        mProgressDialog.setMessage("Loading profile ...");
        mProgressDialog.show();

        mRestService.profile().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                Log.e("MyTestedApp", "profile statusCode=" + response.code());

                if (response.isSuccessful()) {
                    onProfileSuccess(response.body());
                } else {
                    onProfileError();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                String msg = t.getMessage();
                String causeMsg = t.getCause() != null ? t.getCause().getMessage() : null;
                Log.e("MyTestedApp", "Failed to load profile: " + msg + " :: " + causeMsg);
                onProfileError();
            }
        });

    }

    private void onProfileSuccess(ProfileResponse profileResponse) {
        mProgressDialog.dismiss();

        mGreeting.setVisibility(View.VISIBLE);
        mTotalRidesTaken.setVisibility(View.VISIBLE);
        mTotalRides.setVisibility(View.VISIBLE);
        mOkBtn.setVisibility(View.VISIBLE);

        mGreeting.setText(getString(R.string.greeting, profileResponse.getFirstName(), profileResponse.getLastName()));
        mTotalRides.setText(profileResponse.getTotalRides());
    }

    private void onProfileError() {
        mProgressDialog.dismiss();

        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage(R.string.network_error)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create()
                .show();
    }
}
