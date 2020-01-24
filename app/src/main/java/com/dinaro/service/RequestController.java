package com.dinaro.service;

import android.text.TextUtils;
import android.util.Base64;

import com.dinaro.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Controller class for handling all network calls.
 */
public final class RequestController {

    private static String TAG = "RequestController";
    private static String HEADER_USER_ID = "loggedUserId";
    private static String HEADER_ACCESS_TOKEN = "accessToken";
    private static String HEADER_PERSONA_TYPE = "personaType";
    private static String HEADER_CONTENT_TYPE = "Content-Type";
    private static String HEADER_ACCEPT = "accept";
    private static String APPLICATION_JSON = "application/json";
    private static RequestController requestController;
    private static Retrofit retrofit;
    private static HttpLoggingInterceptor logger = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static Interceptor headerInterceptor;
    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(logger)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES);
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static RequestController getInstance() {
        if (requestController == null) {
            requestController = new RequestController();
        }
        return requestController;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, true);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass, final boolean isAuth) {

        if (headerInterceptor == null || !okHttpClient.interceptors().contains(headerInterceptor)) {

            if (isAuth) {
                addNetworkInterceptor();
            }

        }

        Retrofit retrofit = builder.client(okHttpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    private static void addNetworkInterceptor() {
        String credentials = "admin" + ":" + "admin";
        final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        //noinspection NullableProblems
        headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Response response = null;
                //String accessToken = PreferenceUtil.getKeyUserToken();
                String accessToken = "cf8218b6e13e6e96d5e0dd1b814b4fbc";
                if (!TextUtils.isEmpty(accessToken)) {
                    Request.Builder requestBuilder = original.newBuilder()
                            /*.header(HEADER_USER_ID, userId)*/
                            // .header(HEADER_ACCESS_TOKEN, accessToken)
                            .header(HEADER_CONTENT_TYPE, APPLICATION_JSON)
                            // .header(HEADER_ACCEPT, APPLICATION_JSON)
                            .header("Authorization", basic)
                            .method(original.method(), original.body());

                    response = chain.proceed(requestBuilder.build());
                } else {
                    response = chain.proceed(original);
                }

                return response;
            }
        };

        okHttpClient.addInterceptor(headerInterceptor);
    }

}
