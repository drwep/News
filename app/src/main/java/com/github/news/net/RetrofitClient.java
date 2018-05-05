package com.github.news.net;


import com.github.news.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient {


    public static Api getApi() {
        return ApiServiceHolder.API_SERVICE;
    }


    private static final class ApiServiceHolder {
        private static final Api API_SERVICE =
                RetrofitHolder.RETROFIT.create(Api.class);

    }

    private static class RetrofitHolder {
        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(Api.API_SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .build();

    }

    private static final class OKHttpHolder {
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        private static OkHttpClient.Builder addInterceptor() {
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                BUILDER.addInterceptor(loggingInterceptor);
            }
            return BUILDER;
        }
    }
}