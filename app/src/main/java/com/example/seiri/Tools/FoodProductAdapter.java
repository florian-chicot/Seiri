package com.example.seiri.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.R;

import java.util.List;

public class FoodProductAdapter extends RecyclerView.Adapter<FoodProductAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvViewNameFoodProduct);
            date = (TextView) itemView.findViewById(R.id.tvViewDateFoodProduct);
        }
    }

    private List<FoodProduct> listFoodProduct;

    public FoodProductAdapter(List<FoodProduct> foodProducts) {
        listFoodProduct = foodProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View foodProductView = inflater.inflate(R.layout.food_froduct_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(foodProductView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodProduct foodProduct = listFoodProduct.get(position);

        TextView name = holder.name;
        name.setText(foodProduct.getName());

        TextView date = holder.date;
        date.setText(foodProduct.getExpiryDate());
    }

    @Override
    public int getItemCount() {
        return listFoodProduct.size();
    }
}
