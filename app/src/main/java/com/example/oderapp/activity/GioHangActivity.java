package com.example.oderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.R;
import com.example.oderapp.adapter.GioHangAdapter;
import com.example.oderapp.model.EventBus.TinhTongEvent;
import com.example.oderapp.model.GioHang;
import com.example.oderapp.utils.Utils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    TextView giohangtrong, tongtien;
    //    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang;
    GioHangAdapter adapter;
    long tongtiensp;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        tinhTongTien();
        //fix

    }

    private void tinhTongTien() {
        tongtiensp = 0;
        for (int i = 0; i < Utils.mangMuaHang.size(); i++){
            tongtiensp = tongtiensp + (Utils.mangMuaHang.get(i).getGiasp() * Utils.mangMuaHang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp));
    }

    private void initControl() {
        ivBack.setOnClickListener(v -> finish());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GioHangAdapter(getApplicationContext(), Utils.mangGioHang);
        recyclerView.setAdapter(adapter);

        if (Utils.mangGioHang.size() == 0){
            Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
            giohangtrong.setVisibility(View.VISIBLE);
        } else {
            giohangtrong.setVisibility(View.GONE);
        }
        Log.d("GioHang", "So luong san pham: " + Utils.mangGioHang.size());

        btnmuahang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                intent.putExtra("tongtien", tongtiensp);
                Utils.mangGioHang.clear();
                startActivity(intent);
            }
        });
    }

    private void initView() {
        tongtien = findViewById(R.id.txttongtien);
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        btnmuahang = findViewById(R.id.btnmuahang);
        ivBack = findViewById(R.id.ivBack);
    }
    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop(){
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if (event != null){
            tinhTongTien();
        }
    }
}
//package com.example.oderapp.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.oderapp.R;
//import com.example.oderapp.adapter.GioHangAdapter;
//import com.example.oderapp.model.EventBus.TinhTongEvent;
//import com.example.oderapp.model.GioHang;
//import com.example.oderapp.utils.Utils;
//import com.google.gson.Gson;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.text.DecimalFormat;
//import java.util.List;
//
//public class GioHangActivity extends AppCompatActivity {
//    TextView giohangtrong, tongtien, tongthanhtoan;
////    Toolbar toolbar;
//    RecyclerView recyclerView;
//    Button btnmuahang;
//    GioHangAdapter adapter;
//    long tongtiensp, tongtienthanhtoan, tienvanchuyen = 15000;
//    ImageView ivBack;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gio_hang);
//        initView();
//        initControl();
//        tinhTongTien();
//    }
//
//    private void tinhTongTien() {
//        tongtiensp = 0;
//        for (int i = 0; i < Utils.mangMuaHang.size(); i++){
//            tongtiensp = tongtiensp + (Utils.mangMuaHang.get(i).getGiasp() * Utils.mangMuaHang.get(i).getSoluong());
//        }
//        tongtienthanhtoan = tongtiensp + tienvanchuyen;
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        tongtien.setText(decimalFormat.format(tongtiensp));
//        tongthanhtoan.setText(decimalFormat.format(tongtienthanhtoan));
//    }
//
//    private void initControl() {
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                finish();
////            }
////        });
//        ivBack.setOnClickListener(v -> finish());
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new GioHangAdapter(getApplicationContext(), Utils.mangGioHang);
//        recyclerView.setAdapter(adapter);
//
//        if (Utils.mangGioHang.size() == 0){
//            Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
//            giohangtrong.setVisibility(View.VISIBLE);
//        } else {
//            giohangtrong.setVisibility(View.GONE);
//        }
//        Log.d("GioHang", "So luong san pham: " + Utils.mangGioHang.size());
//
//        btnmuahang.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
////                intent.putExtra("tongtien", tongtiensp);
//                intent.putExtra("tongtien", tongtienthanhtoan);
//                Utils.mangGioHang.clear();
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void initView() {
//        tongtien = findViewById(R.id.txttongtien);
//        tongthanhtoan = findViewById(R.id.txttongtienthanhtoan);
//        giohangtrong = findViewById(R.id.txtgiohangtrong);
////        toolbar = findViewById(R.id.toolbar);
//        recyclerView = findViewById(R.id.recycleviewgiohang);
//        btnmuahang = findViewById(R.id.btnmuahang);
//        ivBack = findViewById(R.id.ivBack);
//    }
//    @Override
//    protected void onStart(){
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//    @Override
//    protected void onStop(){
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }
//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
//    public void eventTinhTien(TinhTongEvent event){
//        if (event != null){
//            tinhTongTien();
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        tongtiensp = 0;
//        tongtienthanhtoan = 0;
//
//        tongtien.setText("0");
//        tongthanhtoan.setText("0");
//    }
//
//}