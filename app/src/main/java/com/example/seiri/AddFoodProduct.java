package com.example.seiri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.seiri.BD.FoodProduct;
import com.example.seiri.BD.FoodProductViewModel;
import com.example.seiri.Tools.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

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
    private ImageButton barcodeScanner;

    String baseUrlProduct = "https://world.openfoodfacts.org/api/v0/product/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_product);

        foodProductViewModel = new ViewModelProvider(this).get(FoodProductViewModel.class);
        initDatePicker();
        dateButton = findViewById(R.id.btnDateFoodProduct);
        barcodeScanner = findViewById(R.id.btnBarcodeScanner);
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

        barcodeScanner.setOnClickListener(v -> {
            scanCode();
        });
    }

    private JSONObject testapi(String barcodeFP) {
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

        String nameFP = "Unknown";
        nameFP = name.getText().toString();
        String quantityFP = quantity.getText().toString();
        if (quantityFP.matches("")) {
            quantityFP = "1";
        }
        // date format DD MM YYYYY
        String expiryDateFP = expiryDate.getText().toString();
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
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            EditText name = findViewById(R.id.edtNameFoodProduct);
            String nameFP = "Unknown";
            JSONObject jsonObject = testapi(result.getContents());
            try {
                assert jsonObject != null;
                nameFP = jsonObject.getJSONObject("product").getString("product_name");
                name.setText(nameFP);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    });
}