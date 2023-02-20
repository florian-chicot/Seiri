package com.example.seiri.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
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
        public ImageView pictureFP;
        public ImageView circle_dark_green;
        public ImageView circle_light_green;
        public ImageView circle_orange;
        public ImageView circle_red;
        public TextView expiry;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvViewNameFoodProduct);
            date = (TextView) itemView.findViewById(R.id.tvViewDateFoodProduct);
            pictureFP = (ImageView) itemView.findViewById(R.id.pictureFP);

            circle_dark_green = (ImageView) itemView.findViewById(R.id.circle_dark_green);
            circle_light_green = (ImageView) itemView.findViewById(R.id.circle_light_green);
            circle_orange = (ImageView) itemView.findViewById(R.id.circle_orange);
            circle_red = (ImageView) itemView.findViewById(R.id.circle_red);
            expiry = (TextView) itemView.findViewById(R.id.tvViewExpiryFoodProduct);

            circle_dark_green.setVisibility(View.GONE);
            circle_light_green.setVisibility(View.GONE);
            circle_orange.setVisibility(View.GONE);
            circle_red.setVisibility(View.GONE);

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

        ImageView pictureFP = holder.pictureFP;
        Picasso.get().load(foodProduct.getImageURL()).into(pictureFP);

        TextView expiry = holder.expiry;

        Calendar today = Calendar.getInstance();
        Calendar expiryDate = Calendar.getInstance();
        expiryDate.set(Integer.parseInt(d.substring(0, 4)), Integer.parseInt(d.substring(4, 6)) - 1, Integer.parseInt(d.substring(6, 8)));
        long diffInMillis = expiryDate.getTimeInMillis() - today.getTimeInMillis();
        int diffInDays = (int) (diffInMillis / (24 * 60 * 60 * 1000));

        if (Locale.getDefault().getLanguage().equals("fr")) {
            if (diffInDays < 0) {
                holder.circle_red.setVisibility(View.VISIBLE);
                expiry.setText("Périmé" + " " + "depuis" + " " + -diffInDays  + " " + "jours");
            } else if (diffInDays == 0) {
                holder.circle_red.setVisibility(View.VISIBLE);
                expiry.setText("Périmé" + " " + "depuis" + " " + "aujourd'hui");
            } else if (diffInDays == 1) {
                holder.circle_orange.setVisibility(View.VISIBLE);
                expiry.setText("Périmé" + " " + "depuis" + " " + "hier");
            } else if (diffInDays <= 3) {
                holder.circle_orange.setVisibility(View.VISIBLE);
                expiry.setText("Périme" + " " + "dans" + " " + diffInDays + " " + "jours");
            } else if (diffInDays <= 7) {
                holder.circle_light_green.setVisibility(View.VISIBLE);
                expiry.setText("Périme" + " " + "dans" + " " + diffInDays + " " + "jours");
            } else {
                holder.circle_dark_green.setVisibility(View.VISIBLE);
                expiry.setText("Périme" + " " + "dans" + " " + diffInDays + " " + "jours");
            }
        } else {
            if (diffInDays < 0) {
                holder.circle_red.setVisibility(View.VISIBLE);
                expiry.setText("Expired" + " " + "since" + " " + -diffInDays  + " " + "days");
            } else if (diffInDays == 0) {
                holder.circle_red.setVisibility(View.VISIBLE);
                expiry.setText("Expired" + " " + "since" + " " + "today");
            } else if (diffInDays == 1) {
                holder.circle_orange.setVisibility(View.VISIBLE);
                expiry.setText("Expired" + " " + "since" + " " + "yesterday");
            } else if (diffInDays <= 3) {
                holder.circle_orange.setVisibility(View.VISIBLE);
                expiry.setText("Expire" + " " + "in" + " " + diffInDays + " " + "days");
            } else if (diffInDays <= 7) {
                holder.circle_light_green.setVisibility(View.VISIBLE);
                expiry.setText("Expire" + " " + "in" + " " + diffInDays + " " + "days");
            } else {
                holder.circle_dark_green.setVisibility(View.VISIBLE);
                expiry.setText("Expire" + " " + "in" + " " + diffInDays + " " + "days");
            }
        }
        /* TODO with resources in strings.xml */
        /*
        if (diffInDays < 0) {
            holder.circle_red.setVisibility(View.VISIBLE);
            expiry.setText(getResources().getString(R.string.Expired) + " " + getResources().getString(R.string.since) + " " + -diffInDays  + " " + getResources().getString(R.string.days));
        } else if (diffInDays == 0) {
            holder.circle_red.setVisibility(View.VISIBLE);
            expiry.setText(getResources().getString(R.string.Expired) + " " + getResources().getString(R.string.since) + " " + getResources().getString(R.string.today));
        } else if (diffInDays == 1) {
            holder.circle_orange.setVisibility(View.VISIBLE);
            expiry.setText(getResources().getString(R.string.Expired) + " " + getResources().getString(R.string.since) + " " + getResources().getString(R.string.yesterday));
        } else if (diffInDays <= 3) {
            holder.circle_orange.setVisibility(View.VISIBLE);
            expiry.setText(getResources().getString(R.string.Expire) + " " + getResources().getString(R.string.in) + " " + diffInDays + " " + getResources().getString(R.string.days));
        } else if (diffInDays <= 7) {
            holder.circle_light_green.setVisibility(View.VISIBLE);
            expiry.setText(getResources().getString(R.string.Expire) + " " + getResources().getString(R.string.in) + " " + diffInDays + " " + getResources().getString(R.string.days));
        } else {
            holder.circle_dark_green.setVisibility(View.VISIBLE);
            expiry.setText(getResources().getString(R.string.Expire) + " " + getResources().getString(R.string.in) + " " + diffInDays + " " + getResources().getString(R.string.days));
        }
        */
    }

    @Override
    public int getItemCount() {
        return listFoodProduct.size();
    }
}
