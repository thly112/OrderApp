package com.example.oderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oderapp.R;
import com.example.oderapp.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class DoUongAdapter extends RecyclerView.Adapter<DoUongAdapter.MyViewHolder> {
    Context context;

    public DoUongAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    List<SanPhamMoi> array;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_douong, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPham = array.get(position);
        holder.tensp.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPham.getGiasp()))+"đ");
        holder.mota.setText(sanPham.getMota());
        Glide.with(context).load(sanPham.getHinhanh()).into(holder.hinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tensp, giasp, mota;
        ImageView hinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdu_ten);
            giasp = itemView.findViewById(R.id.itemdu_gia);
            mota = itemView.findViewById(R.id.itemdu_mota);
            hinhanh = itemView.findViewById(R.id.itemdu_image);
        }
    }

}
