package com.example.oderapp.model;

public class SanPhamMoi {
    int id;
    String tensp;
    int hinhanh;
    String giasp;
    String mota;
    int loai;

    public SanPhamMoi(int id, String tensp, int hinhanh, String giasp, String mota, int loai) {
        this.id = id;
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.giasp = giasp;
        this.mota = mota;
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(int hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
}
