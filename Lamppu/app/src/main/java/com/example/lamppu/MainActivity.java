package com.example.lamppu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // Boolean, joka kertoo taskulampun tämän hetkisen tilan
    private boolean torchOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void flashOnOff(View view) {
        // Taskulamppu päälle/pois - yksiknertainen synkroninen kutsu

        // Otetaan yhteys Camera Manageriin
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        // Käydään kaikki kamerat läpi ja luetaan niiden ominaisuudet
        try {
            for (String id : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(id);
                // etsitään kamera, jossa on flash
                if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                    // Laitetaan flash päälle
                    torchOn = !torchOn;
                    cameraManager.setTorchMode(id, torchOn);
                }

            }
        } catch (CameraAccessException e) {
            //Tapahtui poikkeus (virhetilanne)
            e.printStackTrace();
        }

    }

    public void startSensors(View view) {
        // Käynnistetään kiihtyvyysanturit ja niiden kuuntelu
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Listataan kaikki laitteella olevat sensorit
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor s : sensorList) {
            Toast.makeText(this, s.getName(), Toast.LENGTH_SHORT).show();
        }

        // Tutkaillaan kiihtyvyysanturia (accelerometer)
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            // Laitteesta löytyi kiihtyvyysantyru. Rekisteröidään kuuntelemaan (listen) sitä
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Sensorin muutokset tulevat tänne
        float xSensor = sensorEvent.values[0];
        float ySensor = sensorEvent.values[1];
        float zSensor = sensorEvent.values[2];

        // Kirjoitetaan sensorien arvot näytölle TextViewiin
        TextView sensorTextView = (TextView) findViewById(R.id.sensorTextView);
        sensorTextView.setText("X: " + xSensor + "\nY: " + ySensor + "\nZ: " + zSensor);

        TextView colorChangeTextView = (TextView) findViewById(R.id.colorChangeTextView);

        if (xSensor < 0.05 && xSensor > -0.05) {
            // Laite on "vatupassissa" x-koordinaatin suhteen
            colorChangeTextView.setText("Straight");
        } else {
            colorChangeTextView.setText("Not Straight");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //
    }
}