package com.example.seiri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.BD.FoodProductViewModel;

import java.util.Calendar;
import java.util.Locale;

public class UpdateFoodProduct extends AppCompatActivity {
    EditText edtUpdateNameFoodProduct;
    EditText edtUpdateQuantityFoodProduct;

    FoodProductViewModel foodProductViewModel;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food_product);

        foodProductViewModel = new ViewModelProvider(this).get(FoodProductViewModel.class);

        edtUpdateNameFoodProduct = findViewById(R.id.edtUpdateNameFoodProduct);
        dateButton = findViewById(R.id.btnUpdateDateFoodProduct);
        edtUpdateQuantityFoodProduct = findViewById(R.id.edtUpdateQuantityFoodProduct);

        initDatePicker();

        Intent intent = getIntent();

        FoodProduct foodProduct = (FoodProduct) intent.getSerializableExtra("foodProduct");

        edtUpdateNameFoodProduct.setText(foodProduct.getName());
        // date format YYYYYMMDD
        String d = foodProduct.getExpiryDate();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            // date format DD/MM/YYYYY
            String formattedDate = d.substring(6,8) + "/" + d.substring(4,6) + "/" + d.substring(0,4);
            dateButton.setText(formattedDate);
        } else {
            // date format DD.MM.YYYYY
            String formattedDate = d.substring(6,8) + "." + d.substring(4,6) + "." + d.substring(0,4);
            dateButton.setText(formattedDate);
        }
        edtUpdateQuantityFoodProduct.setText(foodProduct.getQuantity());
    }

    public void updateFoodProduct(View view) {
        Intent intent = getIntent();

        FoodProduct foodProduct = (FoodProduct) intent.getSerializableExtra("foodProduct");

        EditText updatedName = findViewById(R.id.edtUpdateNameFoodProduct);
        Button updatedExpiryDate = findViewById(R.id.btnUpdateDateFoodProduct);
        edtUpdateNameFoodProduct = findViewById(R.id.edtUpdateNameFoodProduct);
        edtUpdateQuantityFoodProduct = findViewById(R.id.edtUpdateQuantityFoodProduct);

        String updatedNameFP = updatedName.getText().toString();
        // date format DD MM YYYYY
        String updatedExpiryDateFP = updatedExpiryDate.getText().toString();
        // date format YYYYYMMDD
        String formattedUpdatedExpiryDateFP = updatedExpiryDateFP.substring(6,10) + updatedExpiryDateFP.substring(3,5) + updatedExpiryDateFP.substring(0,2);
        foodProduct.setName(edtUpdateNameFoodProduct.getText().toString());
        foodProduct.setQuantity(edtUpdateQuantityFoodProduct.getText().toString());

        foodProduct.setName(updatedNameFP);
        foodProduct.setExpiryDate(formattedUpdatedExpiryDateFP);
        foodProductViewModel.updateFoodProduct(foodProduct);

        Intent intent1 = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent1);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                // date format YYYYYMMDD
                String d = dateToString(day, month, year);
                if (Locale.getDefault().getLanguage().equals("fr")) {
                    // date format DD/MM/YYYYY
                    String formattedDate = d.substring(6,8) + "/" + d.substring(4,6) + "/" + d.substring(0,4);
                    dateButton.setText(formattedDate);
                } else {
                    // date format DD.MM.YYYYY
                    String formattedDate = d.substring(6,8) + "." + d.substring(4,6) + "." + d.substring(0,4);
                    dateButton.setText(formattedDate);
                }
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private String dateToString(int day, int month, int year) {
        return year + getDateFormat(month) + getDateFormat(day);
    }

    private String getDateFormat(int i) {
        switch (i) {
            case 1:
                return  "01";
            case 2:
                return "02";
            case 3:
                return "03";
            case 4:
                return "04";
            case 5:
                return "05";
            case 6:
                return "06";
            case 7:
                return "07";
            case 8:
                return "08";
            case 9:
                return "09";
            default:
                return Integer.toString(i);
        }
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}