package com.example.seiri.BD;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CrossFoodProductWithCategories {
    @Embedded
    public FoodProduct foodProduct;
    @Relation(
            parentColumn = "foodProductId",
            entityColumn = "categoryId",
            associateBy = @Junction(FoodProductCategoryCrossRef.class)
    )

    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
