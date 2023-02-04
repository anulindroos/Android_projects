package com.example.bmiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {
    private String mUrl = "url here";
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mQueue = Volley.newRequestQueue(this);
    }

    public void fetchWeatherData(View view) {
        // Haetaan säätiedot URLsta
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, mUrl, null, response -> {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    parseJsonAndUpdateUi(response);
                }, error -> {
                    // TODO: Handle error

                });

        // Lisätään request volleyn queueen
        mQueue.add(jsonObjectRequest);
    }
    private void parseJsonAndUpdateUi(JSONObject weatherObject) {
        // Kaivetaan jsonista data käyttöliittymäkomponentteihin
        TextView temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        try {
            double temperature = weatherObject.getJSONObject("main").getDouble("temp");
            temperatureTextView.setText("" + temperature + " C");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}