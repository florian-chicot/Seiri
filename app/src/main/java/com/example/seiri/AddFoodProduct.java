package com.example.seiri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.BD.FoodProductViewModel;

public class AddFoodProduct extends AppCompatActivity {
    private FoodProductViewModel foodProductViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_product);

        foodProductViewModel = new ViewModelProvider(this).get(FoodProductViewModel.class);
    }

    public void addFoodProduct(View view) {

        EditText name = findViewById(R.id.edtNameFoodProduct);
        EditText expiryDate = findViewById(R.id.edtDateFoodProduct);
        EditText quantity = findViewById(R.id.edtQuantityFoodProduct);

        String nameFP = name.getText().toString();
        String expiryDateFP = expiryDate.getText().toString();
        String quantityFP = quantity.getText().toString();

        FoodProduct foodProduct = new FoodProduct(nameFP, expiryDateFP, quantityFP);

        foodProductViewModel.insert(foodProduct);

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }
}