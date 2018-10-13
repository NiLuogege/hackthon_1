package com.niluogege.example.fastcodeframe.net;

import com.niluogege.example.fastcodeframe.bean.ResultInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("hackathon/count")
    Observable<Object> update(@Query("user_name") String user_name, @Query("room_id") int room_id, @Query("pot_id") int pot_id);

    /**
     * 游戏结果
     *
     * @param user_name
     * @return
     */
    @GET("hackathon/result")
    Observable<ResultInfo> result(@Query("user_name") String user_name, @Query("room_id") int room_id);

    /**
     * 开始结束
     *
     * @return
     */
    @GET("hackathon/room")
    Observable<Integer> room(@Query("is_start") int is_start, @Query("is_end") int is_end);


}