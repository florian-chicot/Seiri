package com.example.seiri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FoodProductViewModel foodProductViewModel;

    private List<FoodProduct> data;

    private LinearLayoutManager linearLayoutManager;

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
    }

    public void viewAddFoodProduct(View view) {
        Intent intent = new Intent(view.getContext(), AddFoodProduct.class);
        startActivity(intent);
    }

    public void viewUpdateFoodProduct(View view) {
        Intent intent = new Intent(view.getContext(), AddFoodProduct.class);
        startActivity(intent);
    }

    public void deleteAllFoodProduct(View view) {
        foodProductViewModel.deleteAllFoodProduct();
    }

    // à compléter
//    public void deleteFoodProduct(View view) {
//        foodProductViewModel.deleteFoodProduct(foodProduct);
//    }
}