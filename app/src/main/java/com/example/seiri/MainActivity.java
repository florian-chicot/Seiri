package com.example.seiri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.BD.FoodProductViewModel;
import com.example.seiri.Tools.FoodProductAdapter;
import com.example.seiri.Tools.InterfaceMyListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FoodProductViewModel foodProductViewModel;
    private List<FoodProduct> data;
    private LinearLayoutManager linearLayoutManager;
    private FoodProductAdapter foodProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodProductViewModel = new ViewModelProvider(this).get(FoodProductViewModel.class);
        foodProductViewModel.getNbFoodProduct().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                TextView textView = findViewById(R.id.tvNbFoodProduct);
                if (integer > 1) {
                    textView.setText(integer + " " + getResources().getString(R.string.foods_in_your_list));
                } else {
                    textView.setText(integer + " " + getResources().getString(R.string.food_in_your_list));
                }
            }
        });

        RecyclerView rvFoodProduct = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);

        foodProductViewModel.getAllFoodProduct().observe(this, new Observer<List<FoodProduct>>() {
            @Override
            public void onChanged(List<FoodProduct> foodProducts) {
                if (foodProductViewModel.getAllFoodProduct().getValue() != null) {
                    data = foodProductViewModel.getAllFoodProduct().getValue();

                    FoodProductAdapter adapter = new FoodProductAdapter(data);

                    rvFoodProduct.setAdapter(adapter);

                    rvFoodProduct.setLayoutManager(linearLayoutManager);
                }
            }
        });

        foodProductAdapter = new FoodProductAdapter(data);
        rvFoodProduct.setAdapter(foodProductAdapter);

        foodProductAdapter.setMyListener(new InterfaceMyListener() {
            @Override
            public void onItemClick(int position, View view) {
                FoodProduct foodProduct = data.get(position);
                Intent intent = new Intent(view.getContext(), UpdateFoodProduct.class);

                intent.putExtra("foodProduct", foodProduct);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View view) {
                FoodProduct foodProduct = data.get(position);
                foodProductViewModel.deleteFoodProduct(foodProduct);
                data.remove(position);
            }
        });
    }

    public void viewAddFoodProduct(View view) {
        Intent intent = new Intent(view.getContext(), AddFoodProduct.class);
        startActivity(intent);
    }

    public void deleteAllFoodProduct(View view) {
        foodProductViewModel.deleteAllFoodProduct();
    }

}