package com.example.seiri.BD;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithFoodProduct {
    @Embedded public Category category;
    @Relation(
            parentColumn = "categoryId",
            entityColumn = "foodProductId",
            associateBy = @Junction(FoodProductCategory.class)
    )
    public List<Category> categories;
}
