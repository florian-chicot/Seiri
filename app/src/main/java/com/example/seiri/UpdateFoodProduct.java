package com.example.seiri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.BD.FoodProductViewModel;

public class UpdateFoodProduct extends AppCompatActivity {
    EditText edtUpdateNameFoodProduct;
    EditText edtUpdateDateFoodProduct;

    EditText edtUpdateQuantityFoodProduct;
    FoodProductViewModel foodProductViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food_product);

        foodProductViewModel = new ViewModelProvider(this).get(FoodProductViewModel.class);

        edtUpdateNameFoodProduct = findViewById(R.id.edtUpdateNameFoodProduct);
        edtUpdateDateFoodProduct = findViewById(R.id.edtUpdateDateFoodProduct);
        edtUpdateQuantityFoodProduct = findViewById(R.id.edtUpdateQuantityFoodProduct);

        Intent intent = getIntent();

        FoodProduct foodProduct = (FoodProduct) intent.getSerializableExtra("foodProduct");

        edtUpdateNameFoodProduct.setText(foodProduct.getName());
        edtUpdateDateFoodProduct.setText(foodProduct.getExpiryDate());
        edtUpdateQuantityFoodProduct.setText(foodProduct.getQuantity());
    }

    public void updateFoodProduct(View view) {
        Intent intent = getIntent();

        FoodProduct foodProduct = (FoodProduct) intent.getSerializableExtra("foodProduct");

        edtUpdateNameFoodProduct = findViewById(R.id.edtUpdateNameFoodProduct);
        edtUpdateDateFoodProduct = findViewById(R.id.edtUpdateDateFoodProduct);
        edtUpdateQuantityFoodProduct = findViewById(R.id.edtUpdateQuantityFoodProduct);

        foodProduct.setName(edtUpdateNameFoodProduct.getText().toString());
        foodProduct.setExpiryDate(edtUpdateDateFoodProduct.getText().toString());
        foodProduct.setQuantity(edtUpdateQuantityFoodProduct.getText().toString());

        foodProductViewModel.updateFoodProduct(foodProduct);

        Intent intent1 = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent1);
    }
}