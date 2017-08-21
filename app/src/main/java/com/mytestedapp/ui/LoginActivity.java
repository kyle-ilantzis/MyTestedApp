package com.mytestedapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mytestedapp.Injection;
import com.mytestedapp.R;
import com.mytestedapp.Testing;
import com.mytestedapp.rest.LoginRequest;
import com.mytestedapp.rest.LoginResponse;
import com.mytestedapp.rest.RestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RestService mRestService;

    private EditText mUsername;
    private EditText mPassword;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRestService = Injection.getInstance().provideRestService();

        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.edit_text_login_username);
        mPassword = (EditText) findViewById(R.id.edit_text_login_password);
        findViewById(R.id.button_login_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginSignInClick();
            }
        });
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

    private void onLoginSignInClick() {

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        mProgressDialog = new ProgressDialog(this, R.style.DialogTheme);
        mProgressDialog.setMessage("Sign in you in...");
        mProgressDialog.show();

        LoginRequest loginRequest = new LoginRequest(username, password);

        mRestService.login(loginRequest).enqueue(
                new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                        Log.e("MyTestedApp", "login statusCode=" + response.code());

                        if (response.isSuccessful()) {
                            onLoginSuccess(response.body());
                        } else {
                            onLoginInvalidUsernamePassword();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                        String msg = t.getMessage();
                        String causeMsg = t.getCause() != null ? t.getCause().getMessage() : null;
                        Log.e("MyTestedApp", "Failed to login: " + msg + " :: " + causeMsg);
                        onLoginNetworkError();
                    }
                }
        );
    }

    private void onLoginSuccess(LoginResponse loginResponse) {
        mProgressDialog.dismiss();

        String accessToken = loginResponse.getAccessToken();
        // Save the access token to preferences ...

        setResult(Activity.RESULT_OK);
        MainActivity.start(this);
    }

    private void onLoginInvalidUsernamePassword() {
        mProgressDialog.dismiss();

        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage(R.string.invalid_username_password)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void onLoginNetworkError() {
        mProgressDialog.dismiss();

        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage(R.string.network_error)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}
