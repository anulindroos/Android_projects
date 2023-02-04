package com.example.bmiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void backToMain(View view) {
        // Siirrytään takaisin
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("WEIGHT_UNIT", "lbs");
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //aktiviteetti on tulossa näkyviin
    }

    @Override
    protected void onResume() {
        super.onResume();
        // aktiviteetti on tullut näkyviin
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
}