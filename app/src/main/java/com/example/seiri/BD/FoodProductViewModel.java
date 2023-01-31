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
    private List<FoodProductWithCategory> foodProductWithCategories;
    private List<CategoryWithFoodProduct> categoryWithFoodProducts;

    public FoodProductViewModel(@NonNull Application application) {
        super(application);
        foodProductRepository = new FoodProductRepository(application);
        nbFoodProduct = foodProductRepository.getNbFoodProducts();
        allFoodProduct = foodProductRepository.getAllFoodProduct();
        foodProductWithCategories = foodProductRepository.getFoodProductWithCategories();
        categoryWithFoodProducts = foodProductRepository.getCategoryWithFoodProducts();
    }

    public LiveData<Integer> getNbFoodProduct() {
        return nbFoodProduct;
    }

    public LiveData<List<FoodProduct>> getAllFoodProduct() {
        return allFoodProduct;
    }

    public List<FoodProductWithCategory> getFoodProductWithCategories() {
        return foodProductWithCategories;
    }

    public List<CategoryWithFoodProduct> getCategoryWithFoodProducts() {
        return categoryWithFoodProducts;
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
