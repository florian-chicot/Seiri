package com.example.seiri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.BD.FoodProductViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class AddFoodProduct extends AppCompatActivity {
    private FoodProductViewModel foodProductViewModel;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    String baseUrlProduct = "https://world.openfoodfacts.org/api/v0/product/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_product);

//        Button buttonapi = findViewById(R.id.testapi);
//        buttonapi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                testapi(Strin);
//            }
//        });

        foodProductViewModel = new ViewModelProvider(this).get(FoodProductViewModel.class);
        initDatePicker();
        dateButton = findViewById(R.id.btnDateFoodProduct);
        // date format YYYYYMMDD
        String d = getTodaysDate();
        if (Locale.getDefault().getLanguage().equals("fr")) {
            // date format DD/MM/YYYYY
            String formattedDate = d.substring(6,8) + "/" + d.substring(4,6) + "/" + d.substring(0,4);
            dateButton.setText(formattedDate);
        } else {
            // date format DD.MM.YYYYY
            String formattedDate = d.substring(6,8) + "." + d.substring(4,6) + "." + d.substring(0,4);
            dateButton.setText(formattedDate);
        }

        final Switch SwBarcode = findViewById(R.id.SwBarcode);
        final LinearLayout llBarcodeFoodProduct = findViewById(R.id.llBarcodeFoodProduct);
        final LinearLayout llNameFoodProduct = findViewById(R.id.llNameFoodProduct);

        // default switch checked  = false
        llBarcodeFoodProduct.setVisibility(View.GONE);
        llNameFoodProduct.setVisibility(View.VISIBLE);
        SwBarcode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llBarcodeFoodProduct.setVisibility(View.VISIBLE);
                    llNameFoodProduct.setVisibility(View.GONE);
                } else {
                    llBarcodeFoodProduct.setVisibility(View.GONE);
                    llNameFoodProduct.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private JSONObject testapi(String barcodeFP) {
        EditText barcode = findViewById(R.id.edtBarcodeFoodProduct);
//        String barcodeFP = barcode.getText().toString();
//        String barcodeFP = "3256223014516"; // Sirop de citron
        URL urlFoodProduct = createUrl(barcodeFP);
        GetProductByBarcodeAsyncTask getProductByBarcodeAsyncTask = new GetProductByBarcodeAsyncTask(urlFoodProduct);
        try {
            return  getProductByBarcodeAsyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetProductByBarcodeAsyncTask extends AsyncTask<Void, Void, JSONObject> {

        URL url;
        public GetProductByBarcodeAsyncTask(URL url) {
            this.url = url;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) this.url.openConnection();
                Log.d("MyLogs", String.valueOf(this.url.openConnection()));

                connection.setConnectTimeout(5000);
                int response = connection.getResponseCode();
                if(response == HttpsURLConnection.HTTP_OK){
                    StringBuilder builder = new StringBuilder();
                    try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String line;
                        while((line = bufferedReader.readLine())!= null) {
                            builder.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new JSONObject(builder.toString());
                } else {
                    Log.d("MyLogs", "Response "+ String.valueOf(response));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                assert connection != null;
                connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    Log.d("MyLogs", "onPostExecute: " + jsonObject.toString(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private URL createUrl(String apiRelativeURL){
        String baseURL = this.baseUrlProduct;

        try {
            String urlString = baseURL + apiRelativeURL;
            return new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        // date format YYYYYMMDD
        return dateToString(day, month, year);
    }

    public void addFoodProduct(View view) {

        EditText name = findViewById(R.id.edtNameFoodProduct);
        EditText quantity = findViewById(R.id.edtQuantityFoodProduct);
        Button expiryDate = findViewById(R.id.btnDateFoodProduct);

        EditText barcode = findViewById(R.id.edtBarcodeFoodProduct);
        String nameFP = "Unknown";
        Switch swBarcode = findViewById(R.id.SwBarcode);
        if (swBarcode.isChecked()) {
            String barcodeFP = barcode.getText().toString();
            JSONObject jsonObject = testapi(barcodeFP);
            try {
                assert jsonObject != null;
                nameFP = jsonObject.getJSONObject("product").getString("product_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            nameFP = name.getText().toString();
        }

        // date format DD MM YYYYY
        String expiryDateFP = expiryDate.getText().toString();
        String quantityFP = quantity.getText().toString();
        // date format YYYYYMMDD
        String formattedExpiryDateFP = expiryDateFP.substring(6,10) + expiryDateFP.substring(3,5) + expiryDateFP.substring(0,2);

        FoodProduct foodProduct = new FoodProduct(nameFP, formattedExpiryDateFP, quantityFP);

        foodProductViewModel.insert(foodProduct);

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
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