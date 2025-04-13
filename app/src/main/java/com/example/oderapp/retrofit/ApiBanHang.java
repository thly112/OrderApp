package com.example.oderapp.retrofit;

import io.reactivex.rxjava3.core.Observable;
import com.example.oderapp.model.LoaiSPModel;
import com.example.oderapp.model.SanPhamMoi;
import com.example.oderapp.model.SanPhamMoiModel;

import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSPModel> getLoaiSP();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();
}
