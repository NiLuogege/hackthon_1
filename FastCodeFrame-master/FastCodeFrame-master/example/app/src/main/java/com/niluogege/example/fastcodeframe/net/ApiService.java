package com.niluogege.example.fastcodeframe.net;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("hackathon/count")
    Observable<Object> update(@Query("user_name") String user_name);


}