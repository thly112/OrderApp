//package com.example.oderapp.fragment;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.denzcoskun.imageslider.ImageSlider;
//import com.denzcoskun.imageslider.constants.ScaleTypes;
//import com.denzcoskun.imageslider.interfaces.ItemClickListener;
//import com.denzcoskun.imageslider.models.SlideModel;
//import com.example.oderapp.R;
//import com.example.oderapp.adapter.SanPhamMoiAdapter;
//import com.example.oderapp.databinding.FragmentHomeBinding;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class HomeFragment extends Fragment {
//
//    private FragmentHomeBinding binding;
//
//    public HomeFragment() {
//        // Required empty public constructor
//    }
//
//
//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        ArrayList<SlideModel> imageList = new ArrayList<>();
//        imageList.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
//        imageList.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
//        imageList.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));
//
//        ImageSlider imageSlider = binding.imageSlider;
//        imageSlider.setImageList(imageList);
//        imageSlider.setImageList(imageList, ScaleTypes.FIT);
//        imageSlider.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void doubleClick(int position) {
//            }
//
//            @Override
//            public void onItemSelected(int position) {
//                SlideModel itemPosition = imageList.get(position);
//                String itemMessage = "Selected Image " + position;
//                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show();
//            }
//        });
//        List<String> foodName = Arrays.asList("Burger", "sandwich", "momo", "item");
//        List<String> price = Arrays.asList("$5", "$7", "$8", "$10");
//        List<Integer> popularFoodImages = Arrays.asList(
//                R.drawable.menu1,
//                R.drawable.menu2,
//                R.drawable.menu3,
//                R.drawable.menu4
//        );
//
//        SanPhamMoiAdapter adapter = new SanPhamMoiAdapter(foodName, popularFoodImages, price); // Lưu ý thứ tự nếu constructor của bạn là (food, image, price)
//
//        binding.PopularRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.PopularRecyclerView.setAdapter(adapter);
//        Log.d("AdapterDebug", "Item count: " + adapter.getItemCount());
//        Log.d("AdapterDebug", "Items: " + foodName.toString());
//        Log.d("AdapterDebug", "Prices: " + price.toString());
//        Log.d("AdapterDebug", "Images: " + popularFoodImages.toString());
//
//
//    }
//}