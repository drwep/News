package com.github.news.net;


import com.github.news.model.News;
import com.github.news.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Api {

    //baseUrl
    String API_SERVER_URL = "http://10.0.2.2:8080/";

    @POST("user/signIn")
    Observable<Response<User>> signIn(@Query("username") String username, @Query("password") String password);

    @POST("user/signUp")
    Observable<Response<User>> signUp(@Query("username") String username, @Query("password") String password);

    @POST("user/updateUsername")
    Observable<Response<User>> updateUsername(@Query("id") int id, @Query("username") String username);

    @POST("user/updatePassword")
    Observable<Response<User>> updatePassword(@Query("id") int id, @Query("password") String password);

    @GET("news")
    Observable<Response<List<News>>> getNewsByType(@Query("type") String type);

}
