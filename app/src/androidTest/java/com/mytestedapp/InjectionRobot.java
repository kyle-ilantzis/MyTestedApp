package com.mytestedapp;


import android.support.annotation.NonNull;

import com.mytestedapp.rest.LoginRequest;
import com.mytestedapp.rest.LoginResponse;
import com.mytestedapp.rest.RestService;

import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.internal.matchers.Not;

import java.io.IOException;
import java.net.SocketException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class InjectionRobot {

    private Injection mInjection;
    private RestService mRestService;

    private static <T> Call<T> makeSuccessCall(final T body) {
        return new Call<T>() {
            @Override
            public Response<T> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(@NonNull Callback<T> callback) {

                callback.onResponse(this, Response.success(body));

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<T> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    private static <T> Call<T> makeErrorCall(final int statusCode, final String body) {
        return new Call<T>() {
            @Override
            public Response<T> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(@NonNull Callback<T> callback) {

                ResponseBody responseBody = ResponseBody.create(MediaType.parse("application/json"), body);
                callback.onResponse(this, Response.<T>error(statusCode, responseBody));

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<T> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    private static <T> Call<T> makeFailureCall(final Throwable t) {
        return new Call<T>() {
            @Override
            public Response<T> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(@NonNull Callback<T> callback) {

                callback.onFailure(this, t);

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<T> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    public InjectionRobot setup() {

        mInjection = spy(new Injection());
        Injection.setInstance(mInjection);

        setupRestService();

        return this;
    }

    public InjectionRobot setupRestService() {

        if (mRestService != null) {
            return this;
        }

        mRestService = mock(RestService.class);

        doReturn(mRestService).when(mInjection).provideRestService();

        return this;
    }

    public InjectionRobot setUsernamePassword(final String username, final String password) {

        Matcher<LoginRequest> matcher = new ArgumentMatcher<LoginRequest>() {
            @Override
            public boolean matches(Object argument) {
                LoginRequest loginRequest = (LoginRequest) argument;
                return username.equals(loginRequest.getUsername()) && password.equals(loginRequest.getPassword());
            }
        };

        LoginResponse loginResponse = new LoginResponse("ABC000000XYZ");

        doReturn(makeSuccessCall(loginResponse)).when(mRestService).login(argThat(matcher));

        Matcher<LoginRequest> notMatcher = new Not(matcher);

        doReturn(InjectionRobot.<LoginResponse>makeErrorCall(400, "{error: \"invalid credentials\"}")).when(mRestService).login(argThat(notMatcher));

        return this;
    }

    public InjectionRobot setUsernamePasswordNetworkError() {

        doReturn(InjectionRobot.<LoginResponse>makeFailureCall(new SocketException("test bad network"))).when(mRestService).login(Matchers.<LoginRequest>any());

        return this;
    }
}
