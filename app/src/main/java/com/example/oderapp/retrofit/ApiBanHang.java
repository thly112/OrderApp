package com.example.oderapp.retrofit;

import io.reactivex.rxjava3.core.Observable;
import com.example.oderapp.model.LoaiSPModel;
import com.example.oderapp.model.SanPhamMoi;
import com.example.oderapp.model.SanPhamMoiModel;
import com.example.oderapp.model.User;
import com.example.oderapp.model.UserModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSPModel> getLoaiSP();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username

    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass
    );
}
