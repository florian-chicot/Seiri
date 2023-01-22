package com.example.seiri.BD;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FoodProduct {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String expiryDate;

    public FoodProduct(String name, String expiryDate) {
        this.name = name;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
