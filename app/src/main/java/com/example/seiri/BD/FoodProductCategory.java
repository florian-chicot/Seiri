package com.example.seiri.BD;

import androidx.room.Entity;

@Entity(primaryKeys = {"foodProductId", "categoryId"})
public class FoodProductCategory {
    public int foodProductId;
    public int categoryId;
}

