package com.example.alexeyz.fragmenttesting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SelinaService {

    SelinaApi api;

    static private SelinaService instance;
    static public SelinaService getInstance() {
        if(instance == null) {
            instance = new SelinaService();
        }

        return instance;
    }

    BehaviorSubject<Boolean> stateSubject = BehaviorSubject.createDefault(false);

    public BehaviorSubject<Boolean> getStateObservable() {
        return stateSubject;
    }

    private SelinaService() {
        api = buildRetrofitRx().create(SelinaApi.class);
    }

    public Observable<List<Location>> updateLocations() {
        return api.getLocationsRx()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
                //.doOnNext(list -> Repository.getInstance().setLocations(list));
    }

    public Retrofit buildRetrofitRx() {
        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder().addHeader("User-Agent", "Selina-Android-App");

                String authToken = "token";//Repository.getInstance().userToken;

                if (authToken != null) {
                    requestBuilder.addHeader("Authorization", "Token " + authToken);
                }

                return chain.proceed(requestBuilder.build());
            }
        };

        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .writeTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .connectTimeout(0, TimeUnit.SECONDS);

        builder.interceptors().add(interceptor);

//        if (BuildConfig.DEBUG) {
//            builder.interceptors().add(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
//        }

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(4);
        dispatcher.setMaxRequestsPerHost(4);

        builder.dispatcher(dispatcher);

        OkHttpClient client = builder.build();

        return new Retrofit.Builder()
                .baseUrl(SelinaApi.ENDPOINT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .enableComplexMapKeySerialization() // necessary to properly serialize maps which have non-primitive values
                .create();
    }
}