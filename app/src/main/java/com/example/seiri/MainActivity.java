package com.example.seiri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.BD.FoodProductViewModel;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FoodProductViewModel foodProductViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodProductViewModel = new ViewModelProvider(this).get(FoodProductViewModel.class);
        foodProductViewModel.getNbFoodProduct().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                TextView textView = findViewById(R.id.tvNbFoodProduct);
                textView.setText(integer + " Aliments dans votre liste :");
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