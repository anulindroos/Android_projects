package com.example.systemappesimerkkilotto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyChargerBroadcastReceiver myChargerBroadcastReceiver;
    private MyLottoBroadcastReceiver myLottoBroadcastReceiver;
    // Todo: Aseta aktiviteetti kuuntelemaan laturin tilaa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myChargerBroadcastReceiver = new MyChargerBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);

        // Rekisteröidään oma broadcast receiver kuuntelemaan laturin kytkentää
        registerReceiver(myChargerBroadcastReceiver, intentFilter);

        myLottoBroadcastReceiver = new MyLottoBroadcastReceiver();
        IntentFilter lottoIntentFilter = new IntentFilter("com.tamk.mylottoservice.LOTTONUMBER");

        registerReceiver(myLottoBroadcastReceiver, lottoIntentFilter);
    }

    public void startLottoService (View v) {
        // Käynnistetään taustatehtävä Service
        Intent intent = new Intent(this, MyLottoService.class);
        intent.putExtra("LOTTO_NUMBER_AMOUNT", 7);

        startService(intent);
    }

    // Ns. inner class
    private class MyChargerBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Laturin kytkeminen pitäisi käsitellä täällä
            TextView chargerTextView = findViewById(R.id.chargerTextView);
            if (intent.getAction() == Intent.ACTION_POWER_CONNECTED) {
                // Laturi kytketty
                chargerTextView.setText("Power kytketty");
            } else if (intent.getAction() == Intent.ACTION_POWER_DISCONNECTED) {
                // Laturi irroitettu
                chargerTextView.setText("Power irroitettu");
            }
        }
        // Täältä päästään käsiksi isäntäluokan metodeihin, esim. UI:n päivittäminen
    }

    private class MyLottoBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == "com.tamk.mylottoservice.LOTTONUMBER") {
                int lottonumero = intent.getIntExtra("LOTTO_NUMBER", 0);
                TextView lottoTextView = findViewById(R.id.lottoTextView);
                lottoTextView.setText("Lottonumerot: " + lottonumero);
            }

        }
    }
}