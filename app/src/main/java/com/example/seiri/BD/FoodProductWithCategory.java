package com.example.seiri.BD;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class FoodProductWithCategory {
    @Embedded public FoodProduct foodProduct;
    @Relation(
            parentColumn = "foodProductId",
            entityColumn = "categoryId",
            associateBy = @Junction(FoodProductCategory.class)
    )
    public List<FoodProduct> foodProducts;
}
