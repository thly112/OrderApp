package com.example.oderapp.retrofit;

import io.reactivex.rxjava3.annotations.NonNull;

import com.example.oderapp.model.Message;
import com.example.oderapp.model.MessageData;
import com.example.oderapp.model.NotiRespone;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNotification {
    @Headers(
            {
                    "Content-Type:application/json",
            }
    )
    @POST("projects/oderapp-f3e3f/messages:send")
    @NonNull
    Observable<NotiRespone> sendNotification(@Body MessageData data);
}
