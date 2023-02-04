package com.example.laiteapit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startPositioning(View view) {
        // Aloitetana käyttäjän sijainnin seuraaminen ja kirjaaminen TextViewiin
        // Otetaan yhteys paikkatietopalveluun (LocationManager)
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Kysytään käyttäjän tämän hetkinen lokaatiio
        // Location on ns. "dangerous" API, joten sen kysyminen edellyttää:
        // 1: location permission (coarse, fine location) manifest-tiedostoon
        //2: run-time -luvan käyttäjältä

        // Tarkistetaan, onko meillä Location Permission-lupa
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Pyydetään käyttäjältä lupa (run-time permission)
            String permissionToRequest [] = new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(this, permissionToRequest, 0 );
            return;
        }
        //Oikeudet kunnossa: rekisteröidytään kuuntelemaan location-päivityksiä
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Saatiin uusi lokaatio, päivitetään näytölle
        TextView locationTextView = findViewById(R.id.locationTextView);
        locationTextView.setText("Lat: " + location.getLatitude() + "\nLng: " + location.getLongitude());

    }

    public void callPhone(View view) {
        TextView phoneNo = findViewById(R.id.editTextPhone);
        String phoneNmbr = phoneNo.getText().toString();
        // Varmistetaan ensin, onko meillä lupa. Pyydetään tarvittaessa käyttäjältä
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, 0);
            return;
        }
        //Soitetaan puhelu
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel: " + phoneNmbr));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void openMap(View view) {
        String geoURI = "geo:61.4978,23.7610";
        Uri geo = Uri.parse(geoURI);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geo);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void setAlarm(View view) {
        EditText alarmMsg = findViewById(R.id.alarmMessage);
        String message = alarmMsg.getText().toString();
        EditText alarmHour = findViewById(R.id.alarmHours);
        int hour = Integer.parseInt(alarmHour.getText().toString());
        EditText alarmMinute = findViewById(R.id.alarmMinutes);
        int minutes = Integer.parseInt(alarmMinute.getText().toString());
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void createTextMessage(View view) {

    }

}