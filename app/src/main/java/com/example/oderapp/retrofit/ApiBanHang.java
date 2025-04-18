package com.example.oderapp.retrofit;

import io.reactivex.rxjava3.core.Observable;
import com.example.oderapp.model.LoaiSPModel;
import com.example.oderapp.model.SanPhamMoi;
import com.example.oderapp.model.SanPhamMoiModel;

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
}
