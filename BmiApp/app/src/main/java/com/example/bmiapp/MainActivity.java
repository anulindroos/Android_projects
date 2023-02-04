package com.example.bmiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Aktiviteetin tila (state) == aktiviteetin tietojäsenten data
    String mWeightUnit = "kg";
    float mLatestBmiResult = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getStringExtra("WEIGHT_UNIT") != null) {
            mWeightUnit = getIntent().getStringExtra("WEIGHT_UNIT");
            mLatestBmiResult = savedInstanceState.getFloat("LATEST_BMI", 0.0f);
            mWeightUnit = savedInstanceState.getString("WEIGHT_UNIT", "kg");
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Talletetaan olion tila
        savedInstanceState.putString("WEIGHT_UNIT", mWeightUnit);
        savedInstanceState.putFloat("LATEST_BMI", mLatestBmiResult);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        //Restore state members from saved instance
        mLatestBmiResult = savedInstanceState.getFloat("LATEST_BMI", 0.0f);
        mWeightUnit = savedInstanceState.getString("WEIGHT_UNIT", "kg");

        TextView weightTextView = findViewById(R.id.weightTextView);
        weightTextView.setText("Paino (" + mWeightUnit + ")");
        TextView bmiTextView = findViewById(R.id.bmiTextView);
        bmiTextView.setText(( "" + mLatestBmiResult));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //aktiviteetti on tulossa näkyviin
    }

    @Override
    protected void onResume() {
        super.onResume();
        // aktiviteetti tulee näkyviin
    }

    @Override
    protected void onStop() {
        super.onStop();
        //aktiviteetti on pois näkyvistä
    }

    @Override
    protected void onPause() {
        super.onPause();
        // aktiviteetti on menossa pois näkyvistä
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //aktiviteetti on tuhottu, siivottu muistista
    }

    public void calculateBMI(View view) {
        // Lasketaan painoindeksi ja näytetään käyttäjälle
        EditText heightEditText = findViewById(R.id.heightEditText);
        EditText weightEditText = findViewById(R.id.weightEditText);
        float height = Float.parseFloat(heightEditText.getText().toString());
        float weight = Float.parseFloat(weightEditText.getText().toString());
        mLatestBmiResult = weight / (height/100 * height/100);
        TextView bmiTextView = findViewById(R.id.bmiTextView);
        bmiTextView.setText("" + mLatestBmiResult);

    }

    public void changeColor(View view) {
        Button colorButton = findViewById(R.id.colorButton);
        colorButton.setBackgroundColor(Color.YELLOW);

        // Siirrytään asetus-aktiviteettiin
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }
}