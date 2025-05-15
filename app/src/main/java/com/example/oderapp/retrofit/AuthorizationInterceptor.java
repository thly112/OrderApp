package com.example.oderapp.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    private String authoToken;

    public AuthorizationInterceptor(String authoToken) {
        this.authoToken = authoToken;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer " + authoToken)
                .method(original.method(), original.body());
        Request request = builder.build();
        return chain.proceed(request);
    }
}
