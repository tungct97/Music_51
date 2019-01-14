package com.framgia.music_51.data.resource.API;

import com.framgia.music_51.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoundCloudApiUtils {
    private static final String BASE_URL = "https://api-v2.soundcloud.com/";
    private static final String API_KEY = "client_id";
    private static final int TIME_OUT = 5000;
    private static final int CONNECT_TIME_OUT = 10000;
    private static Retrofit sRetrofit;

    public static Retrofit getClient() {
        if (sRetrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return sRetrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();
                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter(API_KEY, BuildConfig.API_KEY)
                                .build();
                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }
}
