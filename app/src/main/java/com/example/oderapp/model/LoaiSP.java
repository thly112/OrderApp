package com.example.oderapp.model;

public class LoaiSP {
    int id;
    String tensp;
    String hinhanh;

    public LoaiSP(String tensp, String hinhanh) {
        this.tensp = tensp;
        this.hinhanh = hinhanh;
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

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
