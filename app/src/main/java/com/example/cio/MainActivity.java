package com.example.cio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.cio.WorkManager.CargaAsincrona;
import com.example.cio.utilidades.DataConverter;
import com.example.cio.utilidades.Utilidades;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    final static String TAG ="MainActivity";

    public static ConstraintLayout dialogo;
    public  static TextView tituloDialogo;
    public static TextView textoDialogo;

    public static ProgressBar progressDialogo;

    public static Button buttonDialogo;
    ImageView imgLogo;
    ImageView imgLogo2;
    TextView lblFecha;

    TextView version;
    Button btnIngresos;
    Button btnSalidas;
    Button btnVehiculos;
    Button btnEspeciales;
    Button btnVisitas;
    Button btnTecnica;
    Button btnGrupal;
    Button btnTransportista;
    Button btnEmergencia;
    Button btnLicencias;
    Button btnPanelCentral;
    Button btnSincronizar;
    TextView lblActualizarInformacion;

    CountDownTimer cdtLogo = null;
    boolean cdtCancel = false;

    ConexionSQLiteHelper conn;

    String CFGid_app,
            CFGwsConfiguraciones,
            CFGurl_logo;
            public static String tipoPase="";
    boolean esVehiculo = false;
    byte[] CFGimage_logo;
    String CFGwsInternoExterno,
            CFGwsVisitas,
            CFGwsTransportista,
            CFGwsEmergencia,
            CFGwsLicencias,
            CFGwsMarcaVehiculos,
            CFGwsTipoVehiculos,
            CFGwsTipoLicencia,
            CFGwsVehiculos,
            CFGwsVisitaTecnica,
            CFGwsIntExtRechazo,
            CFGwsVehicuRechazo,
            CFGwsEspeciRechazo,
            CFGwsPaseViRechazo,
            CFGwsLicenciaRechazo,
            CFGtiempo,
            CFGhabilitar_camara,
            CFGhabilitar_btnIngresos,
            CFGhabilitar_btnSalidas,
            CFGhabilitar_btnVehiculos,
            CFGhabilitar_btnEspeciales,
            CFGhabilitar_btnVisitas,
            CFGhabilitar_btnTecnica,
            CFGhabilitar_btnGrupal,
            CFGhabilitar_btnTransportista,
            CFGhabilitar_btnEmergencia,
            CFGhabilitar_btnLicencias;

    Bitmap bmpLogo = null;

    RequestQueue rq = null;

    MediaPlayer mP = null;
    final  static String SYNC_DATA_WORK_NAME = "SINCRONIZAR_AUTOMATIC";
    WorkRequest uploadWorkRequest =
            new OneTimeWorkRequest.Builder(CargaAsincrona.class)
                    .setInputData(createInputDataForClass())
                    .build();

    String versiontxt = BuildConfig.VERSION_NAME;

    private Data createInputDataForClass() {
        Data.Builder builder = new Data.Builder();
        builder.putString("tipo", "1");
        return builder.build();
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn = new ConexionSQLiteHelper(this, "DB_CIO", null, 1);
        dialogo = findViewById(R.id.fondoOscuro);
        tituloDialogo = findViewById(R.id.tituloDialog);
        textoDialogo = findViewById(R.id.textoDialog);
        progressDialogo = findViewById(R.id.progressDialgo);
        buttonDialogo = findViewById(R.id.butonDialog);
        version = findViewById(R.id.version);
        imgLogo = findViewById(R.id.imgLogo);
        imgLogo2 = findViewById(R.id.imgLogo2);
        lblFecha = findViewById(R.id.lblFecha);
        btnIngresos = findViewById(R.id.btnIngresos);
        btnSalidas = findViewById(R.id.btnSalidas);
        btnVehiculos = findViewById(R.id.btnVehiculos);
        btnEspeciales = findViewById(R.id.btnIngresoEspeciales);
        btnVisitas = findViewById(R.id.btnVisitas);
        btnGrupal = findViewById(R.id.btnVisitaGrupal);
        btnTecnica = findViewById(R.id.btnTecnica);
        btnTransportista = findViewById(R.id.btnTransportista);
        btnEmergencia = findViewById(R.id.btnEmergencia);
        btnLicencias = findViewById(R.id.btnLicencias);
        btnPanelCentral = findViewById(R.id.btnPanelCentral);
        btnSincronizar = findViewById(R.id.btnSincronizar);
        lblActualizarInformacion = findViewById(R.id.lblActualizarInformacion);

        btnVisitas.setVisibility(View.INVISIBLE);
        btnTecnica.setVisibility(View.INVISIBLE);
        btnGrupal.setVisibility(View.INVISIBLE);
        btnTransportista.setVisibility(View.INVISIBLE);
        btnEmergencia.setVisibility(View.INVISIBLE);
        btnPanelCentral.setVisibility(View.INVISIBLE);

        version.setText("V "+versiontxt);

        rq = Volley.newRequestQueue(getApplicationContext());

        //mantener presionado logo para entrar a configuraciones
        imgLogo2.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                cdtCancel = false;
                cdtLogo = new CountDownTimer(2000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (cdtCancel) cdtLogo.cancel();
                    }

                    @Override
                    public void onFinish() {
                        if (cdtCancel) {
                            cdtLogo.cancel();
                        } else {
                            Intent intent = new Intent(MainActivity.this, accesoConfiguracion.class);
                            startActivity(intent);
                        }
                    }
                }.start();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                cdtCancel = true;
            }
            return true;
        });

        //cargar o crear configuraciones app
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"id_app", "url_wsConfiguraciones", "url_logo", "image_logo",
                "url_wsInternoExterno", "url_wsVisitas", "url_wsTransportista",
                "url_wsEmergencia", "url_wsLicencias","url_wsMarcaVehiculos",
                "url_wsTipoVehiculos", "url_wsTipoLicencia", "url_wsVehiculos",
                "url_wsTecnica", "tiempo", "habilitar_camara", "habilitar_btnIngresos",
                "habilitar_btnSalidas", "habilitar_btnVehiculos", "habilitar_btnEspeciales",
                "habilitar_btnVisitas","habilitar_btnTecnica", "habilitar_btnTransportista",
                "habilitar_btnEmergencia", "habilitar_btnLicencias", "url_wsIntExt_Rechazo",
                "url_wsVehicu_Rechazo","habilitar_btnGrupal","url_wsEspeci_Rechazo",
                "url_wsPaseVi_Rechazo","url_wsLicenc_Rechazo"};
        String[] parametros = new String[]{};
        Cursor cursor = db.query("configuraciones", campos, null, parametros, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            CFGid_app = cursor.getString(0);
            CFGwsConfiguraciones = cursor.getString(1);
            CFGurl_logo = cursor.getString(2);
            CFGimage_logo = cursor.getBlob(3);
            CFGwsInternoExterno = cursor.getString(4);
            CFGwsVisitas = cursor.getString(5);
            CFGwsTransportista = cursor.getString(6);
            CFGwsEmergencia = cursor.getString(7);
            CFGwsLicencias = cursor.getString(8);
            CFGwsMarcaVehiculos = cursor.getString(9);
            CFGwsTipoVehiculos = cursor.getString(10);
            CFGwsTipoLicencia = cursor.getString(11);
            CFGwsVehiculos = cursor.getString(12);
            CFGwsVisitaTecnica = cursor.getString(13);
            CFGtiempo = cursor.getString(14);
            CFGhabilitar_camara = cursor.getString(15);
            CFGhabilitar_btnIngresos = cursor.getString(16);
            CFGhabilitar_btnSalidas = cursor.getString(17);
            CFGhabilitar_btnVehiculos = cursor.getString(18);
            CFGhabilitar_btnEspeciales = cursor.getString(19);
            CFGhabilitar_btnVisitas = cursor.getString(20);
            CFGhabilitar_btnTecnica = cursor.getString(21);
            CFGhabilitar_btnTransportista = cursor.getString(22);
            CFGhabilitar_btnEmergencia = cursor.getString(23);
            CFGhabilitar_btnLicencias = cursor.getString(24);
            CFGwsIntExtRechazo = cursor.getString(25);
            CFGwsVehicuRechazo = cursor.getString(26);
            CFGhabilitar_btnGrupal = cursor.getString(27);
            CFGwsEspeciRechazo = cursor.getString(28);
            CFGwsPaseViRechazo = cursor.getString(29);
            CFGwsLicenciaRechazo = cursor.getString(30);

            if (CFGimage_logo != null) {
                bmpLogo = DataConverter.convertByteArray2Image(CFGimage_logo);
                imgLogo.setImageBitmap(bmpLogo);
                //imgLogo2.setVisibility(View.INVISIBLE);
            }
        } else {
            //valores iniciales
            String id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            CFGid_app = id+Utilidades.generarAppID();
            CFGhabilitar_camara = "0";
            CFGhabilitar_btnIngresos = "1";
            CFGhabilitar_btnSalidas = "1";
            CFGhabilitar_btnVehiculos = "1";
            CFGhabilitar_btnEspeciales = "1";
            CFGhabilitar_btnVisitas = "1";
            CFGhabilitar_btnTecnica = "1";
            CFGhabilitar_btnTransportista = "1";
            CFGhabilitar_btnEmergencia = "1";
            CFGhabilitar_btnLicencias = "1";
            CFGhabilitar_btnGrupal = "1";

            cursor.close();
            db.close();
            db = conn.getWritableDatabase();
            db.execSQL(
                "INSERT INTO configuraciones(id_app, url_wsConfiguraciones, url_logo, " +
                        "image_logo, url_wsInternoExterno, url_wsVisitas, " +
                        "url_wsTransportista, url_wsEmergencia, url_wsLicencias, " +
                        "habilitar_camara, habilitar_btnIngresos, habilitar_btnSalidas, " +
                        "habilitar_btnVehiculos, habilitar_btnEspeciales, " +
                        "habilitar_btnVisitas, habilitar_btnTecnica, " +
                        "habilitar_btnTransportista, habilitar_btnEmergencia, " +
                        "habilitar_btnLicencias, habilitar_btnGrupal) " +
                " VALUES('" + CFGid_app + "', NULL, NULL, NULL, NULL, NULL, NULL, NULL, " +
                        "NULL, '" + CFGhabilitar_camara + "', '" + CFGhabilitar_btnIngresos +
                        "', '" + CFGhabilitar_btnSalidas + "', '" + CFGhabilitar_btnVehiculos +
                        "', '" + CFGhabilitar_btnEspeciales + "', '" + CFGhabilitar_btnVisitas +
                        "', '" + CFGhabilitar_btnTecnica + "', '" + CFGhabilitar_btnTransportista +
                        "', '" + CFGhabilitar_btnEmergencia + "', '" + CFGhabilitar_btnLicencias +
                        "', '" + CFGhabilitar_btnGrupal + "')"
            );
            db.close();
        }
        //aplicar configuraciones
        if(CFGhabilitar_btnIngresos.equals("1")){
            btnIngresos.setVisibility(View.VISIBLE);
        } else {
            btnIngresos.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnSalidas.equals("1")){
            btnSalidas.setVisibility(View.VISIBLE);
        } else {
            btnSalidas.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnVehiculos.equals("1")){
            btnVehiculos.setVisibility(View.VISIBLE);
        } else {
            btnVehiculos.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnEspeciales.equals("1")){
            btnEspeciales.setVisibility(View.VISIBLE);
        } else {
            btnEspeciales.setVisibility(View.GONE);
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 200);
        Log.i(TAG,"El tiempo programado es "+CFGtiempo);
        if(CFGtiempo != null){
            PeriodicWorkRequest periodicSyncDataWork =
                    new PeriodicWorkRequest.Builder(
                            CargaAsincrona.class,
                            Long.parseLong(CFGtiempo),
                            TimeUnit.MILLISECONDS)
                            .setInputData(createInputDataForClass())
                            .build();
            WorkManager.getInstance(getApplicationContext()).cancelAllWork();
            WorkManager
                    .getInstance(getApplicationContext())
                    .enqueueUniquePeriodicWork(
                            SYNC_DATA_WORK_NAME,
                            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                            periodicSyncDataWork
                    );
        }
        buttonDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.setVisibility(View.INVISIBLE);
            }
        });

    }
    @Override public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 200) if(!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) Toast.makeText(getApplicationContext(), "Falta permiso cámara", Toast.LENGTH_SHORT).show();
    }
    @Override protected void onResume() {
        lblFecha.setText(Utilidades.fechaHoy());
        super.onResume();
    }
    @Override public void onBackPressed() {
        if(btnPanelCentral.getVisibility() == View.VISIBLE){
            onclickPanelCentral(null);
        }else {
            super.onBackPressed();
        }
    }

    public void onclickSincronizar(View view) {
        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(uploadWorkRequest);
    }

    /*INTERFAZ BOTONES*/
    public void onclickBtnIngresos(View view) {
        tipoPase="ingresos"+tipoPase;
        if(esVehiculo){
            Log.i(TAG,"la variable vehiculo es "+esVehiculo);
            try {
                mP.stop();
            }catch (Exception e){ }
            try {
                mP.release();
            }catch (Exception e){ }
            try {
                mP = null;
            }catch (Exception e){ }
            mP = MediaPlayer.create(getApplicationContext(), R.raw.ingresovehicular);
            mP.start();
            esVehiculo = false;
            btnVehiculos.setVisibility(View.VISIBLE);
            btnEspeciales.setVisibility(View.VISIBLE);
            lblActualizarInformacion.setVisibility(View.VISIBLE);
            btnSincronizar.setVisibility(View.VISIBLE);
            btnLicencias.setVisibility(View.VISIBLE);
            lblActualizarInformacion.setVisibility(View.VISIBLE);
            btnPanelCentral.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(MainActivity.this, Control.class);
            intent.putExtra("destino",tipoPase);
            tipoPase = "";
            startActivity(intent);
        }else{
            try {
                mP.stop();
            }catch (Exception e){ }
            try {
                mP.release();
            }catch (Exception e){ }
            try {
                mP = null;
            }catch (Exception e){ }
            mP = MediaPlayer.create(getApplicationContext(), R.raw.registrandoacceso);
            mP.start();
            Intent intent = new Intent(MainActivity.this, Control.class);
            intent.putExtra("destino",tipoPase);
            tipoPase = tipoPase.replace("ingresos","");
            startActivity(intent);
        }
    }
    public void onclickBtnSalidas(View view) {
        tipoPase="salidas"+tipoPase;
        if(esVehiculo){
            try {
                mP.stop();
            }catch (Exception e){ }
            try {
                mP.release();
            }catch (Exception e){ }
            try {
                mP = null;
            }catch (Exception e){ }
            mP = MediaPlayer.create(getApplicationContext(), R.raw.salidavehicular);
            mP.start();
            esVehiculo = false;
            btnVehiculos.setVisibility(View.VISIBLE);
            btnEspeciales.setVisibility(View.VISIBLE);
            lblActualizarInformacion.setVisibility(View.VISIBLE);
            btnSincronizar.setVisibility(View.VISIBLE);
            btnLicencias.setVisibility(View.VISIBLE);
            lblActualizarInformacion.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, Control.class);
            intent.putExtra("destino",tipoPase);
            tipoPase = "";
            startActivity(intent);
        }else{
            try {
                mP.stop();
            }catch (Exception e){ }
            try {
                mP.release();
            }catch (Exception e){ }
            try {
                mP = null;
            }catch (Exception e){ }
            mP = MediaPlayer.create(getApplicationContext(), R.raw.registrandosalida);
            mP.start();
            Intent intent = new Intent(MainActivity.this, Control.class);
            intent.putExtra("destino",tipoPase);
            tipoPase = tipoPase.replace("salidas","");
            startActivity(intent);
        }
    }
    public void onclickBtnVehiculos(View view) {
        esVehiculo = true;
        tipoPase="Vehicular";
        btnLicencias.setVisibility(View.INVISIBLE);
        btnVehiculos.setVisibility(View.INVISIBLE);
        btnEspeciales.setVisibility(View.INVISIBLE);
        btnSincronizar.setVisibility(View.VISIBLE);
        lblActualizarInformacion.setVisibility(View.VISIBLE);
        btnPanelCentral.setVisibility(View.VISIBLE);
    }
    public void onclickEspeciales(View view) {
        if(CFGhabilitar_btnVisitas.equals("1")){
            btnVisitas.setVisibility(View.VISIBLE);
        } else {
            btnVisitas.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnTecnica.equals("1")){
            btnTecnica.setVisibility(View.VISIBLE);
        } else {
            btnTecnica.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnGrupal.equals("1")){
            btnGrupal.setVisibility(View.VISIBLE);
        } else {
            btnGrupal.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnTransportista.equals("1")){
            btnTransportista.setVisibility(View.VISIBLE);
        } else {
            btnTransportista.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnEmergencia.equals("1")){
            btnEmergencia.setVisibility(View.VISIBLE);
        } else {
            btnEmergencia.setVisibility(View.GONE);
        }

        btnPanelCentral.setVisibility(View.VISIBLE);

        btnIngresos.setVisibility(View.INVISIBLE);
        btnSalidas.setVisibility(View.INVISIBLE);
        btnVehiculos.setVisibility(View.INVISIBLE);
        btnLicencias.setVisibility(View.INVISIBLE);
        btnEspeciales.setVisibility(View.INVISIBLE);
        lblActualizarInformacion.setVisibility(View.INVISIBLE);
        btnSincronizar.setVisibility(View.INVISIBLE);
    }
    public void onclickBtnVisitas(View view) {
        tipoPase="visitas";
        btnIngresos.setVisibility(View.VISIBLE);
        btnSalidas.setVisibility(View.VISIBLE);
        btnSincronizar.setVisibility(View.VISIBLE);
        lblActualizarInformacion.setVisibility(View.VISIBLE);

        btnVisitas.setVisibility(View.INVISIBLE);
        btnTecnica.setVisibility(View.INVISIBLE);
        btnGrupal.setVisibility(View.INVISIBLE);
        btnTransportista.setVisibility(View.INVISIBLE);
        btnEmergencia.setVisibility(View.INVISIBLE);
    }
    public void onclickBtnTecnica(View view) {
        tipoPase="tecnica";
        btnIngresos.setVisibility(View.VISIBLE);
        btnSalidas.setVisibility(View.VISIBLE);
        btnSincronizar.setVisibility(View.VISIBLE);
        lblActualizarInformacion.setVisibility(View.VISIBLE);

        btnVisitas.setVisibility(View.INVISIBLE);
        btnTecnica.setVisibility(View.INVISIBLE);
        btnGrupal.setVisibility(View.INVISIBLE);
        btnTransportista.setVisibility(View.INVISIBLE);
        btnEmergencia.setVisibility(View.INVISIBLE);
    }
    public void onclickBtnGrupal(View view) {
        tipoPase="grupal";
        btnIngresos.setVisibility(View.VISIBLE);
        btnSalidas.setVisibility(View.VISIBLE);
        btnSincronizar.setVisibility(View.VISIBLE);
        lblActualizarInformacion.setVisibility(View.VISIBLE);

        btnVisitas.setVisibility(View.INVISIBLE);
        btnTecnica.setVisibility(View.INVISIBLE);
        btnGrupal.setVisibility(View.INVISIBLE);
        btnTransportista.setVisibility(View.INVISIBLE);
        btnEmergencia.setVisibility(View.INVISIBLE);
    }
    public void onclickBtnTransportista(View view) {
        tipoPase="transportista";
        btnIngresos.setVisibility(View.VISIBLE);
        btnSalidas.setVisibility(View.VISIBLE);
        btnSincronizar.setVisibility(View.VISIBLE);
        lblActualizarInformacion.setVisibility(View.VISIBLE);

        btnVisitas.setVisibility(View.INVISIBLE);
        btnTecnica.setVisibility(View.INVISIBLE);
        btnGrupal.setVisibility(View.INVISIBLE);
        btnTransportista.setVisibility(View.INVISIBLE);
        btnEmergencia.setVisibility(View.INVISIBLE);
    }
    public void onclickBtnEmergencia(View view) {
        try {
            mP.stop();
        }catch (Exception e){ }
        try {
            mP.release();
        }catch (Exception e){ }
        try {
            mP = null;
        }catch (Exception e){ }
        mP = MediaPlayer.create(getApplicationContext(), R.raw.registrandoemergencia);
        mP.start();
        Intent intent = new Intent(MainActivity.this, Control.class);
        intent.putExtra("destino","emergencia");
        startActivity(intent);
    }
    public void onclickBtnLicencias(View view) {
        try {
            mP.stop();
        }catch (Exception e){ }
        try {
            mP.release();
        }catch (Exception e){ }
        try {
            mP = null;
        }catch (Exception e){ }
        mP = MediaPlayer.create(getApplicationContext(), R.raw.registrandolicencia);
        mP.start();
        Intent intent = new Intent(MainActivity.this, Control.class);
        intent.putExtra("destino","licencias");
        startActivity(intent);
    }
    public void onclickPanelCentral(View view) {
        tipoPase="";
        Log.i(TAG,"El tipo de comtrol es "+tipoPase);
        if(CFGhabilitar_btnIngresos.equals("1")){
            btnIngresos.setVisibility(View.VISIBLE);
        } else {
            btnIngresos.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnSalidas.equals("1")){
            btnSalidas.setVisibility(View.VISIBLE);
        } else {
            btnSalidas.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnLicencias.equals("1")){
            btnLicencias.setVisibility(View.VISIBLE);
        } else {
            btnLicencias.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnVehiculos.equals("1")){
            btnVehiculos.setVisibility(View.VISIBLE);
        } else {
            btnVehiculos.setVisibility(View.GONE);
        }
        if(CFGhabilitar_btnEspeciales.equals("1")){
            btnEspeciales.setVisibility(View.VISIBLE);
        } else {
            btnEspeciales.setVisibility(View.GONE);
        }

        btnSincronizar.setVisibility(View.VISIBLE);
        lblActualizarInformacion.setVisibility(View.VISIBLE);

        btnVisitas.setVisibility(View.INVISIBLE);
        btnTecnica.setVisibility(View.INVISIBLE);
        btnGrupal.setVisibility(View.INVISIBLE);
        btnTransportista.setVisibility(View.INVISIBLE);
        btnEmergencia.setVisibility(View.INVISIBLE);
        btnPanelCentral.setVisibility(View.INVISIBLE);
    }
    /* ./INTERFAZ BOTONES*/
}