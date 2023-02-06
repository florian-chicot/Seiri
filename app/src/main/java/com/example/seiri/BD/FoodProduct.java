package com.example.seiri.BD;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FoodProduct implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int foodProductId;
    private String name;
    private String expiryDate;
    private String quantity;

    public FoodProduct(String name, String expiryDate, String quantity) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    public int getFoodProductId() {
        return foodProductId;
    }

    public void setFoodProductId(int foodProductId) {
        this.foodProductId = foodProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
