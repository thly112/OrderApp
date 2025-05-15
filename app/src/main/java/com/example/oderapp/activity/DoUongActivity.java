package com.example.oderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.R;
import com.example.oderapp.adapter.DoUongAdapter;
import com.example.oderapp.model.SanPhamMoi;
import com.example.oderapp.retrofit.ApiBanHang;
import com.example.oderapp.retrofit.RetrofitClient;
import com.example.oderapp.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DoUongActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    BottomNavigationView navigationView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    DoUongAdapter adapterDu;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_uong);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loai = getIntent().getIntExtra("loai", 1);
        AnhXa();
//        ActionToolBar();
        getEventClick();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        isLoading = true;
                        loadMore();
                    }

                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // add null
                sanPhamMoiList.add(null);
                adapterDu.notifyItemInserted(sanPhamMoiList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // remover null
                sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                adapterDu.notifyItemRemoved(sanPhamMoiList.size());
                page = page + 1;
                getData(page);
                adapterDu.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);

    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getSanPham(page, loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                if (adapterDu == null){
                                    sanPhamMoiList = sanPhamMoiModel.getResult();
                                    adapterDu = new DoUongAdapter(getApplicationContext(), sanPhamMoiList);
                                    recyclerView.setAdapter(adapterDu);
                                }else {
                                    int vitri = sanPhamMoiList.size()-1;
                                    int soluongadd = sanPhamMoiModel.getResult().size();
                                    for (int i = 0; i<soluongadd; i++){
                                        sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                                    }
                                    adapterDu.notifyItemRangeInserted(vitri,soluongadd);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Đã hiển thị hết sản phẩm", Toast.LENGTH_LONG).show();
                                isLoading = true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Khong ket noi duoc server", Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

//    private void ActionToolBar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }

    private void AnhXa() {
//        toolbar = findViewById(R.id.toolbardu);
        recyclerView = findViewById(R.id.recycleview_du);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
        navigationView = findViewById(R.id.bottom_navigation);
        if (loai == 1) {
            navigationView.setSelectedItemId(R.id.nav_drink);
        } else if (loai == 2) {
            navigationView.setSelectedItemId(R.id.nav_cake);
        }

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
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}