package com.example.oderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.oderapp.R;
import com.example.oderapp.databinding.ActivityLoginBinding;
import com.example.oderapp.databinding.ActivitySignupBinding;
import com.example.oderapp.retrofit.ApiBanHang;
import com.example.oderapp.retrofit.RetrofitClient;
import com.example.oderapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    EditText email, pass, username;
    ApiBanHang apiBanHang;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initControl();


    }
    private void initControl() {
        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKi();
            }
        });
        binding.btnHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dangKi() {
        String stremail = this.email.getText().toString().trim();
        String strpass = this.pass.getText().toString().trim();
        String strusername = this.username.getText().toString().trim();
        if (stremail.isEmpty() || strpass.isEmpty() || strusername.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(stremail, strpass)
                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if(user != null){
                                    postData(stremail, strpass, strusername, user.getUid());
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

    }

    private void postData(String stremail, String strpass, String strusername, String uid) {
        compositeDisposable.add(apiBanHang.dangKi(stremail, strpass, strusername, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                    Toast.makeText(SignupActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();

                                    new android.os.Handler().postDelayed(() -> {
                                        Utils.user_current.setEmail(stremail);
                                        Utils.user_current.setPass(strpass);
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }, 1500);

                                } else {
                                    Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                ));
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        username = findViewById(R.id.username);
    }
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}