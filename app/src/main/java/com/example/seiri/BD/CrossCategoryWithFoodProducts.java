package com.example.seiri.BD;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CrossCategoryWithFoodProducts {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "categoryId",
            entityColumn = "foodProductId",
            associateBy = @Junction(FoodProductCategoryCrossRef.class)
    )

    private List<FoodProduct> foodProducts;

    public List<FoodProduct> getFoodProducts() {
        return foodProducts;
    }

    public void setFoodProducts(List<FoodProduct> foodProducts) {
        this.foodProducts = foodProducts;
    }
}
