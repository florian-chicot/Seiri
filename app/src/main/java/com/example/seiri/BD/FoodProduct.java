package com.example.seiri.BD;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FoodProduct implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String expiryDate;
    private String quantity;
//    private String imageURL;

    public FoodProduct(String name, String expiryDate, String quantity/*, String imageURL*/) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
//        this.imageURL = imageURL;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

//    public String getImageURL() {
//        return imageURL;
//    }

//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }
}
