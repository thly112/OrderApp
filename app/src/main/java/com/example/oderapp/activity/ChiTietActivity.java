package com.example.oderapp.activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import com.bumptech.glide.Glide;
import com.example.oderapp.R;
import com.example.oderapp.model.GioHang;
import com.example.oderapp.model.SanPhamMoi;
import com.example.oderapp.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota, numberItemTxt, minusCart, plusCart;
    Button btnthem;
    ImageView imghinhanh;

    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;
    ImageView ivBack, ivCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet);
        initView();
//        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        ivBack.setOnClickListener(v -> finish());

        ivCart.setOnClickListener(v -> {
            Toast.makeText(this, "Mở giỏ hàng", Toast.LENGTH_SHORT).show();
            // Chuyển sang Activity giỏ hàng nếu có
             startActivity(new Intent(this, GioHangActivity.class));
        });


        minusCart.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(numberItemTxt.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity--;
                numberItemTxt.setText(String.valueOf(currentQuantity));
            }
        });

        plusCart.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(numberItemTxt.getText().toString());
            currentQuantity++;
            numberItemTxt.setText(String.valueOf(currentQuantity));
        });

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        //Guests
        if (Utils.user_current == null) {
            Intent intent = new Intent(ChiTietActivity.this, LoginActivity.class);
            Toast.makeText(this, "Vui lòng đăng nhập để thêm sản phẩm vào giỏ", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;
        }
        int soluong = Integer.parseInt(numberItemTxt.getText().toString());

        if (Utils.mangGioHang.size() > 0) {
            boolean flag = false;
            for (int i = 0; i < Utils.mangGioHang.size(); i++) {
                if (Utils.mangGioHang.get(i).getIdsp() == (sanPhamMoi.getId())) {
                    Utils.mangGioHang.get(i).setSoluong(soluong + Utils.mangGioHang.get(i).getSoluong());
                    long gia = Long.parseLong(sanPhamMoi.getGiasp()) * Utils.mangGioHang.get(i).getSoluong();
                    Utils.mangGioHang.get(i).setGiasp(gia);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                long gia = Long.parseLong(sanPhamMoi.getGiasp()) * soluong;
                GioHang gioHang = new GioHang();
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                Utils.mangGioHang.add(gioHang);
            }
        } else {
            long gia = Integer.parseInt(sanPhamMoi.getGiasp()) * soluong;
            GioHang gioHang = new GioHang();
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            Utils.mangGioHang.add(gioHang);
        }

        int totalItem = 0;
        for (int i = 0; i < Utils.mangGioHang.size(); i++) {
            totalItem += Utils.mangGioHang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }


    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"đ");

      

    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogio);

        imghinhanh = findViewById(R.id.imgchitiet);
//        toolbar = findViewById(R.id.toolbar);
        badge = findViewById(R.id.menu_sl);
        ivBack = findViewById(R.id.ivBack);
        ivCart = findViewById(R.id.ivCart);
        numberItemTxt = findViewById(R.id.numberItemTxt);
        minusCart = findViewById(R.id.minusCart);
        plusCart = findViewById(R.id.plusCart);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if (Utils.mangGioHang != null){
            int totalItem = 0;
            for (int i = 0; i<Utils.mangGioHang.size(); i++)
            {
                totalItem = totalItem + Utils.mangGioHang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.mangGioHang != null){
            int totalItem = 0;
            for (int i = 0; i<Utils.mangGioHang.size(); i++)
            {
                totalItem = totalItem + Utils.mangGioHang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
}