package com.example.a63serviciowifi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class Wirelesstester extends Service {
    private Tester test;
    final String tag = "Demo Servicio";
    public boolean enEjecucion = false;

    public Wirelesstester() {
    }

    public void onCreate() {
        Log.i(tag, "Servicio Creado");
        //necesitamos correrlo en hilo aparte
        test = new Tester();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!enEjecucion) {
            enEjecucion = true;
            test.start();
            Log.i(tag, "Servicio Arrancado");

        } else {
            Log.i(tag, "Servicio YA ESTABA Arrancado");
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }


    @Override
    public void onDestroy() {
        Log.i(tag, "Servicio Destruido");
        if (enEjecucion)
            test.interrupt();
    }

    public class Tester extends Thread {

        public boolean wifi_activo;


        @Override
        public void run() {
            while (enEjecucion) {

                try {
                    Log.i(tag, "servicio ejecutandose");

                    if (wifi_activo != compruebaConexionWifi()) {
                        //cambia de estado
                        wifi_activo = !wifi_activo;
                        if (wifi_activo)
                            Log.i(tag, "Conexion wifi activada");
                        else
                            Log.i(tag, "Conexion wifi desactivada");
                    }
                    this.sleep(3000);
                } catch (InterruptedException e) {
                    enEjecucion = false;
                    Log.i(tag, "hilo del servicio FINALIZADO");
                }
            }
        }

        public boolean compruebaConexionWifi() {
            ConnectivityManager conn = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conn != null) {
                NetworkInfo info = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

}