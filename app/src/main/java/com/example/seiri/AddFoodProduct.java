package com.example.seiri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    }

    public void addFoodProduct(View view) {
        EditText name = findViewById(R.id.edtNameFoodProduct);
        EditText expiryDate = findViewById(R.id.edtDateFoodProduct);

        String nameFP = name.getText().toString();
        String expiryDateFP = expiryDate.getText().toString();

        Log.d("MyLogs", "Variable de base ======> " + nameFP + " et " + expiryDateFP);

        FoodProduct foodProduct = new FoodProduct("nameFP", "02/04/2014");

        Log.d("MyLogs", "Objet créer ======> " + foodProduct.getName() + " et " + foodProduct.getExpiryDate());

        foodProductViewModel.insert(foodProduct);
    }
}