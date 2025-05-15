package com.example.oderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.oderapp.adapter.DonHangAdapter;
import com.example.oderapp.retrofit.ApiBanHang;
import com.example.oderapp.retrofit.RetrofitClient;
import com.example.oderapp.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LichSuActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView relichsu;
    BottomNavigationView navigationView;
    int loai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);
        initView();
        loai = getIntent().getIntExtra("loai", 1);
        getEventClick();
        getLichsu();

    }

    private void getLichsu() {
        compositeDisposable.add(apiBanHang.xemDonHang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            DonHangAdapter adapter = new DonHangAdapter(getApplicationContext(), donHangModel.getResult());
                            relichsu.setAdapter(adapter);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }



    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        relichsu = findViewById(R.id.recycleview_ls);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        relichsu.setLayoutManager(layoutManager);
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_order);
    }

    private void getEventClick() {
        navigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                if (!MainActivity.class.isAssignableFrom(getClass())) {
                    Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(trangchu);
                    finish();
                }
            } else if (id == R.id.nav_drink) {
                if (!(DoUongActivity.class.isAssignableFrom(getClass()) && loai == 1)) {
                    Intent douong = new Intent(getApplicationContext(), DoUongActivity.class);
                    douong.putExtra("loai", 1);
                    startActivity(douong);
                    finish();
                }
            } else if (id == R.id.nav_cake) {
                if (!(DoUongActivity.class.isAssignableFrom(getClass()) && loai == 2)) {
                    Intent banh = new Intent(getApplicationContext(), DoUongActivity.class);
                    banh.putExtra("loai", 2);
                    startActivity(banh);
                    finish();
                }
            } else if (id == R.id.nav_order) {
                Intent lichsu = new Intent(getApplicationContext(), LichSuActivity.class);
                startActivity(lichsu);
                finish();
            } else if (id == R.id.nav_other) {
                Intent khac = new Intent(getApplicationContext(), OthersActivity.class);
                startActivity(khac);
                finish();
            } else {
                return false;
            }

            return true;
        });

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}