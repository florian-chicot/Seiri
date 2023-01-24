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
        return dateToString(day, month, year);
    }

    public void addFoodProduct(View view) {

        EditText name = findViewById(R.id.edtNameFoodProduct);
        EditText expiryDate = findViewById(R.id.edtDateFoodProduct);

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
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style, dateSetListener, year, month, day);
    }

    private String dateToString(int day, int month, int year) {
        return day + "/" + getMonthFormat(month) + "/" + year;
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return getResources().getString(R.string.january_short);
            case 2:
                return getResources().getString(R.string.february_short);
            case 3:
                return getResources().getString(R.string.march_short);
            case 4:
                return getResources().getString(R.string.april_short);
            case 5:
                return getResources().getString(R.string.may_short);
            case 6:
                return getResources().getString(R.string.june_short);
            case 7:
                return getResources().getString(R.string.july_short);
            case 8:
                return getResources().getString(R.string.august_short);
            case 9:
                return getResources().getString(R.string.september_short);
            case 10:
                return getResources().getString(R.string.october_short);
            case 11:
                return getResources().getString(R.string.november_short);
            case 12:
                return getResources().getString(R.string.december_short);
            default:
                return getResources().getString(R.string.january_short);
        }
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}