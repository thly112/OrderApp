package com.example.oderapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oderapp.databinding.ActivitySignupBinding;
import com.example.oderapp.databinding.PopularItemBinding;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder>{

        private List<String> items;
        private List<Integer> images;
        private List<String> prices;
        public PopularAdapter(List<String> items, List<Integer> images, List<String> prices) {
                this.items = items;
                this.images = images;
                this.prices = prices;
        }
        @NonNull
        @Override
        public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                PopularItemBinding binding = PopularItemBinding.inflate(inflater, parent, false);
                return new PopularViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
                String item = items.get(position);
                int imageResId = images.get(position);
                String price = prices.get(position);
                holder.bind(item,imageResId, price);
        }

        @Override
        public int getItemCount() {
                return items.size();
        }

        class PopularViewHolder extends RecyclerView.ViewHolder {
                private PopularItemBinding binding;

                public PopularViewHolder(PopularItemBinding binding) {
                        super(binding.getRoot());
                        this.binding = binding;
                }

                public void bind(String item, int imageResId, String price) {
                        binding.foodNamePopular.setText(item);
                        binding.pricePopular.setText(price);
                        binding.imageView4.setImageResource(imageResId);
                }
        }
}
