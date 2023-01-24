package com.example.seiri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.BD.FoodProductViewModel;

import java.util.Calendar;
import java.util.Locale;

public class AddFoodProduct extends AppCompatActivity {
    private FoodProductViewModel foodProductViewModel;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_product);

        foodProductViewModel = new ViewModelProvider(this).get(FoodProductViewModel.class);
        initDatePicker();
        dateButton = findViewById(R.id.btnDateFoodProduct);
        dateButton.setText(getTodaysDate());
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String date = dateToString(day, month, year);
        if (Locale.getDefault().getLanguage().equals("fr")) {
            return date.substring(6,8) + "/" + date.substring(4,6) + "/" + date.substring(0,4);
        } else {
            return date.substring(6,8) + "." + date.substring(4,6) + "." + date.substring(0,4);
        }
    }

    public void addFoodProduct(View view) {

        EditText name = findViewById(R.id.edtNameFoodProduct);
        Button expiryDate = findViewById(R.id.btnDateFoodProduct);

        String nameFP = name.getText().toString();
        String expiryDateFP = expiryDate.getText().toString();

        FoodProduct foodProduct = new FoodProduct(nameFP, expiryDateFP);

        foodProductViewModel.insert(foodProduct);

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = dateToString(day, month, year);
                if (Locale.getDefault().getLanguage().equals("fr")) {
                    String formattedDate = date.substring(6,8) + "/" + date.substring(4,6) + "/" + date.substring(0,4);
                    dateButton.setText(formattedDate);
                } else {
                    String formattedDate = date.substring(6,8) + "." + date.substring(4,6) + "." + date.substring(0,4);
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
        if (i == 1) {
            return "01";
        }
        if (i == 2) {
            return "02";
        }
        if (i == 3) {
            return "03";
        }
        if (i == 4) {
            return "04";
        }
        if (i == 5) {
            return "05";
        }
        if (i == 6) {
            return "06";
        }
        if (i == 7) {
            return "07";
        }
        if (i == 8) {
            return "08";
        }
        if (i == 9) {
            return "09";
        }
        return Integer.toString(i);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}