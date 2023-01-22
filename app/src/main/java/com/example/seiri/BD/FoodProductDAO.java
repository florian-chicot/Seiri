package com.example.seiri.BD;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodProductDAO {
    @Insert
    void insert(FoodProduct foodProduct);

    @Update
    void updateFoodProduct(FoodProduct foodProduct);

    @Delete
    void deleteFoodProduct(FoodProduct foodProduct);

    @Query("DELETE from FoodProduct")
    void deleteAllFoodProduct();

    @Query("SELECT * from FoodProduct")
    LiveData<List<FoodProduct>> getAllFoodProduct();

    @Query("SELECT count(*) from FoodProduct")
    LiveData<Integer> nbFoodProducts();
}
