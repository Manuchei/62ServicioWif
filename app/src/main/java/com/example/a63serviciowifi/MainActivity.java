package com.example.a63serviciowifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*Arranca el servicio WirelessTester */

    public void Arrancar(View v) {
        startService(new Intent(getBaseContext(), Wirelesstester.class));
    }
    /*Detiene el servicio WirelessTester */

    public void Detener(View v) {
        stopService(new Intent(getBaseContext(), Wirelesstester.class));
    }
}