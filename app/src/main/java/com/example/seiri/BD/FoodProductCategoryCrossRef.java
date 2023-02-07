package com.example.seiri.BD;

import androidx.room.Entity;

@Entity(primaryKeys = {"foodProductId", "categoryId"})
public class FoodProductCategoryCrossRef {
    private int foodProductId;
    private int categoryId;

    public int getFoodProductId() {
        return foodProductId;
    }

    public void setFoodProductId(int foodProductId) {
        this.foodProductId = foodProductId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
