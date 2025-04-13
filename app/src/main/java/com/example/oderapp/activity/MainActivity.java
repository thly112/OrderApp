package com.example.oderapp.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oderapp.R;
import com.example.oderapp.adapter.LoaiSPAdapter;
import com.example.oderapp.adapter.SanPhamMoiAdapter;
import com.example.oderapp.model.LoaiSP;
import com.example.oderapp.model.SanPhamMoi;
import com.example.oderapp.retrofit.ApiBanHang;
import com.example.oderapp.retrofit.RetrofitClient;
import com.example.oderapp.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSPAdapter loaiSPAdapter;
    List<LoaiSP> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Anhxa();
        ActionBar();
        ActionViewFlipper();
        if(isConnected(this)){
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
            ActionViewFlipper();
//            getLoaiSanPham();
//            getSpMoi();
            fakeLoaiSP();
            fakeSpMoi();


        }else{
            Toast.makeText(getApplicationContext(), "khong co internet", Toast.LENGTH_LONG).show();
        }
    }

    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    sanPhamMoiModel -> {
                        if(sanPhamMoiModel.isSuccess()){
                            mangSpMoi = sanPhamMoiModel.getResult();
                            spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpMoi);
                            recyclerViewManHinhChinh.setAdapter(spAdapter);
                        }
                    },
                    throwable -> {
                        Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
            ));
    }

    private void fakeSpMoi() {
        mangSpMoi.add(new SanPhamMoi(
                1,
                "Menu 1",
                R.drawable.menu1,
                "2300000",
                "",
                1 // id loại sản phẩm
        ));
        mangSpMoi.add(new SanPhamMoi(
                2,
                "Menu 2",
                R.drawable.menu2,
                "2300000",
                "", // giá truyền dưới dạng String
                2 // id loại sản phẩm
        ));

        spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpMoi);
        recyclerViewManHinhChinh.setAdapter(spAdapter);
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSP()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                loaiSPModel -> {
                    if (loaiSPModel.isSuccess()) {
                        mangloaisp = loaiSPModel.getResult(); // ✅ đúng kiểu List<LoaiSP>
                        loaiSPAdapter = new LoaiSPAdapter(getApplicationContext(), mangloaisp);
                        listViewManHinhChinh.setAdapter(loaiSPAdapter);
                        loaiSPAdapter.notifyDataSetChanged();
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
        ));

    }
    private void fakeLoaiSP() {
        mangloaisp.clear();
        mangloaisp.add(new LoaiSP(1, "Giày Thể Thao", "https://bit.ly/loaigiay1"));
        mangloaisp.add(new LoaiSP(2, "Giày Tây", "https://bit.ly/loaigiay2"));
        mangloaisp.add(new LoaiSP(3, "Giày Sandal", "https://bit.ly/loaigiay3"));
        mangloaisp.add(new LoaiSP(4, "Phụ Kiện", "https://bit.ly/phukien1"));
        mangloaisp.add(new LoaiSP(5, "Khuyến Mãi", "https://bit.ly/khuyenmai1"));

        loaiSPAdapter.notifyDataSetChanged(); // cập nhật lại ListView
    }


    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://res.cloudinary.com/ds1rgnuvr/image/upload/v1744212311/2d009ced-6f4b-4d2a-9d04-60751cd82e04.png");
        mangquangcao.add("https://res.cloudinary.com/ds1rgnuvr/image/upload/v1744212203/3c50b50f-a8a8-48a9-b9c0-9fc442b9d736.png");
        mangquangcao.add("https://res.cloudinary.com/ds1rgnuvr/image/upload/v1744212144/2c0fbf7d-7c9b-4f7f-906d-6ab63ddaed04.png");

        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        Animation slideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);

        viewFlipper.setInAnimation(slideIn);
        viewFlipper.setOutAnimation(slideOut);

        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setDrawingCacheEnabled(true);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        // khoi tao list
        mangloaisp = new ArrayList<>();
        // khoi tao adapter
        loaiSPAdapter = new LoaiSPAdapter(getApplicationContext(), mangloaisp);
        listViewManHinhChinh.setAdapter(loaiSPAdapter);
        // khoi tao mang san pham moi
        mangSpMoi = new ArrayList<>();
    }
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }

    }
}