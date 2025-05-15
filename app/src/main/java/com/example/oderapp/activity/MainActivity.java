package com.example.oderapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import com.example.oderapp.model.User;
import com.example.oderapp.retrofit.ApiBanHang;
import com.example.oderapp.retrofit.RetrofitClient;
import com.example.oderapp.utils.AccessToken;
import com.example.oderapp.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.internal.Util;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class MainActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    BottomNavigationView navigationView;
//    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSPAdapter loaiSPAdapter;
    List<LoaiSP> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
//    ImageView imgsearch;
    EditText edtSearch;
    int loai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        ((android.view.Window) window).setStatusBarColor(ContextCompat.getColor(this, R.color.beige));
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init(this);
        initAccessToken();
        if (Paper.book().read("user") != null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
        getToken();
        Anhxa();
        ActionViewFlipper();
        if(isConnected(this)){
            getLoaiSanPham();
            getSpMoi();
            loai = getIntent().getIntExtra("loai", 1);
            getEventClick();
        }else{
            Toast.makeText(getApplicationContext(), "khong co internet", Toast.LENGTH_LONG).show();
        }

        // Kiểm tra nếu có dữ liệu truyền vào từ ThanhToanActivity
        String paymentStatus = getIntent().getStringExtra("payment_status");
        if (paymentStatus != null) {
            switch (paymentStatus) {
                case "success":
                    Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    break;
                case "cancel":
                    Toast.makeText(this, "Đã hủy thanh toán", Toast.LENGTH_SHORT).show();
                    break;
                case "error":
                    Toast.makeText(this, "Lỗi thanh toán", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void initAccessToken() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                AccessToken accessToken = new AccessToken();
                String token = accessToken.getAccessToken();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("token ", token);
                        Utils.tokenSend = token;
                    }
                });
            }
        });


    }

    private void getToken() {
        if (Utils.user_current == null ) {
            return; // Bỏ qua nếu là khách
        }
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)){
                            compositeDisposable.add(apiBanHang.updateToken(Utils.user_current.getId(), s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messageModel -> {

                                            }, throwable -> {
                                                Log.d("log", throwable.getMessage());
                                            }
                                    ));
                        }
                    }
                });
    }


    private void getEventClick() {
        navigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Nếu chưa ở MainActivity thì mới chuyển
                if (!getClass().getSimpleName().equals("MainActivity")) {
                    Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(trangchu);
                    finish();
                }
            } else if (id == R.id.nav_drink) {
                // Nếu chưa ở DoUongActivity với loai=1 thì chuyển
                if (!(getClass().getSimpleName().equals("DoUongActivity") && loai == 1)) {
                    Intent douong = new Intent(getApplicationContext(), DoUongActivity.class);
                    douong.putExtra("loai", 1);
                    startActivity(douong);
                    finish();
                }
            } else if (id == R.id.nav_cake) {
                // Nếu chưa ở DoUongActivity với loai=2 thì chuyển
                if (!(getClass().getSimpleName().equals("DoUongActivity") && loai == 2)) {
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
            }

            drawerLayout.closeDrawers(); // Đóng drawer sau khi chọn
            return true;
        });
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
                            Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server spm" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("ERROR_SPM", throwable.getMessage());
                        }
                ));
    }

    private void getLoaiSanPham() {
//        compositeDisposable.add(apiBanHang.getLoaiSP()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        loaiSPModel -> {
//                            if (loaiSPModel.isSuccess()) {
//                                mangloaisp = loaiSPModel.getResult();
//                                loaiSPAdapter = new LoaiSPAdapter(getApplicationContext(), mangloaisp);
//                                listViewManHinhChinh.setAdapter(loaiSPAdapter);
//                            }
//                        },
//                        throwable -> {
//                            Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server lsp" + throwable.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                ));

    }
    private void ActionViewFlipper() {
        // Danh sách ảnh từ drawable
        int[] mangquangcao = {
                R.drawable.banner,
                R.drawable.banner2,
                R.drawable.banner3
        };

        // Thêm ảnh vào ViewFlipper
        for (int i = 0; i < mangquangcao.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(mangquangcao[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        // Tạo animation trượt trái/phải
        Animation slideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);

        viewFlipper.setInAnimation(slideIn);
        viewFlipper.setOutAnimation(slideOut);

        // Tự động lật ảnh
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
    }




    private void Anhxa() {
        edtSearch = findViewById(R.id.edtsearch);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_home);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        // khoi tao list
        mangloaisp = new ArrayList<>();

        mangSpMoi = new ArrayList<>();
        if (Utils.mangGioHang == null) {
            Utils.mangGioHang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for (int i = 0; i<Utils.mangGioHang.size(); i++) {
                totalItem = totalItem + Utils.mangGioHang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        edtSearch.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    v.performClick(); // Gọi lại performClick để xử lý accessibility

                    String keyword = edtSearch.getText().toString().trim();
                    if (!keyword.isEmpty()) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("keyword", keyword);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Vui lòng nhập từ khóa!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i<Utils.mangGioHang.size(); i++) {
            totalItem = totalItem + Utils.mangGioHang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
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

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}