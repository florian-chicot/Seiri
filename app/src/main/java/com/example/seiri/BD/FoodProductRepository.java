package com.example.seiri.BD;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodProductRepository {
    private FoodProductDAO foodProductDAO;
    private LiveData<Integer> nbFoodProducts;
    private LiveData<List<FoodProduct>> allFoodProduct;

    public FoodProductRepository(Application application) {
        FoodProductRoomDataBase db = FoodProductRoomDataBase.getDataBase(application);
        foodProductDAO = db.foodProductDAO();
        nbFoodProducts = foodProductDAO.nbFoodProducts();
        allFoodProduct = foodProductDAO.getAllFoodProduct();
    }

    public LiveData<List<FoodProduct>> getAllFoodProduct() {
        return allFoodProduct;
    }

    public LiveData<Integer> getNbFoodProducts() {
        return nbFoodProducts;
    }

    public void deleteAllFoodProduct() {
        new DeleteAsyncTask(foodProductDAO).execute();
    }

//    public void deleteFoodProduct(FoodProduct foodProduct) {
//        new DeleteAsyncTask(foodProductDAO).execute();
//    }

    private static class DeleteAsyncTask extends AsyncTask<FoodProduct, Void, Void> {
        private FoodProductDAO foodProductDAO;

        public DeleteAsyncTask(FoodProductDAO w) {
            foodProductDAO = w;
        }

        @Override
        protected Void doInBackground(final FoodProduct... params) {
            foodProductDAO.deleteAllFoodProduct();
//            foodProductDAO.deleteFoodProduct(params[0]);
            return null;
        }
    }

    public void insert(FoodProduct w) {
        new InsertThread(foodProductDAO).execute(w);
    }

//    public void updateFoodProduct(FoodProduct w) {
//        new InsertThread(foodProductDAO).execute(w);
//    }

    public static class InsertThread {
        private  FoodProductDAO foodProductDAO;
        ExecutorService executorService;
        Handler handler;

        public  InsertThread(FoodProductDAO w) {
            foodProductDAO = w;
            executorService = Executors.newSingleThreadExecutor();
            handler = new Handler(Looper.getMainLooper());
        }

        public void execute(FoodProduct foodProduct) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    foodProductDAO.insert(foodProduct);
                    // foodProductDAO.updateFoodProduct(foodProduct);
                }
            });
        }
    }
}
