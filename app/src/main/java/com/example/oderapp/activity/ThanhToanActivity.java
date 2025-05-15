package com.example.oderapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.oderapp.R;
import com.example.oderapp.model.CreateOrder;
import com.example.oderapp.model.Message;
import com.example.oderapp.model.MessageData;
import com.example.oderapp.model.Notification;
import com.example.oderapp.retrofit.ApiBanHang;
import com.example.oderapp.retrofit.ApiPushNotification;
import com.example.oderapp.retrofit.AuthorizationInterceptor;
import com.example.oderapp.retrofit.RetrofitClient;
import com.example.oderapp.retrofit.RetrofitClientNoti;
import com.example.oderapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {
    TextView txttongtien, txtemail;
    ImageView ivBack;
    EditText edtdiachi, edtsdt;
    AppCompatButton btndathang, btnzalopay;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;
    int iddonhang;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        //zalopay
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);
        initView();
        countItem();
        initControl();
    }

    private void countItem() {
        totalItem = 0;
        for (int i = 0; i<Utils.mangMuaHang.size(); i++)
        {
            totalItem = totalItem + Utils.mangMuaHang.get(i).getSoluong();
        }
    }

    private void initControl() {
        ivBack.setOnClickListener(v -> finish());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien));
        txtemail.setText(Utils.user_current.getEmail());
        edtsdt.setText(Utils.user_current.getMobile());
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else {
                    //post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangMuaHang));
                    compositeDisposable.add(apiBanHang.createOrder(str_email, str_sdt, String.valueOf(tongtien), id, str_diachi, totalItem, new Gson().toJson(Utils.mangMuaHang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        pushNotiToUser();
                                        Toast.makeText(getApplicationContext(), "Thanh cong", Toast.LENGTH_SHORT).show();
                                        Utils.mangMuaHang.clear();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
        btnzalopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }
                else {
                    //post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangMuaHang));
                    compositeDisposable.add(apiBanHang.createOrder(str_email,str_sdt, String.valueOf(tongtien), id, str_diachi, totalItem, new Gson().toJson(Utils.mangMuaHang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        iddonhang = Integer.parseInt(messageModel.getIddonhang());
                                        Log.d("iddonhang", String.valueOf(iddonhang));

                                        requestZaloPay();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void requestZaloPay() {
        CreateOrder orderApi = new CreateOrder();

        try {
            String amountRaw = txttongtien.getText().toString().trim();
            String amount = amountRaw.replace(".", "");  // Loại bỏ dấu chấm
            Log.d("Amount", amount);  // ví dụ: "20000"

            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("return_code");
            Log.d("test return code", code);

            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        Log.d("ZaloPay", "Payment succeeded with token: " + token);
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();

                        compositeDisposable.add(apiBanHang.updateZalo(iddonhang, token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        messageModel -> {
                                            Log.d("ZaloPay", "Update đơn hàng thành công: " + messageModel.isSuccess());
                                            if (messageModel.isSuccess()) {
                                                pushNotiToUser();
                                                Utils.mangMuaHang.clear(); // Xóa giỏ hàng
                                                Toast.makeText(getApplicationContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                intent.putExtra("payment_status", "success");
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish(); // Đóng Activity hiện tại
                                            } else {
                                                Log.d("ZaloPay", "Cập nhật đơn hàng thất bại!");
                                            }
                                        },
                                        throwable -> {
                                            Log.e("ZaloPay", "Lỗi khi cập nhật đơn hàng: " + throwable.getMessage());
                                        }
                                ));
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        // Gửi trạng thái thanh toán bị hủy về MainActivity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("payment_status", "cancel");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Đóng Activity hiện tại
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        Log.d("loggg", s + " " + s1 + "" + zaloPayError.toString());
                        // Gửi trạng thái lỗi thanh toán về MainActivity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("payment_status", "error");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Đóng Activity hiện tại

                    }
                });
//                lblZpTransToken.setText("zptranstoken");
//                txtToken.setText(data.getString("zp_trans_token"));
//                IsDone();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pushNotiToUser() {
        if(Utils.tokenSend != null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthorizationInterceptor(Utils.tokenSend))
                    .build();
        }

        //getTokken
        compositeDisposable.add(apiBanHang.gettoken(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                for (int i = 0; i < userModel.getResult().size(); i++) {
                                    Notification notification = new Notification("Thông báo!", "Bạn đã đặt hàng thành công :)");
                                    Message message = new Message(userModel.getResult().get(i).getToken(), notification);

                                    Log.d("FCM_TOKEN2", userModel.getResult().get(i).getToken());
                                    MessageData messageData = new MessageData(message);
                                    ApiPushNotification apiPushNotification = RetrofitClientNoti.getInstance(client).create(ApiPushNotification.class);
                                    compositeDisposable.add(apiPushNotification.sendNotification(messageData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    user -> {
                                                        Log.d("FCM_RESPONSE", new Gson().toJson(user));
                                                    },
                                                    throwable -> {
                                                        Log.e("FCM_ERROR", Log.getStackTraceString(throwable));

                                                    }
                                            ));
                                }
                            }
                        }
                ));
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        ivBack = findViewById(R.id.ivBack);
        txttongtien = findViewById(R.id.txttongtien);
        edtsdt = findViewById(R.id.edtsdt);
        txtemail = findViewById(R.id.txtemail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);
        btnzalopay = findViewById(R.id.btnzalopay);
    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}