package com.example.oderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.oderapp.R;
import com.example.oderapp.model.LoaiSP;

import java.util.List;

public class LoaiSPAdapter extends BaseAdapter {
    List<LoaiSP> array;
    Context context;

    public LoaiSPAdapter(Context context, List<LoaiSP> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView txttensp;
        ImageView imghinhanh;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham, null);
            viewHolder.txttensp = view.findViewById(R.id.item_tensp);
            viewHolder.imghinhanh = view.findViewById(R.id.item_imagesp);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.txttensp.setText(array.get(i).getTensp());
            Glide.with(context).load(array.get(i).getHinhanh()).into(viewHolder.imghinhanh);
        }
        return view;


    }
}
