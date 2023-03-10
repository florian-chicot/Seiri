package com.example.seiri.BD;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodProductViewModel extends AndroidViewModel {
    private FoodProductRepository foodProductRepository;
    private LiveData<Integer> nbFoodProduct;
    private LiveData<List<FoodProduct>> allFoodProduct;

    public FoodProductViewModel(@NonNull Application application) {
        super(application);
        foodProductRepository = new FoodProductRepository(application);
        nbFoodProduct = foodProductRepository.getNbFoodProducts();
        allFoodProduct = foodProductRepository.getAllFoodProduct();
    }

    public LiveData<Integer> getNbFoodProduct() {
        return nbFoodProduct;
    }

    public LiveData<List<FoodProduct>> getAllFoodProduct() {
        return allFoodProduct;
    }

    public void insert(FoodProduct foodProduct) {
        foodProductRepository.insert(foodProduct);
    }

    public void updateFoodProduct(FoodProduct foodProduct) {
        foodProductRepository.updateFoodProduct(foodProduct);
    }

    public void deleteAllFoodProduct() {
        foodProductRepository.deleteAllFoodProduct();
    }

    public void deleteFoodProduct(FoodProduct foodProduct) {
        foodProductRepository.deleteFoodProduct(foodProduct);
    }
}
