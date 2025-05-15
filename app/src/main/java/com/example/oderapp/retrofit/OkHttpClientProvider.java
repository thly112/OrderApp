package com.example.oderapp.retrofit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
public class OkHttpClientProvider {
    public static OkHttpClient getClient(String authoToken) {
        // Tạo Logging Interceptor để log các request và response
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Ghi lại tất cả request và response body

        // Tạo Authorization Interceptor để thêm Authorization header
        AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor(authoToken);

        // Tạo OkHttpClient với cả hai interceptor
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor) // Thêm logging
                .addInterceptor(authorizationInterceptor) // Thêm Authorization header
                .build();
    }
}
