package com.mytestedapp;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.mytestedapp.rest.RestService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Injection {

    private static Injection sInjection;

    public static synchronized Injection getInstance() {
        if (sInjection == null) {
            sInjection = new Injection();
        }
        return sInjection;
    }

    public RestService provideRestService() {

        OkHttpClient okHttpClient = provideOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:9000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(RestService.class);
    }

    public OkHttpClient provideOkHttpClient() {

        ExecutorService executorService = provideExecutor();

        Dispatcher dispatcher = new Dispatcher(executorService);

        return new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .build();
    }

    public ExecutorService provideExecutor() {

        ThreadFactory threadFactory = new ThreadFactory() {

            AtomicInteger seq = new AtomicInteger();

            @Override
            public Thread newThread(@NonNull final Runnable r) {
                String name = "MyThread-" + seq.getAndIncrement();
                return new Thread(r, name);
            }
        };

        return new ThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                5L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                threadFactory) {

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                Log.d("MyTestedApp", "Will be executing on " + t.getName() + " runnable " + r);
            }
        };
    }

    @VisibleForTesting
    public static synchronized void setInstance(Injection injection) {
        sInjection = injection;
    }
}
