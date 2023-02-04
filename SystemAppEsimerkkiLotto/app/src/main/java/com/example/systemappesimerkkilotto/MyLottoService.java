package com.example.systemappesimerkkilotto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyLottoService extends Service {
    private int mLottoNumberAmount = 0;
    public MyLottoService() {
    }

    @Override
    public int onStartCommand(Intent intent, int lags, int startId) {
        // Tässä tehdään itse palvelu. Luetaan ensin arvottavien lottonumeroiden määrä
        mLottoNumberAmount = intent.getIntExtra("LOTTO_NUMBER_AMOUNT", 0);
        // Oletuksena service ajetaan samassa pääsäikeessä -> luodaan tehtävälle oma säie

        new Thread( () -> {
            //Tämä ajetaan taustalla

            for (int i = 0; i < mLottoNumberAmount; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int lottonumero = (int) (Math.random() * 40);
                // lähetetään numero aktiviteetille broadcastina
                Intent lottoNumberIntent = new Intent("com.tamk.mylottoservice.LOTTONUMBER");
                lottoNumberIntent.putExtra("LOTTO_NUMBER", lottonumero);
                sendBroadcast(lottoNumberIntent);
            }
        }
        ).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}