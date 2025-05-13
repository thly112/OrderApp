package com.example.oderapp.utils;

import com.example.oderapp.model.GioHang;
import com.example.oderapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://192.168.1.23/banhang/";
    public static List<GioHang> mangGioHang = new ArrayList<>();
    public static List<GioHang> mangMuaHang = new ArrayList<>();

    // Đặt mặc định là null để dễ kiểm tra trạng thái đăng nhập
    public static User user_current = null;
}
