package com.example.oderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oderapp.R;
import com.example.oderapp.databinding.ActivityLoginBinding;
import com.example.oderapp.databinding.ActivitySignupBinding;
import com.example.oderapp.retrofit.ApiBanHang;
import com.example.oderapp.retrofit.RetrofitClient;
import com.example.oderapp.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    EditText email, pass;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initControl();
    }

    private void initControl() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stremail = email.getText().toString().trim();
                String strpass = pass.getText().toString().trim();
                if (stremail.isEmpty() || strpass.isEmpty()) {
                    Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    //Save
                    Paper.book().write("email", stremail);
                    Paper.book().write("pass", strpass);
                    compositeDisposable.add(apiBanHang.dangKi(stremail, strpass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if (userModel.isSuccess()) {
                                            Utils.user_current = userModel.getResult().get(0);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }

            }
        });

        binding.btnDontHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        binding.btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = findViewById(R.id.txtemail);
        pass = findViewById(R.id.txtpass);

        //read data
        if(Paper.book().read("email") != null && Paper.book().read("pass") != null){
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
        }
    }

    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null) {
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
        }
    }

    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
