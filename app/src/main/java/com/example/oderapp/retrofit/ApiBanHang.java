package com.example.oderapp.retrofit;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

import com.example.oderapp.model.DonHangModel;
import com.example.oderapp.model.LoaiSPModel;
import com.example.oderapp.model.MessageModel;
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
            @Field("username") String username,
            @Field("mobile") String sdt
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<MessageModel> createOrder(
            @Field("email") String email,
            @Field("sodienthoai") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int id,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet

    );

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );

    @POST("updatezalopay.php")
    @FormUrlEncoded
    Observable<MessageModel> updateZalo(
            @Field("id") int id,
            @Field("token") String token
    );


    @POST("updateuser.php")
    @FormUrlEncoded
    Observable<UserModel> updateUser(
            @Field("id") int id,
            @Field("username") String username,
            @Field("mobile") String mobile
    );
}
