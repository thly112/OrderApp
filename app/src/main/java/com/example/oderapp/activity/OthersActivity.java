package com.example.oderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oderapp.R;
import com.example.oderapp.databinding.ActivityOthersBinding;
import com.example.oderapp.retrofit.ApiBanHang;
import com.example.oderapp.retrofit.RetrofitClient;
import com.example.oderapp.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OthersActivity extends AppCompatActivity {
    EditText edtUsername, edtPhone;
    TextView tvEmail, btnForgot;
    Button btnEdit;
    ApiBanHang apiBanHang;
    ImageView ivOut;
    BottomNavigationView navigationView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    int loai;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        initView();
        loai = getIntent().getIntExtra("loai", 1);
        getEventClick();
        initControl();
        loadUserInfo();
    }

    private void loadUserInfo() {
        if (Utils.user_current != null) {
            tvEmail.setText(Utils.user_current.getEmail());
            edtUsername.setText(Utils.user_current.getUsername());
            edtPhone.setText(Utils.user_current.getMobile());
        }
    }

    private void initControl() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPassActivity.class);
                startActivity(intent);
            }
        });

        ivOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đăng xuất
                if (Utils.mangGioHang != null) {
                    Utils.mangGioHang.clear();
                }
                Paper.book().delete("giohang");

                Paper.book().delete("user");
                Intent dangnhap = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(dangnhap);
                finish();

            }
        });

    }

    private void updateUserInfo() {
        String newUsername = edtUsername.getText().toString().trim();
        String newPhone = edtPhone.getText().toString().trim();

        if (newUsername.isEmpty()) {
            edtUsername.setError("Tên người dùng không được để trống");
            edtUsername.requestFocus();
            return;
        }

        if (newPhone.isEmpty()) {
            edtPhone.setError("Số điện thoại không được để trống");
            edtPhone.requestFocus();
            return;
        }

        compositeDisposable.add(apiBanHang.updateUser(Utils.user_current.getId(), newUsername, newPhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    if (userModel.isSuccess()) {
                        Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        // Cập nhật user hiện tại trong Utils
                        Utils.user_current.setUsername(newUsername);
                        Utils.user_current.setMobile(newPhone);
                    } else {
                        Toast.makeText(this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> Toast.makeText(this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show()));

    }

    private void initView() {
        // Ánh xạ view
        edtUsername = findViewById(R.id.edt_username);
        edtPhone = findViewById(R.id.edt_phone);
        tvEmail = findViewById(R.id.tv_email);
        btnForgot = findViewById(R.id.btnForgot);
        btnEdit = findViewById(R.id.btnedit);
        ivOut = findViewById(R.id.ivOut);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_other);

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
