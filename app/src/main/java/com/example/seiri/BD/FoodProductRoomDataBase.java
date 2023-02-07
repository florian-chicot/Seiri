package com.example.seiri.BD;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FoodProduct.class, Category.class, FoodProductCategoryCrossRef.class}, version = 1)
public abstract class FoodProductRoomDataBase extends RoomDatabase {
    public abstract FoodProductDAO foodProductDAO();

    private static FoodProductRoomDataBase INSTANCE;

    static FoodProductRoomDataBase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FoodProductRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FoodProductRoomDataBase.class, "foodProduct_database").build();
                }
            }
        }

        return INSTANCE;
    }
}
