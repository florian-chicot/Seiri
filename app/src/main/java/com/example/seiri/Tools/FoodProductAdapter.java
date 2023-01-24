package com.example.seiri.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.R;

import java.util.List;
import java.util.Locale;

public class FoodProductAdapter extends RecyclerView.Adapter<FoodProductAdapter.ViewHolder> {

    private static InterfaceMyListener myListener;

    public static void setMyListener(InterfaceMyListener myListener) {
        FoodProductAdapter.myListener = myListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView name;
        public TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvViewNameFoodProduct);
            date = (TextView) itemView.findViewById(R.id.tvViewDateFoodProduct);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            myListener.onItemLongClick(getAdapterPosition(), view);
            return false;
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
        View foodProductView = inflater.inflate(R.layout.food_product_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(foodProductView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodProduct foodProduct = listFoodProduct.get(position);

        TextView name = holder.name;
        name.setText(foodProduct.getQuantity() + " " + foodProduct.getName());

        TextView date = holder.date;
        // date format YYYYYMMDD
        String d = foodProduct.getExpiryDate();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            // date format DD/MM/YYYYY
            String formattedDate = d.substring(6,8) + "/" + d.substring(4,6) + "/" + d.substring(0,4);
            date.setText(formattedDate);
        } else {
            // date format DD.MM.YYYYY
            String formattedDate = d.substring(6,8) + "." + d.substring(4,6) + "." + d.substring(0,4);
            date.setText(formattedDate);
        }
    }

    @Override
    public int getItemCount() {
        return listFoodProduct.size();
    }
}
