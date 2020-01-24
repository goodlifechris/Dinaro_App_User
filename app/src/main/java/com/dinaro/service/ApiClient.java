package com.dinaro.service;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Base64;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //    public static String BASE_URL = "http://172.16.0.101/PROJECTS/DinaroMobilePaymentApp-19013492/website/public/api/";
    //public static String BASE_URL = "http://52.221.54.107/PROJECTS/Dianaro/trunk/public/api/";
    public static String BASE_URL = "http://ec2-3-8-105-184.eu-west-2.compute.amazonaws.com/api";
    private static WeakReference<Context> mContext;
    private static ApiClient _client = null;
    private static Api api_;
    String credentials = "admin" + ":" + "admin";

    /*public static Api getInstance() {
        if (api_ == null) {
            OkHttpClient okHttpClient = newback OkHttpClient().newBuilder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = newback Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build();
            Api api_ = retrofit.create(Api.class);
            return api_;
        } else
            return api_;
    }*/
    final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    private Api _service;

    ApiClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor)
                .connectTimeout(80 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(80 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(2 * 60 * 1000, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("basicAuth", basic)
                                /* .addHeader("token", PrefManager.getStringPreferences(mContext.get(), AppConstant.TOKEN))
                                 .addHeader("_id", PrefManager.getStringPreferences(mContext.get(), AppConstant.USER_ID))*/
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        _service = retrofit.create(Api.class);
    }

    static Api current(WeakReference<Context> mContext) {
        ApiClient.mContext = mContext;
        return getInstance().getService();
    }

    private static ApiClient getInstance() {
        _client = new ApiClient();
        return _client;
    }

    private Api getService()
    {
        return _service;
    }

}
