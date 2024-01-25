package com.example.cio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.cio.WorkManager.CargaAsincrona;
import com.example.cio.utilidades.DataConverter;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.cio.utilidades.Utilidades.TIMESTAMP;
import static com.example.cio.utilidades.Utilidades.VRCHR;
import static com.example.cio.utilidades.Utilidades.date2spa;
import static com.example.cio.utilidades.Utilidades.fecha1MayorQueFecha2;
import static com.example.cio.utilidades.Utilidades.ifN;
import static com.example.cio.utilidades.Utilidades.TIMESTAMPdate;

public class Control extends AppCompatActivity {
    final static String TAG = "ControlActivity";

    public static ConstraintLayout dialogo;
    public  static TextView tituloDialogo;
    public static TextView textoDialogo;

    public static ProgressBar progressDialogo;

    public static Button buttonDialogo;

    ImageView imgLogo;
    ImageView imgVehiculo, imgConductor,imgPasajeros;
    TextView txtVehiculo, txtConductor, txtPasajeros;
    TextView lblTitulo;
    TextView lblResultado;
    TextView lblNombre;
    TextView lblCargo;
    TextView textoAyuda;
    EditText txtEscanear;
    Button btnCamara,btnActualiza,btnTeclado,btnCerrar,btnPanel,btnTeclado2,btnCamara2;
    TableLayout tablaTeclado;
    Button button;
    ConstraintLayout dialogoV, resultadoVehi, resultadoCho,constraintTable, constraintVehiculo, constraintImageVehiculo;
    TextView rut1, rut2, rut3, nombre1, nombre2, nombre3, respuesta1,respuesta2,respuesta3;
    TextView titulo;
    TextView subTitulo;
    EditText input;
    TextView nombre;
    TextView cargo;
    TextView resultadoPatente,resultadoVehiculo;
    TextView resultadoRut, resultadoEmpresa, resultadoNombre, resultadoChofer;

    Vibrator vibrator;

    String tipoControl;
    Long idGrupal,idGrupalRechaza;
    Boolean hayResultadoChofer = false;
    String tipoVehiculo;

    ConexionSQLiteHelper conn;

    String CFGid_app,
            CFGwsConfiguraciones,
            CFGurl_logo;
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
            CFGhabilitar_camara;

    private static Bitmap bmpLogo = null;
    RequestQueue rq = null;

    MediaPlayer mP = null;

    WorkRequest uploadWorkRequest =
            new OneTimeWorkRequest.Builder(CargaAsincrona.class)
                    .setInputData(createInputDataForClass())
                    .build();
    private Data createInputDataForClass() {
        Data.Builder builder = new Data.Builder();
        builder.putString("tipo", "2");
        return builder.build();
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        conn = new ConexionSQLiteHelper(this, "DB_CIO", null, 1);

        dialogo = findViewById(R.id.fondoOscuro);
        tituloDialogo = findViewById(R.id.tituloDialog);
        textoDialogo = findViewById(R.id.textoDialog);
        progressDialogo = findViewById(R.id.progressDialgo);
        buttonDialogo = findViewById(R.id.butonDialog);

        imgLogo = findViewById(R.id.imgLogo);
        imgVehiculo = findViewById(R.id.ImagenVehiculo);
        imgConductor = findViewById(R.id.ImagenChofer);
        imgPasajeros = findViewById(R.id.ImagenPasajeros);
        txtVehiculo = findViewById(R.id.LabelVehiculo);
        txtConductor = findViewById(R.id.LabelChofer);
        txtPasajeros = findViewById(R.id.LabelPasajeros);
        lblTitulo = findViewById(R.id.lblTitulo);
        lblResultado = findViewById(R.id.lblResultado);
        lblNombre = findViewById(R.id.lblNombre);
        lblCargo = findViewById(R.id.lblCargo);
        lblTitulo = findViewById(R.id.lblTitulo);
        txtEscanear = findViewById(R.id.txtEscanear);
        btnCamara = findViewById(R.id.btnCamara);
        btnActualiza = findViewById(R.id.btnSincronizar);
        btnTeclado = findViewById(R.id.btnTeclado);
        btnTeclado2 = findViewById(R.id.btnTeclado2);
        btnCamara2 = findViewById(R.id.btnCamara2);
        btnCerrar = findViewById(R.id.btnPanelCerrarGrupo);
        btnPanel = findViewById(R.id.btnPanelCentral);
        tablaTeclado = findViewById(R.id.tablaTeclado);
        textoAyuda = findViewById(R.id.textView4);
        dialogoV = findViewById(R.id.constraintDialogo);
        resultadoVehi = findViewById(R.id.constraintResultadoVehiculo);
        resultadoCho = findViewById(R.id.constraintResultadoChofer);
        constraintTable = findViewById(R.id.constraintTable);
        constraintVehiculo = findViewById(R.id.constraintVehiculo);
        constraintImageVehiculo = findViewById(R.id.constraintImagenVehiculo);
        rut1 = findViewById(R.id.rut1);
        rut2 = findViewById(R.id.rut2);
        rut3 = findViewById(R.id.rut3);
        nombre1 = findViewById(R.id.nombre1);
        nombre2 = findViewById(R.id.nombre2);
        nombre3 = findViewById(R.id.nombre3);
        respuesta1 = findViewById(R.id.respuesta1);
        respuesta2 = findViewById(R.id.respuesta2);
        respuesta3 = findViewById(R.id.respuesta3);
        titulo = findViewById(R.id.titulo);
        subTitulo = findViewById(R.id.subTitulo);
        input = findViewById(R.id.inputText);
        nombre = findViewById(R.id.nombre);
        cargo = findViewById(R.id.cargo);
        button = findViewById(R.id.boton);
        resultadoPatente = findViewById(R.id.resultadoPatente);
        resultadoVehiculo = findViewById(R.id.resultadoVehiculo);
        resultadoRut = findViewById(R.id.resultadoRut);
        resultadoNombre = findViewById(R.id.resultadoNombre);
        resultadoEmpresa = findViewById(R.id.resultadoEmpresa);
        resultadoChofer = findViewById(R.id.resultadoChofer);

        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        rq = Volley.newRequestQueue(getApplicationContext());

        lblResultado.setVisibility(View.INVISIBLE);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            tipoControl = bundle.getString("destino");
            formatoTipoControl();
        }

        //cuando se usa el laser para escanear
        txtEscanear.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            ocultarTecladoAndroid();
            if(txtEscanear.getText().length()>0){
                if(actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    ocultarTecladoVirtual();
                    String rutLimpio = filtrarRut(txtEscanear.getText().toString());
                    if(validarRut(rutLimpio)){
                        procesarRut(rutLimpio);
                    }else{
                        mostrarAlerta("RUT INVÁLIDO", "warning", "short");
                        txtEscanear.setText("");
                        limpiarPantalla();
                    }
                    return true;
                }
            }
            ocultarTecladoAndroid();
            return false;
        });

        //cargar configuraciones
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"id_app", "url_wsConfiguraciones", "url_logo", "image_logo", "url_wsInternoExterno", "url_wsVisitas", "url_wsTransportista", "url_wsEmergencia", "url_wsLicencias", "url_wsMarcaVehiculos", "url_wsTipoVehiculos", "url_wsTipoLicencia", "url_wsVehiculos", "habilitar_camara"};
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
            CFGhabilitar_camara = cursor.getString(13);

            if (CFGimage_logo != null) {
                bmpLogo = DataConverter.convertByteArray2Image(CFGimage_logo);
                imgLogo.setImageBitmap(bmpLogo);
            }
            if(CFGhabilitar_camara.equals("1")){
                if(tipoControl.equals("ingresosVehicular")||tipoControl.equals("salidasVehicular")){
                    btnCamara2.setVisibility(View.VISIBLE);
                }else{
                    btnCamara.setVisibility(View.VISIBLE);
                }
            }else{
                if(tipoControl.equals("ingresosVehicular")||tipoControl.equals("salidasVehicular")){
                    btnCamara2.setVisibility(View.GONE);
                }else{
                    btnCamara.setVisibility(View.INVISIBLE);
                }
            }
        }
        imgVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoVehiculo = "vehiculo";
                btnPanel.setVisibility(View.GONE);
                btnCerrar.setVisibility(View.GONE);
                formulario();
            }
        });
        imgConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idGrupal!=null){
                    tipoVehiculo = "conductor";
                    formulario();
                }else{
                    Toast.makeText(Control.this,"Debe ingresar el vehiculo primero",Toast.LENGTH_LONG).show();
                }
            }
        });
        imgPasajeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idGrupal!=null){
                    tipoVehiculo = "pasajeros";
                    formulario();
                }else{
                    Toast.makeText(Control.this,"Debe ingresar el vehiculo primero",Toast.LENGTH_LONG).show();
                }
            }
        });
        txtEscanear.requestFocus();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txtEscanear.getWindowToken(), 0);
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,250);
        buttonDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0){
            if(requestCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    String rutLimpio = filtrarRut(barcode.displayValue);
                    if(tipoControl.equals("ingresosVehicular")||tipoControl.equals("salidasVehicular")){
                        if(validarRut(rutLimpio)){
                            if(tipoVehiculo.equals("conductor")){
                                procesarChofer(rutLimpio);
                            }else {
                                procesarPasajero(rutLimpio);
                            }
                        }else{
                            mostrarAlerta("RUT INVÁLIDO", "warning", "short");
                            input.setText("");
                        }
                    }else {
                        if(validarRut(rutLimpio)){
                            procesarRut(rutLimpio);
                        }else{
                            mostrarAlerta("RUT INVÁLIDO", "warning", "short");
                            txtEscanear.setText("");
                            limpiarPantalla();
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override protected void onResume() {
        ocultarTecladoVirtual();
        super.onResume();
    }
    @Override public void onBackPressed() {
        if(tablaTeclado.getVisibility() == View.VISIBLE){
            ocultarTecladoVirtual();
        }else {
            super.onBackPressed();
        }
    }

    /*INTERFAZ BOTONES*/
    public void formatoTipoControl(){
        lblResultado.setVisibility(View.INVISIBLE);
        lblNombre.setVisibility(View.INVISIBLE);
        lblCargo.setVisibility(View.INVISIBLE);
        dialogoV.setVisibility(View.GONE);
        resultadoVehi.setVisibility(View.GONE);
        resultadoCho.setVisibility(View.GONE);
        constraintTable.setVisibility(View.GONE);
        btnCerrar.setVisibility(View.GONE);
        btnTeclado2.setVisibility(View.GONE);
        btnCamara2.setVisibility(View.GONE);
        Log.i(TAG,"El tipo de comtrol es "+tipoControl);
        switch(tipoControl){
            case "salidasVehicular":
                lblTitulo.setText(R.string.control_sal_vec);
                btnCerrar.setVisibility(View.VISIBLE);
                txtEscanear.setVisibility(View.GONE);
                textoAyuda.setVisibility(View.GONE);
                btnCamara.setVisibility(View.GONE);
                btnActualiza.setVisibility(View.GONE);
                btnTeclado.setVisibility(View.GONE);
                imgVehiculo.setVisibility(View.VISIBLE);
                imgConductor.setVisibility(View.VISIBLE);
                imgPasajeros.setVisibility(View.VISIBLE);
                txtVehiculo.setVisibility(View.VISIBLE);
                txtConductor.setVisibility(View.VISIBLE);
                txtPasajeros.setVisibility(View.VISIBLE);
            case "ingresosVehicular":
                lblTitulo.setText(R.string.control_ing_vec);
                btnCerrar.setVisibility(View.VISIBLE);
                txtEscanear.setVisibility(View.GONE);
                textoAyuda.setVisibility(View.GONE);
                btnCamara.setVisibility(View.GONE);
                btnActualiza.setVisibility(View.GONE);
                btnTeclado.setVisibility(View.GONE);
                imgVehiculo.setVisibility(View.VISIBLE);
                imgConductor.setVisibility(View.VISIBLE);
                imgPasajeros.setVisibility(View.VISIBLE);
                txtVehiculo.setVisibility(View.VISIBLE);
                txtConductor.setVisibility(View.VISIBLE);
                txtPasajeros.setVisibility(View.VISIBLE);
                break;
            case "ingresos":
                lblTitulo.setText(R.string.control_ingresos);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                lblTitulo.setTextColor(getResources().getColor(R.color.azulControlandoIngresos));
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                break;
            case "salidas":
                lblTitulo.setText(R.string.control_salidas);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                lblTitulo.setTextColor(getResources().getColor(R.color.rojoControlandoSalida));
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                break;
            case "ingresosvisitas":
                lblTitulo.setText(R.string.control_entrada_visitas);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.azulControlandoIngresos));
                break;
            case "salidasvisitas":
                lblTitulo.setText(R.string.control_salida_visitas);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.rojoControlandoVisitas));
                break;
            case "ingresostecnica":
                lblTitulo.setText(R.string.control_tecnica_ent);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.azulControlandoIngresos));
                break;
            case "salidastecnica":
                lblTitulo.setText(R.string.control_tecnica_sal);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.rojoControlandoVisitas));
                break;
            case "ingresosgrupal":
                lblTitulo.setText(R.string.control_grupal_ent);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.azulControlandoIngresos));
                break;
            case "ingresostransportista":
                lblTitulo.setText(R.string.control_fletes_ent);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.azulControlandoIngresos));
                break;
            case "salidasgrupal":
                lblTitulo.setText(R.string.control_grupal_sal);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.rojoControlandoSalida));
                break;
            case "salidastransportista":
                lblTitulo.setText(R.string.control_fletes_sal);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.rojoControlandoSalida));
                break;
            case "emergencia":
                lblTitulo.setText(R.string.control_emergencia);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.rojoControlandoVisitas));
                break;
            case "licencias":
                lblTitulo.setText(R.string.control_licencia);
                txtEscanear.setHint(R.string.texto_escaner_persona);
                textoAyuda.setVisibility(View.VISIBLE);
                textoAyuda.setVisibility(View.VISIBLE);
                btnCamara.setVisibility(View.VISIBLE);
                btnActualiza.setVisibility(View.VISIBLE);
                btnTeclado.setVisibility(View.VISIBLE);
                imgVehiculo.setVisibility(View.GONE);
                imgConductor.setVisibility(View.GONE);
                imgPasajeros.setVisibility(View.GONE);
                txtVehiculo.setVisibility(View.GONE);
                txtConductor.setVisibility(View.GONE);
                txtPasajeros.setVisibility(View.GONE);
                lblTitulo.setTextColor(getResources().getColor(R.color.rojoControlandoVisitas));
                break;
        }
    }
    public String formatoLicencia(String licencia){
        String traduccion="";
        switch (licencia) {
            case "1":
                traduccion = "A1";
                break;
            case "2":
                traduccion = "A2";
                break;
            case "3":
                traduccion = "A3";
                break;
            case "4":
                traduccion = "A4";
                break;
            case "5":
                traduccion = "A5";
                break;
            case "6":
                traduccion = "B";
                break;
            case "7":
                traduccion = "D";
                break;
            case "8":
                traduccion = "F";
                break;
        }
        return traduccion;
    }
    public void onclickTxtEscanear(View view) {
        ocultarTecladoAndroid();
        /*new CountDownTimer(200, 200) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                ocultarTecladoAndroid();
                new CountDownTimer(1200, 800) {
                    public void onTick(long millisUntilFinished) { ocultarTecladoAndroid(); }
                    public void onFinish() {
                        ocultarTecladoAndroid();
                    }
                }.start();
            }
        }.start();*/
        view.requestFocus();
        mostrarTecladoVirtual();
    }
    public void onclickTxtEscanear2(View view) {
        if(tipoVehiculo.equals("vehiculo")){
            btnPanel.setVisibility(View.GONE);
            btnCerrar.setVisibility(View.GONE);
        }else{
            new CountDownTimer(200, 200) {
                public void onTick(long millisUntilFinished) { }
                public void onFinish() {
                    ocultarTecladoAndroid();
                    new CountDownTimer(1200, 800) {
                        public void onTick(long millisUntilFinished) { ocultarTecladoAndroid(); }
                        public void onFinish() {
                            ocultarTecladoAndroid();
                        }
                    }.start();
                }
            }.start();
            mostrarTecladoVirtual();
        }
    }
    public void onclickCamara(View view) {
        try {
            Intent intent = new Intent(this, scan_barcode.class);
            startActivityForResult(intent, 0);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void onclickTeclado(View view) {
        mostrarTecladoVirtual();
    }
    private void mostrarTecladoVirtual() {
        if(tipoControl.equals("ingresosVehicular")||tipoControl.equals("salidasVehicular")){
            lblResultado.setVisibility(View.GONE);
            lblNombre.setVisibility(View.GONE);
            lblCargo.setVisibility(View.GONE);
            btnCerrar.setVisibility(View.GONE);
            btnPanel.setVisibility(View.GONE);
        }else{
            imgLogo.setVisibility(View.GONE);
            lblTitulo.setVisibility(View.GONE);
            lblResultado.setVisibility(View.GONE);
            lblNombre.setVisibility(View.GONE);
            lblCargo.setVisibility(View.GONE);
            btnCerrar.setVisibility(View.GONE);
            btnPanel.setVisibility(View.GONE);
            resultadoVehi.setVisibility(View.GONE);
            resultadoCho.setVisibility(View.GONE);
            constraintTable.setVisibility(View.GONE);
            constraintVehiculo.setVisibility(View.GONE);
            constraintImageVehiculo.setVisibility(View.GONE);
        }
        tablaTeclado.setVisibility(View.VISIBLE);
    }
    private void ocultarTecladoVirtual() {
        imgLogo.setVisibility(View.VISIBLE);
        lblTitulo.setVisibility(View.VISIBLE);
        btnPanel.setVisibility(View.VISIBLE);
        if(tipoControl.equals("ingresosVehicular")||tipoControl.equals("salidasVehicular")){
            btnCerrar.setVisibility(View.VISIBLE);
            constraintTable.setVisibility(View.VISIBLE);
            constraintVehiculo.setVisibility(View.VISIBLE);
            constraintImageVehiculo.setVisibility(View.VISIBLE);
        }else{
            lblResultado.setVisibility(View.VISIBLE);
            lblNombre.setVisibility(View.VISIBLE);
            lblCargo.setVisibility(View.VISIBLE);
        }
        if(idGrupal!=null){
            resultadoVehi.setVisibility(View.VISIBLE);
        }
        if(hayResultadoChofer){
            resultadoCho.setVisibility(View.VISIBLE);
        }
        tablaTeclado.setVisibility(View.GONE);
    }
    private void ocultarTecladoAndroid() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public void onclickBtn1(View view) { escribir("1"); }
    public void onclickBtn2(View view) { escribir("2"); }
    public void onclickBtn3(View view) { escribir("3"); }
    public void onclickBtn4(View view) { escribir("4"); }
    public void onclickBtn5(View view) { escribir("5"); }
    public void onclickBtn6(View view) { escribir("6"); }
    public void onclickBtn7(View view) { escribir("7"); }
    public void onclickBtn8(View view) { escribir("8"); }
    public void onclickBtn9(View view) { escribir("9"); }
    public void onclickBtn0(View view) { escribir("0"); }
    public void onclickBtnK(View view) { escribir("K"); }
    public void onclickBtnBorrar(View view) {
        vibrator.vibrate(60);
        if(tipoControl.equals("ingresosVehicular")||tipoControl.equals("salidasVehicular")){
            if(input.getText().toString().length() > 0){
                input.setText(input.getText().toString().substring(0, input.getText().toString().length() - 1));
                input.setSelection(input.getText().length());
            }
        }else{
            if(txtEscanear.getText().toString().length() > 0){
                txtEscanear.setText(txtEscanear.getText().toString().substring(0, txtEscanear.getText().toString().length() - 1));
                txtEscanear.setSelection(txtEscanear.getText().length());
            }
        }
    }
    private void escribir(String caracter) {
        vibrator.vibrate(60);
        if(tipoControl.equals("ingresosVehicular")||tipoControl.equals("salidasVehicular")){
            if (input.getText().length() < 9) {
                input.setText(input.getText() + caracter);
                input.setSelection(input.getText().length());
            }
            if(tipoVehiculo.equals("conductor")){
                if (input.getText().length() == 8) {
                    if (validarRut(input.getText().toString()) && Integer.parseInt(input.getText().toString().substring(0,1)) > 3)
                        procesarChofer(input.getText().toString());
                } else if (input.getText().length() == 9) {
                    if (validarRut(input.getText().toString())) {
                        procesarChofer(input.getText().toString());
                    } else {
                        mostrarAlerta("RUT INVÁLIDO\n" + input.getText().toString(), "warning", "short");
                        input.setText("");
                    }
                }
            }else{
                if (input.getText().length() == 8) {
                    if (validarRut(input.getText().toString()) && Integer.parseInt(input.getText().toString().substring(0,1)) > 3)
                        procesarPasajero(input.getText().toString());
                } else if (input.getText().length() == 9) {
                    if (validarRut(input.getText().toString())) {
                        procesarPasajero(input.getText().toString());
                    } else {
                        mostrarAlerta("RUT INVÁLIDO\n" + input.getText().toString(), "warning", "short");
                        input.setText("");
                    }
                }
            }
        }
        else{
            if (txtEscanear.getText().length() < 9) {
                txtEscanear.setText(txtEscanear.getText() + caracter);
                txtEscanear.setSelection(txtEscanear.getText().length());
            }
            if (txtEscanear.getText().length() == 8) {
                if (validarRut(txtEscanear.getText().toString()) && Integer.parseInt(txtEscanear.getText().toString().substring(0,1)) > 3) procesarRut(txtEscanear.getText().toString());
            } else if (txtEscanear.getText().length() == 9) {
                if (validarRut(txtEscanear.getText().toString())) {
                    procesarRut(txtEscanear.getText().toString());
                } else {
                    mostrarAlerta("RUT INVÁLIDO\n" + txtEscanear.getText().toString(), "warning", "short");
                    txtEscanear.setText("");
                    limpiarPantalla();
                }
            }
        }
    }
    public void onclickPanelCentral(View view) {
        finish();
    }
    public void onclickCerrar(View view) {
        ocultarTecladoVirtual();
        btnPanel.setVisibility(View.VISIBLE);
        btnCerrar.setVisibility(View.VISIBLE);
        dialogoV.setVisibility(View.GONE);
    }
    public void onclickPanelCerrar(View view) {
        idGrupal = null;
        hayResultadoChofer = false;
        MainActivity.tipoPase = "";
        finish();
    }
    private void limpiarPantalla() {
        lblResultado.setText("");
        lblNombre.setText("");
        lblCargo.setText("");
    }
    private void mostrarAlerta(String mensajeTexto, String mensajeClass, String mensajeTime) {
        Intent intent = new Intent(Control.this, Alerta.class);
        intent.putExtra("mensaje", mensajeTexto);
        intent.putExtra("class", mensajeClass);
        intent.putExtra("time", mensajeTime);
        startActivity(intent);
    }
    private void formulario(){
        dialogoV.setVisibility(View.VISIBLE);
        input.setText("");
        input.requestFocus();
        switch (tipoVehiculo){
            case "vehiculo":
                    btnCamara2.setVisibility(View.GONE);
                    titulo.setText(R.string.ingresar_patente);
                    subTitulo.setText(R.string.sin_espacios);
                    subTitulo.setVisibility(View.VISIBLE);
                    input.setHint(R.string.digitar_patente);
                    button.setText("verificar");
                break;
            case "pasajeros":
            case "conductor":
                input.requestFocus();
                if(CFGhabilitar_camara.equals("1")){
                    btnCamara2.setVisibility(View.VISIBLE);
                }
                titulo.setText(R.string.escribir_rut);
                subTitulo.setVisibility(View.GONE);
                input.setHint(R.string.digitar_rut);
                button.setVisibility(View.GONE);
                btnTeclado2.setVisibility(View.VISIBLE);
                break;
        }

        Fuente: https://www.iteramos.com/pregunta/63376/evento-para-manejar-el-foco-del-edittext
        input.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            ocultarTecladoAndroid();
            if(input.getText().length()>0){
                if(actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    if(tipoVehiculo.equals("vehiculo")){
                        if(input.getText().length()<7){
                            procesarPatente(input.getText().toString());
                        }else {
                            mostrarAlerta("PATENTE INVÁLIDA", "warning", "short");
                            input.setText("");
                        }
                        return true;
                    }else if (tipoVehiculo.equals("conductor")) {
                        String rutLimpio = filtrarRut(input.getText().toString());
                        if(validarRut(rutLimpio)){
                            procesarChofer(rutLimpio);
                        }else{
                            mostrarAlerta("RUT INVÁLIDO", "warning", "short");
                            input.setText("");
                            limpiarPantalla();
                        }
                        return true;
                    }else{
                        String rutLimpio = filtrarRut(input.getText().toString());
                        if(validarRut(rutLimpio)){
                            procesarPasajero(rutLimpio);
                        }else{
                            mostrarAlerta("RUT INVÁLIDO", "warning", "short");
                            input.setText("");
                            limpiarPantalla();
                        }
                        return true;
                    }
                }
            }
            return false;
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTecladoAndroid();
                if(input.getText().length()>0){
                    if(input.getText().length()<7){
                        procesarPatente(input.getText().toString());
                    }else {
                        mostrarAlerta("PATENTE INVÁLIDA", "warning", "short");
                        input.setText("");
                    }
                }
            }
        });
    }
    /* ./INTERFAZ BOTONES*/
    public void onclickSincronizar(View view) {
        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(uploadWorkRequest);
    }

    /*PROCESO VEHICULO*/
    private void procesarPatente(String patente) {
        try {
            try {
                mP.stop();
            }catch (Exception e){ }
            try {
                mP.release();
            }catch (Exception e){ }
            try {
                mP = null;
            }catch (Exception e){ }
            ocultarTecladoVirtual();
            SQLiteDatabase db = conn.getReadableDatabase();
            String[] campos,
                    parametros;
            Cursor cursor = null, cursor2 = null,cursor3 = null;
            campos = new String[]{"marca", "year", "modelo", "id_estado", "id_empresa","ID_VEHICULO"};
            parametros = new String[]{patente.toUpperCase(), "2"};
            cursor = db.query("vehiculos", campos, "patente = ? AND id_estado = ?", parametros, null, null, null);
            String tipo_c = "";
            if(tipoControl.equals("ingresosVehicular")){
                parametros = new String[]{patente.toUpperCase(),"1"};
                tipo_c = "1";
                mP = MediaPlayer.create(getApplicationContext(), R.raw.vehiculoacreditado);
            }else{
                parametros = new String[]{patente.toUpperCase(),"2"};
                tipo_c = "2";
                mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
            }
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                //validar que no se repitan las salidas de la misma persona
                campos = new String[]{"id_grupo"};
                cursor2 = db.query("vehiculosCapturadas", campos, "patente = ? AND tipo_captura = ?", parametros, null, null, null);
                if (cursor2.getCount() > 0) {
                    cursor2.moveToFirst();
                    idGrupal = cursor2.getLong(0);
                    mostrarAlerta("El dato ya esta ingresado", "warning", "short");
                } else {
                    registrarCapturaVehiculo(ifN(cursor.getString(5), null), patente.toUpperCase(),tipo_c,ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                }
                resultadoPatente.setText(patente.toUpperCase());
                resultadoVehiculo.setText("AUTORIZADO");
                resultadoVehiculo.setTextColor(getResources().getColor(R.color.green_success));
                button.setText("AUTORIZADO");
                button.setBackground(getDrawable(R.drawable.afirmativo));
            } else {
                resultadoPatente.setText(patente.toUpperCase());
                resultadoVehiculo.setText("DENEGADO");
                resultadoVehiculo.setTextColor(getResources().getColor(R.color.red_danger));
                button.setText("DENEGADO");
                button.setBackground(getDrawable(R.drawable.negativo));
                campos = new String[]{"ID_VEHICULO","id_estado","id_empresa"};
                parametros = new String[]{patente.toUpperCase()};
                cursor = db.query("vehiculos", campos, "patente = ?", parametros, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    registrarRechazoVehiculo(ifN(cursor.getString(0), null), patente.toUpperCase(),motivoRechazo(Integer.parseInt(cursor.getString(1))),tipo_c,ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                }else{
                    registrarRechazoVehiculo(patente.toUpperCase(), patente.toUpperCase(),"No existe registro del Vehiculo",tipo_c,ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                }
                mP = MediaPlayer.create(getApplicationContext(), R.raw.vehiculonoacreditado);
            }
            btnCerrar.setVisibility(View.VISIBLE);
            btnPanel.setVisibility(View.VISIBLE);
            resultadoVehi.setVisibility(View.VISIBLE);
            dialogoV.setVisibility(View.GONE);
            mP.start();
            db.close();
            cursor.close();
        }catch (Exception e){
            Log.e(TAG," El error es "+e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void procesarChofer(String rut) {
        ocultarTecladoVirtual();
        try {
            try {
                mP.stop();
            }catch (Exception e){ }
            try {
                mP.release();
            }catch (Exception e){ }
            try {
                mP = null;
            }catch (Exception e){ }
            SQLiteDatabase db = conn.getReadableDatabase();
            String[] campos,
                    parametros;
            Cursor cursor = null, cursor2 = null;
            campos = new String[]{"NOMBRE", "APELLIDO", "LST_LICENCIA", "FECHA_VENCIMIENTO", "FECHA_RESTRICCION", "RUT", "OBSERVACION", "FAENA"};
            parametros = new String[]{rut.toLowerCase()};
            cursor = db.query("licencias", campos, "LOWER(RUT) = ?", parametros, null, null, null);
            String tipo_c = "";
            if(tipoControl.equals("ingresosVehicular")){
                tipo_c = "1";
                mP = MediaPlayer.create(getApplicationContext(), R.raw.licenciacorrecta);
            }else{
                tipo_c = "2";
                mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
            }
            if (cursor.getCount() > 0) {
                hayResultadoChofer = true;
                cursor.moveToFirst();
                if (!fecha1MayorQueFecha2(TIMESTAMPdate(), cursor.getString(3)) || cursor.getString(3).equals("0000-00-00")) { //si fecha_vencimiento licencia no ha pasado
                    //si fecha restriccion licencia ya paso o simplemente no tiene restriccion
                    if (ifN(cursor.getString(4)).equals("") || ifN(cursor.getString(4)).equals("0000-00-00") || (!fecha1MayorQueFecha2(cursor.getString(4), TIMESTAMPdate()) && !cursor.getString(4).equals(TIMESTAMPdate()))) {
                        campos = new String[]{"NOMBRE", "APELLIDO", "CARGO", "EMPRESA","id","ESTADO"};
                        parametros = new String[]{rut.toLowerCase()};
                        cursor2 = db.query("personal", campos, "LOWER(RUT) = ?", parametros, null, null, null);
                        if (cursor2.getCount()>0){
                            Cursor personal = db.rawQuery("SELECT ID_PERSONA, EMPRESA, ESTADO FROM personal WHERE RUT = ?", new String[]{cursor.getString(5)});
                            String cargo = "";
                            String idPer = "";
                            if(personal != null){
                                if(personal.moveToFirst()){
                                    if(personal.getCount() > 0){
                                        cargo = personal.getString(1);
                                        idPer = personal.getString(0);
                                    }
                                }
                            }
                            button.setText("AUTORIZADO");
                            button.setBackground(getDrawable(R.drawable.afirmativo));
                            resultadoRut.setText(rut);
                            resultadoNombre.setText(cursor.getString(0) + ((cursor.getString(1) != null)?" " + cursor.getString(1):"") );
                            resultadoEmpresa.setText(cargo);
                            resultadoChofer.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.green_success));
                            resultadoChofer.setText("AUTORIZADO");
                            resultadoCho.setVisibility(View.VISIBLE);
                            dialogoV.setVisibility(View.GONE);
                            registrarCapturaPasajeros(idGrupal,ifN(rut, null), idPer, isNumeric(personal.getString(2)) ? motivoRechazo(Integer.parseInt(personal.getString(2))) : personal.getString(2), "1", tipo_c, ifN(TIMESTAMP(), null));
                        }else{
                            rechazoChofer(ifN(rut, null),tipo_c,"No se encuentra en Sistema");
                        }
                    }
                    else {
                        rechazoChofer(ifN(rut, null),tipo_c,"TIENE UNA RESTRICCION VIGENTE");
                    }
                } else {
                    rechazoChofer(ifN(rut, null),tipo_c,"LICENCIA VENCIDA");
                }
            } else {
                rechazoChofer(ifN(rut, null),tipo_c,"No registra Licencia");
            }
            mP.start();
            db.close();
            cursor.close();
            if(cursor2!=null){
                cursor2.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG," El error es "+e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void rechazoChofer(String rut, String tipo, String motivo){
        button.setText("DENEGADO");
        button.setBackground(getDrawable(R.drawable.negativo));
        resultadoRut.setText(rut);
        resultadoChofer.setText("DENEGADO");
        resultadoChofer.setTextColor(getResources().getColor(R.color.red_danger));
        resultadoCho.setVisibility(View.VISIBLE);
        dialogoV.setVisibility(View.GONE);
        registrarCapturaPasajeros(idGrupal,rut,rut,motivo, "1", tipo, ifN(TIMESTAMP(), null));
        if (tipo == "1") {
            mP = MediaPlayer.create(getApplicationContext(), R.raw.licenciarechazo);
        }else{
            mP = MediaPlayer.create(getApplicationContext(), R.raw.registrandosalida);
        }

    }
    private void procesarPasajero(String rut) {
        try {
            try {
                mP.stop();
            }catch (Exception e){ }
            try {
                mP.release();
            }catch (Exception e){ }
            try {
                mP = null;
            }catch (Exception e){ }
            ocultarTecladoVirtual();
            txtEscanear.setText("");

            SQLiteDatabase db = conn.getReadableDatabase();
            String[] campos,
                    parametros;
            Cursor cursor = null, cursor2 = null,cursor3 = null;
            campos = new String[]{"NOMBRE", "APELLIDO", "CARGO", "EMPRESA","id","ESTADO"};
            parametros = new String[]{rut.toLowerCase()};
            cursor = db.query("personal", campos, "LOWER(RUT) = ?", parametros, null, null, null);
            if (cursor.getCount() > 0) {
                button.setText("AUTORIZADO");
                button.setBackground(getDrawable(R.drawable.afirmativo));
                cursor.moveToFirst();
                //validar que no se repitan los ingresos de la misma persona
                campos = new String[]{"RUT"};
                if(tipoControl.equals("ingresosVehicular")){
                    parametros = new String[]{rut.toLowerCase(),"1"};
                    mP = MediaPlayer.create(getApplicationContext(), R.raw.accesopermitido);
                }else{
                    parametros = new String[]{rut.toLowerCase(),"2"};
                    mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
                }
                cursor2 = db.query("personalCapturado", campos, "LOWER(RUT) = ? AND TIPO_CAPTURA = ?", parametros, null, null, null);
                if (cursor2.getCount() > 0) {
                    cursor2.moveToFirst();
                    if(rut1.getText().length()==0){
                        rut1.setText(rut);
                        nombre1.setText(cursor.getString(0) + " " + cursor.getString(1));
                        respuesta1.setText("AUTORIZADO");
                        respuesta1.setTextColor(getResources().getColor(R.color.green_success));
                    }else if (rut2.getText().length()==0){
                        rut2.setText(rut);
                        nombre2.setText(cursor.getString(0) + " " + cursor.getString(1));
                        respuesta2.setText("AUTORIZADO");
                        respuesta2.setTextColor(getResources().getColor(R.color.green_success));
                    }else if (rut3.getText().length()==0){
                        rut3.setText(rut);
                        nombre3.setText(cursor.getString(0) + " " + cursor.getString(1));
                        respuesta3.setText("AUTORIZADO");
                        respuesta3.setTextColor(getResources().getColor(R.color.green_success));
                    }
                    mostrarAlerta("El dato ya está ingresado", "warning", "short");
                } else {
                    if(rut1.getText().length()==0){
                        rut1.setText(rut);
                        nombre1.setText(cursor.getString(0) + " " + cursor.getString(1));
                        respuesta1.setText("AUTORIZADO");
                        respuesta1.setTextColor(getResources().getColor(R.color.green_success));
                    }else if (rut2.getText().length()==0){
                        rut2.setText(rut);
                        nombre2.setText(cursor.getString(0) + " " + cursor.getString(1));
                        respuesta2.setText("AUTORIZADO");
                        respuesta2.setTextColor(getResources().getColor(R.color.green_success));
                    }else if (rut3.getText().length()==0){
                        rut3.setText(rut);
                        nombre3.setText(cursor.getString(0) + " " + cursor.getString(1));
                        respuesta3.setText("AUTORIZADO");
                        respuesta3.setTextColor(getResources().getColor(R.color.green_success));
                    }
                    registrarCapturaPasajeros(idGrupal,ifN(rut, null),cursor.getString(4),isNumeric(cursor.getString(5)) ? motivoRechazo(Integer.parseInt(cursor.getString(5))) : cursor.getString(5), "2", "1", ifN(TIMESTAMP(), null));
                }
            } else {
                button.setText("DENEGADO");
                button.setBackground(getDrawable(R.drawable.negativo));
                if(rut1.getText().length()==0){
                    rut1.setText(rut);
                    nombre1.setText("Sin nombre");
                    respuesta1.setText("DENEGADO");
                    respuesta1.setTextColor(getResources().getColor(R.color.red_danger));
                }else if (rut2.getText().length()==0){
                    rut2.setText(rut);
                    nombre2.setText("Sin nombre");
                    respuesta2.setText("DENEGADO");
                    respuesta2.setTextColor(getResources().getColor(R.color.red_danger));
                }else if (rut3.getText().length()==0){
                    rut3.setText(rut);
                    nombre3.setText("Sin nombre");
                    respuesta3.setText("DENEGADO");
                    respuesta3.setTextColor(getResources().getColor(R.color.red_danger));
                }
                registrarCapturaPasajeros(idGrupal,ifN(rut, null),ifN(rut, null),"No se encuentra en Sistema", "2", "1", ifN(TIMESTAMP(), null));
                mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
            }
            constraintTable.setVisibility(View.VISIBLE);
            input.setText("");
            mP.start();
            db.close();
            cursor.close();
        }catch (Exception e){
            Log.e(TAG," El error es "+e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*PROCESO RUT*/
    private boolean validarRut(String rut){
        boolean resultado = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
            char dv = rut.charAt(rut.length() - 1);
            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            if (dv == (char) (s != 0 ? s + 47 : 75)) resultado = true;
        } catch (Exception e) {
        }
        return resultado;
    }
    private String filtrarRut(String rut){
        String soloRut = rut;

        //filtrar qr cédula de identidad nueva chilena
        Log.i(TAG,"el resultado de index.of "+rut.toLowerCase().indexOf("https://portal.sidiv"));
        if (rut.toLowerCase().indexOf("https://portal.sidiv") == 0) soloRut = rut.substring(52, 62);
        //5555555566666666667
        //2345678901234567890
        //18540458-7
        soloRut = soloRut.toUpperCase().trim();
        soloRut = soloRut.replace(".", "");
        soloRut = soloRut.replace("-", "");

        return soloRut;
    }
    private void procesarRut(String rut) {
        Log.i(TAG,"el rut es "+rut);
        try {
            try {
                mP.stop();
            }catch (Exception e){ }
            try {
                mP.release();
            }catch (Exception e){ }
            try {
                mP = null;
            }catch (Exception e){ }
            ocultarTecladoVirtual();
            txtEscanear.setText("");

            SQLiteDatabase db = conn.getReadableDatabase();
            String[] campos,
                    parametros;
            Cursor cursor = null, cursor2 = null;
            switch (tipoControl) {
                case "ingresosvisitas":
                    campos = new String[]{"RUT_EMPRESA","NOMBRE_EMPRESA","FECHA_PASE_INICIO", "FECHA_PASE_TERMINO", "RUT_PASE", "NOMBRE_PASE","ID_REGISTRO_VISITA"};
                    parametros = new String[]{rut.toLowerCase()};
                    cursor = db.query("visitaAdminis", campos, "RUT_PASE = ? AND FECHA_PASE_TERMINO >= CURRENT_DATE", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        lblResultado.setText("PERMITIDO");
                        lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                        lblNombre.setText(cursor.getString(5));
                        lblCargo.setText(cursor.getString(1));
                        registrarCapturaVisitaTec(cursor.getInt(6),1,ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesopermitido);
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoVisita(0,2,"1","Pase sin vigencia",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                    }
                    mP.start();
                    break;
                case "ingresostecnica":
                    campos = new String[]{"RUT_EMPRESA","NOMBRE_EMPRESA","FECHA_PASE_INICIO", "FECHA_PASE_TERMINO", "RUT_PASE", "NOMBRE_PASE","ID_REGISTRO_VISITA"};
                    parametros = new String[]{rut.toLowerCase()};
                    cursor = db.query("visitaTecnica", campos, "RUT_PASE = ? AND FECHA_PASE_TERMINO >= CURRENT_DATE", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        lblResultado.setText("PERMITIDO");
                        lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                        lblNombre.setText(cursor.getString(5));
                        lblCargo.setText(cursor.getString(1));
                        registrarCapturaVisitaTec(cursor.getInt(6),1,ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesopermitido);
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoVisita(0,1,"1","Pase sin vigencia",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                    }
                    mP.start();
                    break;
                case "salidasvisitas":
                    campos = new String[]{"RUT_EMPRESA","NOMBRE_EMPRESA","FECHA_PASE_INICIO", "FECHA_PASE_TERMINO", "RUT_PASE", "NOMBRE_PASE","ID_REGISTRO_VISITA"};
                    parametros = new String[]{rut.toLowerCase()};
                    cursor = db.query("visitaAdminis", campos, "RUT_PASE = ? AND FECHA_PASE_TERMINO >= CURRENT_DATE", parametros, null, null, null);
                    cursor2 = db.query("visitaAdminis", campos, "RUT_PASE = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        lblResultado.setText("PERMITIDO");
                        lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                        lblNombre.setText(cursor.getString(5));
                        lblCargo.setText(cursor.getString(1));
                        registrarCapturaVisitaTec(cursor.getInt(6),2,ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoVisita(0,2,"2","Pase sin vigencia",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                    }
                    mP.start();
                    break;
                case "salidastecnica":
                    campos = new String[]{"RUT_EMPRESA","NOMBRE_EMPRESA","FECHA_PASE_INICIO", "FECHA_PASE_TERMINO", "RUT_PASE", "NOMBRE_PASE","ID_REGISTRO_VISITA"};
                    parametros = new String[]{rut.toLowerCase()};
                    cursor = db.query("visitaTecnica", campos, "RUT_PASE = ? AND FECHA_PASE_TERMINO >= CURRENT_DATE", parametros, null, null, null);
                    cursor2 = db.query("visitaTecnica", campos, "RUT_PASE = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        lblResultado.setText("PERMITIDO");
                        lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                        lblNombre.setText(cursor.getString(5));
                        lblCargo.setText(cursor.getString(1));
                        registrarCapturaVisitaTec(cursor.getInt(6),2,ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoVisita(0,1,"2","Pase sin vigencia",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                    }
                    mP.start();
                    break;
                case "ingresos":
                    campos = new String[]{"NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "ID_PERSONA"};
                    parametros = new String[]{rut.toLowerCase(),"2"};
                    Log.i(TAG, Arrays.toString(parametros));
                    cursor = db.query("personal", campos, "LOWER(RUT) = ? AND ESTADO = ? AND TERMINO_CONTRATO >= CURRENT_DATE", parametros, null, null, null);
                    Log.i(TAG,cursor.toString());
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        //validar que no se repitan los ingresos de la misma persona
                        campos = new String[]{"RUT"};
                        parametros = new String[]{rut.toLowerCase(), "1"};
                        cursor2 = db.query("personalCapturado", campos, "LOWER(RUT) = ? AND TIPO_CAPTURA = ?", parametros, null, null, null);
                        if (cursor2.getCount() > 0) {
                            cursor2.moveToFirst();
                            lblResultado.setText("PERMITIDO");
                            lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                            lblNombre.setText(cursor.getString(0) + " " + cursor.getString(1));
                            lblCargo.setText(cursor.getString(3));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.yaregistraingreso);
                            mostrarAlerta("El dato ya está ingresado", "warning", "short");
                        } else {
                            lblResultado.setText("PERMITIDO");
                            lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                            lblNombre.setText(cursor.getString(0) + " " + cursor.getString(1));
                            lblCargo.setText(cursor.getString(3));
                            registrarCapturaPersonal(rut, ifN(cursor.getString(4), null), ifN(cursor.getString(0), null), ifN(cursor.getString(1), null), ifN(cursor.getString(2), null), ifN(cursor.getString(3), null), ifN("1", null), ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.accesopermitido);
                        }
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        campos = new String[]{"NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "ESTADO"};
                        parametros = new String[]{rut.toLowerCase()};
                        cursor = db.query("personal", campos, "LOWER(RUT) = ?", parametros, null, null, null);
                        if (cursor.getCount() > 0) {
                            cursor.moveToLast();
                            lblNombre.setText(cursor.getString(0) + " " + cursor.getString(1));
                            lblCargo.setText(cursor.getString(3));
                            registrarRechazoPersonal(rut, cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),motivoRechazo(Integer.parseInt(cursor.getString(4))), ifN("1", null), ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                        }else{
                            registrarRechazoPersonal(rut, "No registrado", "No registrado", "No registrado", "No registrado","No se encuentra en sistema", ifN("1", null), ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                        }
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                    }
                    mP.start();
                    break;
                case "salidas":
                    campos = new String[]{"NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "ID_PERSONA"};
                    parametros = new String[]{rut.toLowerCase(),"2"};
                    cursor = db.query("personal", campos, "LOWER(RUT) = ? AND ESTADO = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        //validar que no se repitan las salidas de la misma persona
                        campos = new String[]{"RUT"};
                        parametros = new String[]{rut.toLowerCase(), "2"};
                        cursor2 = db.query("personalCapturado", campos, "LOWER(RUT) = ? AND TIPO_CAPTURA = ?", parametros, null, null, null);
                        if (cursor2.getCount() > 0) {
                            cursor2.moveToFirst();
                            lblResultado.setText("PERMITIDO");
                            lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                            lblNombre.setText(cursor.getString(0) + " " + cursor.getString(1));
                            lblCargo.setText(cursor.getString(3));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
                            mostrarAlerta("El dato ya esta ingresado", "warning", "short");
                        } else {
                            lblResultado.setText("PERMITIDO");
                            lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                            lblNombre.setText(cursor.getString(0) + " " + cursor.getString(1));
                            lblCargo.setText(cursor.getString(3));
                            registrarCapturaPersonal(rut, ifN(cursor.getString(4), null), ifN(cursor.getString(0), null), ifN(cursor.getString(1), null), ifN(cursor.getString(2), null), ifN(cursor.getString(3), null), ifN("2", null), ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
                        }
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        campos = new String[]{"NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "ESTADO"};
                        parametros = new String[]{rut.toLowerCase()};
                        cursor = db.query("personal", campos, "LOWER(RUT) = ?", parametros, null, null, null);
                        if (cursor.getCount() > 0) {
                            cursor.moveToLast();
                            lblNombre.setText(cursor.getString(0) + " " + cursor.getString(1));
                            lblCargo.setText(cursor.getString(3));
                            registrarRechazoPersonal(rut, cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),motivoRechazo(Integer.parseInt(cursor.getString(4))), ifN("2", null), ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                        }
                        else{
                            registrarRechazoPersonal(rut, "No registrado", "No registrado", "No registrado", "No registrado","No se encuentra en sistema", ifN("1", null), ifN(CFGid_app, null), ifN(TIMESTAMP(), null));
                        }
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                    }
                    mP.start();
                    break;
                case "ingresosgrupal":
                    campos = new String[]{"ID_PASE", "ID_LISTADO", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "PATENTE", "FECHA_PASE_INICIO", "ESTADO"};
                    parametros = new String[]{rut.toLowerCase(), "1"};
                    cursor = db.query("especiales", campos, "LOWER(RUT) = ? AND ID_TIPO_PASE = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(8).equals("2")) {
                            if (TIMESTAMPdate().equals(cursor.getString(7))){
                                lblResultado.setText("PERMITIDO");
                                lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("CARGO: " + cursor.getString(4));
                                registrarCapturaGrupal(ifN(cursor.getString(0), null), ifN(cursor.getString(1), null), ifN(rut, null), ifN(cursor.getString(2), null),ifN(cursor.getString(3), null),ifN(cursor.getString(4), null),ifN(cursor.getString(5), null),ifN(cursor.getString(6), null),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.accesopermitido);
                            }else{
                                lblResultado.setText("DENEGADO");
                                lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("CARGO: " + cursor.getString(4));
                                registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                            }
                        } else if (cursor.getString(8).equals("4")) {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("CARGO: " + cursor.getString(4));
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        } else {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("CARGO: " + cursor.getString(4));
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        }
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoGrupal(0,0,"1","No existe registro en Sistema",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                    }
                    mP.start();
                    break;
                case "ingresostransportista":
                    campos = new String[]{"ID_PASE", "ID_LISTADO", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "PATENTE", "FECHA_PASE_INICIO", "ESTADO"};
                    parametros = new String[]{rut.toLowerCase(), "4"};
                    cursor = db.query("especiales", campos, "LOWER(RUT) = ? AND ID_TIPO_PASE = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(8).equals("2")) {
                            if (TIMESTAMPdate().equals(cursor.getString(7))){
                                lblResultado.setText("PERMITIDO");
                                lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("PATENTE: " + cursor.getString(6));
                                registrarCapturaTransportista(ifN(cursor.getString(0), null), ifN(cursor.getString(1), null), ifN(rut, null), ifN(cursor.getString(2), null),ifN(cursor.getString(3), null),ifN(cursor.getString(4), null),ifN(cursor.getString(5), null),ifN(cursor.getString(6), null),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.accesopermitido);
                            }else{
                                lblResultado.setText("DENEGADO");
                                lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("PATENTE: " + cursor.getString(6));
                                registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                            }
                        } else if (cursor.getString(8).equals("4")) {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("PATENTE: " + cursor.getString(6));
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        } else {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("PATENTE: " + cursor.getString(6));
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        }
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoGrupal(0,0,"1","No existe registro en Sistema",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                    }
                    mP.start();
                    break;
                case "salidasgrupal":
                    campos = new String[]{"ID_PASE", "ID_LISTADO", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "PATENTE", "FECHA_PASE_INICIO", "ESTADO"};
                    parametros = new String[]{rut.toLowerCase(), "1"};
                    cursor = db.query("especiales", campos, "LOWER(RUT) = ? AND ID_TIPO_PASE = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(8).equals("2")) {
                            if (TIMESTAMPdate().equals(cursor.getString(7))){
                                lblResultado.setText("PERMITIDO");
                                lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("CARGO: " + cursor.getString(4));
                                registrarCapturaGrupal(ifN(cursor.getString(0), null), ifN(cursor.getString(1), null), ifN(rut, null), ifN(cursor.getString(2), null),ifN(cursor.getString(3), null),ifN(cursor.getString(4), null),ifN(cursor.getString(5), null),ifN(cursor.getString(6), null),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
                            }else{
                                lblResultado.setText("DENEGADO");
                                lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("CARGO: " + cursor.getString(4));
                                registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                            }
                        } else if (cursor.getString(8).equals("4")) {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("CARGO: " + cursor.getString(4));
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        } else {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("CARGO: " + cursor.getString(4));
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        }
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoGrupal(0,0,"1","No existe registro en Sistema",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                    }
                    mP.start();
                    break;
                case "salidastransportista":
                    campos = new String[]{"ID_PASE", "ID_LISTADO", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "PATENTE", "FECHA_PASE_INICIO", "ESTADO"};
                    parametros = new String[]{rut.toLowerCase(), "4"};
                    cursor = db.query("especiales", campos, "LOWER(RUT) = ? AND ID_TIPO_PASE = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(8).equals("2")) {
                            if (TIMESTAMPdate().equals(cursor.getString(7))){
                                lblResultado.setText("PERMITIDO");
                                lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("PATENTE: " + cursor.getString(6));
                                registrarCapturaTransportista(ifN(cursor.getString(0), null), ifN(cursor.getString(1), null), ifN(rut, null), ifN(cursor.getString(2), null),ifN(cursor.getString(3), null),ifN(cursor.getString(4), null),ifN(cursor.getString(5), null),ifN(cursor.getString(6), null),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.seharegistradosalida);
                            }else{
                                lblResultado.setText("DENEGADO");
                                lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("PATENTE: " + cursor.getString(6));
                                registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                            }
                        } else if (cursor.getString(8).equals("4")) {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("PATENTE: " + cursor.getString(6));
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        } else {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("PATENTE: " + cursor.getString(6));
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        }
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoGrupal(0,0,"1","No existe registro en Sistema",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                    }
                    mP.start();
                    break;
                case "emergencia":
                    campos = new String[]{"ID_PASE", "ID_LISTADO", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "PATENTE", "FECHA_PASE_INICIO", "ESTADO"};
                    parametros = new String[]{rut.toLowerCase(), "5"};
                    cursor = db.query("especiales", campos, "LOWER(RUT) = ? AND ID_TIPO_PASE = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        if (cursor.getString(8).equals("2")) {
                            if (TIMESTAMPdate().equals(cursor.getString(7))){
                                lblResultado.setText("PERMITIDO");
                                lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("");
                                registrarCapturaEmergencia(ifN(cursor.getString(0), null), ifN(cursor.getString(1), null), ifN(rut, null), ifN(cursor.getString(2), null),ifN(cursor.getString(3), null),ifN(cursor.getString(4), null),ifN(cursor.getString(5), null),ifN(cursor.getString(6), null),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.accesopermitido);
                            }else{
                                lblResultado.setText("DENEGADO");
                                lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                                lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                                lblCargo.setText("");
                                registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                            }
                        } else if (cursor.getString(8).equals("4")) {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("");
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        } else {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(2)); //lblNombre.setText(cursor.getString(2) + " " + cursor.getString(3));
                            lblCargo.setText("");
                            registrarRechazoGrupal(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),"1",motivoRechazo(Integer.parseInt(cursor.getString(8))),ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                        }
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoGrupal(0,0,"1","No existe registro en Sistema",ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.pasenoautorizado);
                    }
                    mP.start();
                    break;
                case "licencias":
                    campos = new String[]{"NOMBRE", "APELLIDO", "LST_LICENCIA", "FECHA_VENCIMIENTO", "FECHA_RESTRICCION", "RUT", "OBSERVACION", "FAENA"};
                    parametros = new String[]{rut.toLowerCase()};
                    cursor = db.query("licencias", campos, "LOWER(RUT) = ?", parametros, null, null, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        if (!fecha1MayorQueFecha2(TIMESTAMPdate(), cursor.getString(3)) || cursor.getString(3).equals("0000-00-00")) { //si fecha_vencimiento licencia no ha pasado
                            //si fecha restriccion licencia ya paso o simplemente no tiene restriccion
                            if (ifN(cursor.getString(4)).equals("") || ifN(cursor.getString(4)).equals("0000-00-00") || (!fecha1MayorQueFecha2(cursor.getString(4), TIMESTAMPdate()) && !cursor.getString(4).equals(TIMESTAMPdate()))) {
                                Cursor personal = db.rawQuery("SELECT EMPRESA FROM personal WHERE RUT = ?", new String[]{cursor.getString(5)});
                                String cargo = "";
                                if(personal != null){
                                    if(personal.moveToFirst()){
                                        if(personal.getCount() > 0){
                                            cargo = personal.getString(0);
                                        }
                                    }
                                }
                                if(cursor.getString(7) != null) {
                                    if (!cursor.getString(7).equals("")) {
                                        cargo += ((cargo.isEmpty()) ? "" : "\n") + "Faenas Autorizadas: " + cursor.getString(7);
                                    }
                                }
                                if(cursor.getString(2) != null) {
                                    if (!cursor.getString(2).equals("")) {
                                        String[] licencias = cursor.getString(2).split(",");
                                        String tiposLicencias = "";
                                        for (int i = 0; i < licencias.length; i++) {
                                            String traduccion = "";
                                            traduccion = formatoLicencia(licencias[i].trim().toLowerCase());
                                            tiposLicencias += ((tiposLicencias.isEmpty()) ? "" : ", ") + traduccion;
                                        }
                                        cargo += ((cargo.isEmpty()) ? "" : "\n") + "Licencias Autorizadas: " + tiposLicencias;
                                    }
                                }
                                if(cursor.getString(6) != null) {
                                    if (!cursor.getString(6).equals("")) {
                                        cargo += ((cargo.isEmpty()) ? "" : "\n") + "Observación: " + cursor.getString(6);
                                    }
                                }
                                lblResultado.setText("PERMITIDO");
                                lblResultado.setTextColor(getResources().getColor(R.color.green_success));
                                lblNombre.setText(cursor.getString(0) + ((cursor.getString(1) != null)?" " + cursor.getString(1):"") );
                                lblCargo.setText(cargo);
                                registrarCapturaLicencia(ifN(rut, null), ifN(cursor.getString(0), null),
                                        ifN(cursor.getString(1), null), ifN("1", null),
                                        ifN(TIMESTAMP(), null),ifN(CFGid_app, null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.accesopermitido);
                            } else {
                                lblResultado.setText("DENEGADO");
                                lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                                lblNombre.setText(cursor.getString(0) + ((cursor.getString(1) != null)?" " + cursor.getString(1):"") );
                                lblCargo.setText("PRESENTA RESTRICCIÓN\n" + cursor.getString(4));
                                registrarRechazoLicencia(ifN(rut, null), ifN(cursor.getString(2), null),
                                        "TIENE UNA RESTRICCION VIGENTE", ifN("1", null),
                                        ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                                mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                            }
                        } else {
                            lblResultado.setText("DENEGADO");
                            lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                            lblNombre.setText(cursor.getString(0) + ((cursor.getString(1) != null)?" " + cursor.getString(1):"") );
                            lblCargo.setText("LICENCIA VENCIDA\n" + date2spa(cursor.getString(3)));
                            registrarRechazoLicencia(ifN(rut, null), ifN(cursor.getString(2), null),
                                    "LICENCIA VENCIDA", ifN("1", null),
                                    ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                            mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                        }
                    } else {
                        lblResultado.setText("DENEGADO");
                        lblResultado.setTextColor(getResources().getColor(R.color.red_danger));
                        lblNombre.setText(rut);
                        lblCargo.setText("");
                        registrarRechazoLicencia(ifN(rut, null), "0",
                                "Sin Licencia", ifN("1", null),
                                ifN(CFGid_app, null),ifN(TIMESTAMP(), null));
                        mP = MediaPlayer.create(getApplicationContext(), R.raw.accesodenegado);
                    }
                    mP.start();
                    break;
            }
            db.close();
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void registrarCapturaPersonal(String RUT, String ID_PERSONA, String NOMBRE, String APELLIDO, String CARGO, String EMPRESA, String TIPO_CAPTURA, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
            "INSERT INTO personalCapturado(RUT, ID_PERSONA, NOMBRE, APELLIDO, CARGO, EMPRESA, TIPO_CAPTURA, NOMBRE_DISPOSITIVO, FECHA_CAPTURA) " +
                    " VALUES(" + ifN(RUT, "NULL", VRCHR) +
                    ", " + ifN(ID_PERSONA,"NULL", VRCHR) +
                    ", " + ifN(NOMBRE,"NULL", VRCHR) +
                    ", " + ifN(APELLIDO,"NULL",VRCHR) +
                    ", " + ifN(CARGO,"NULL", VRCHR) +
                    ", " + ifN(EMPRESA,"NULL", VRCHR) +
                    ", " + ifN(TIPO_CAPTURA,"NULL", VRCHR) +
                    ", " + ifN(NOMBRE_DISPOSITIVO,"NULL", VRCHR) +
                    ", " + ifN(FECHA_CAPTURA,"NULL", VRCHR) + ")"
        );
        db.close();
    }
    private void registrarRechazoPersonal(String RUT, String NOMBRE, String APELLIDO, String CARGO, String EMPRESA, String MOTIVO_RECHAZO, String TIPO_CAPTURA, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
                "INSERT INTO personalRechazado(RUT, NOMBRE, APELLIDO, CARGO, EMPRESA, MOTIVO_RECHAZO, TIPO_CAPTURA, NOMBRE_DISPOSITIVO, FECHA_CAPTURA) " +
                        "               VALUES(" + ifN(RUT, "NULL", VRCHR) +
                        ", " + ifN(NOMBRE,"NULL", VRCHR) +
                        ", " + ifN(APELLIDO,"NULL",VRCHR) +
                        ", " + ifN(CARGO,"NULL", VRCHR) +
                        ", " + ifN(EMPRESA,"NULL", VRCHR) +
                        ", " + ifN(MOTIVO_RECHAZO,"NULL", VRCHR) +
                        ", " + ifN(TIPO_CAPTURA,"NULL", VRCHR) +
                        ", " + ifN(NOMBRE_DISPOSITIVO,"NULL", VRCHR) +
                        ", " + ifN(FECHA_CAPTURA,"NULL", VRCHR) +
                        ")"
        );
        db.close();
    }
    private void registrarCapturaVehiculo(String id_vehiculo, String patente, String tipo_captura, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_vehiculo", id_vehiculo);
        values.put("patente", patente);
        values.put("tipo_captura", tipo_captura);
        values.put("nombre_dispositivo", NOMBRE_DISPOSITIVO);
        values.put("fecha", FECHA_CAPTURA);
        idGrupal = db.insert("vehiculosCapturadas",null,values);
        db.close();
    }
    private void registrarRechazoVehiculo(String id_vehiculo, String patente, String motivo_rechazo, String tipo_captura, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_vehiculo", id_vehiculo);
        values.put("patente", patente);
        values.put("MOTIVO_RECHAZO", motivo_rechazo);
        values.put("tipo_captura", tipo_captura);
        values.put("nombre_dispositivo", NOMBRE_DISPOSITIVO);
        values.put("FECHA_CAPTURA", FECHA_CAPTURA);
        idGrupalRechaza = db.insert("vehiculosRechazado",null,values);
        db.close();
    }
    private void registrarCapturaPasajeros(Long id_grupo, String rut, String id_persona, String estado_acre, String tipo, String tipo_captura, String fecha) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
                "INSERT INTO pasajerosCapturadas(id_grupo, rut, id_persona, estado_acre, tipo, tipo_captura, fecha) " +
                        "                       VALUES(" + id_grupo + ", " +
                        ifN(rut,"NULL", VRCHR) + ", " +
                        ifN(id_persona,"NULL", VRCHR) + ", " +
                        ifN(estado_acre,"NULL", VRCHR) + ", " +
                        ifN(tipo,"NULL", VRCHR) + ", " +
                        ifN(tipo_captura,"NULL", VRCHR) + ", " +
                        ifN(fecha,"NULL", VRCHR) +
                        ")"
        );
        db.close();
    }
    private void registrarCapturaVisitaTec(int ID_REGISTRO_VISITA, int TIPO_REGISTRO, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
                "INSERT INTO visitaTecnicaCap(ID_REGISTRO_VISITA                       , TIPO_REGISTRO      , NOMBRE_DISPOSITIVO                                , FECHA_CAPTURA                            ) " +
                        "                          VALUES(" + ID_REGISTRO_VISITA + ", " + TIPO_REGISTRO + ", " + ifN(NOMBRE_DISPOSITIVO,"NULL",VRCHR) + ", " + ifN(FECHA_CAPTURA,"NULL", VRCHR) + ")"
        );
        db.close();
    }
    private void registrarRechazoVisita(int ID_PASE, int ID_TIPO_PASE, String ID_TIPO_ACCESO, String MOTIVO_RECHAZO, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
                "INSERT INTO paseVisitaRechazado(ID_PASE, " +
                        "ID_TIPO_PASE, " +
                        "ID_TIPO_ACCESO, " +
                        "MOTIVO_RECHAZO, " +
                        "nombre_dispositivo, " +
                        "FECHA_CAPTURA) " +
                        "VALUES(" + ID_PASE + ", " +
                                    ID_TIPO_PASE + ", " +
                                    ID_TIPO_ACCESO + ", " +
                                    ifN(MOTIVO_RECHAZO,"NULL",VRCHR) + ", " +
                                    ifN(NOMBRE_DISPOSITIVO,"NULL",VRCHR) + ", " +
                                    ifN(FECHA_CAPTURA,"NULL", VRCHR) +
                        ")"
        );
        db.close();
    }
    private void registrarCapturaGrupal(String ID_PASE, String ID_LISTADO, String RUT, String NOMBRE, String APELLIDO, String CARGO, String EMPRESA, String PATENTE, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
                "INSERT INTO especialesCapturados(ID_TIPO_PASE                               , ID_PASE                                  , ID_LISTADO                                , RUT                                  , NOMBRE                                 , APELLIDO                                  , CARGO                                 , EMPRESA                                 , PATENTE                                  , NOMBRE_DISPOSITIVO                                 , FECHA_CAPTURA                                  ) " +
                        "                          VALUES(" + ifN("1", "NULL", VRCHR) + ", " + ifN(ID_PASE,"NULL", VRCHR) + ", " + ifN(ID_LISTADO,"NULL",VRCHR) + ", " + ifN(RUT,"NULL", VRCHR) + ", " + ifN(NOMBRE,"NULL", VRCHR) + ", " + ifN(APELLIDO,"NULL", VRCHR) + ", " + ifN(CARGO,"NULL", VRCHR) + ", " + ifN(EMPRESA,"NULL", VRCHR) + ", " + ifN(PATENTE,"NULL", VRCHR) + ", " + ifN(NOMBRE_DISPOSITIVO,"NULL", VRCHR) + ", " + ifN(FECHA_CAPTURA,"NULL", VRCHR) + ")"
        );
        db.close();
    }
    private void registrarRechazoGrupal(int ID_PASE, int ID_TIPO_PASE, String ID_TIPO_ACCESO, String MOTIVO_RECHAZO, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
                "INSERT INTO especialesRechazado(ID_PASE, " +
                        "ID_TIPO_PASE, " +
                        "tipo_captura, " +
                        "MOTIVO_RECHAZO, " +
                        "nombre_dispositivo, " +
                        "FECHA_CAPTURA) " +
                        "VALUES(" + ID_PASE + ", " +
                        ID_TIPO_PASE + ", " +
                        ID_TIPO_ACCESO + ", " +
                        ifN(MOTIVO_RECHAZO,"NULL",VRCHR) + ", " +
                        ifN(NOMBRE_DISPOSITIVO,"NULL",VRCHR) + ", " +
                        ifN(FECHA_CAPTURA,"NULL", VRCHR) +
                        ")"
        );
        db.close();
    }
    private void registrarCapturaTransportista(String ID_PASE, String ID_LISTADO, String RUT, String NOMBRE, String APELLIDO, String CARGO, String EMPRESA, String PATENTE, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
            "INSERT INTO especialesCapturados(ID_TIPO_PASE                               , ID_PASE                                  , ID_LISTADO                                , RUT                                  , NOMBRE                                 , APELLIDO                                  , CARGO                                 , EMPRESA                                 , PATENTE                                  , NOMBRE_DISPOSITIVO                                 , FECHA_CAPTURA                                  ) " +
            "                          VALUES(" + ifN("4", "NULL", VRCHR) + ", " + ifN(ID_PASE,"NULL", VRCHR) + ", " + ifN(ID_LISTADO,"NULL",VRCHR) + ", " + ifN(RUT,"NULL", VRCHR) + ", " + ifN(NOMBRE,"NULL", VRCHR) + ", " + ifN(APELLIDO,"NULL", VRCHR) + ", " + ifN(CARGO,"NULL", VRCHR) + ", " + ifN(EMPRESA,"NULL", VRCHR) + ", " + ifN(PATENTE,"NULL", VRCHR) + ", " + ifN(NOMBRE_DISPOSITIVO,"NULL", VRCHR) + ", " + ifN(FECHA_CAPTURA,"NULL", VRCHR) + ")"
        );
        db.close();
    }
    private void registrarCapturaEmergencia(String ID_PASE, String ID_LISTADO, String RUT, String NOMBRE, String APELLIDO, String CARGO, String EMPRESA, String PATENTE, String NOMBRE_DISPOSITIVO, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
            "INSERT INTO especialesCapturados(ID_TIPO_PASE                               , ID_PASE                                  , ID_LISTADO                                , RUT                                  , NOMBRE                                 , APELLIDO                                  , CARGO                                 , EMPRESA                                 , PATENTE                                  , NOMBRE_DISPOSITIVO                                 , FECHA_CAPTURA                                  ) " +
            "                          VALUES(" + ifN("5", "NULL", VRCHR) + ", " + ifN(ID_PASE,"NULL", VRCHR) + ", " + ifN(ID_LISTADO,"NULL",VRCHR) + ", " + ifN(RUT,"NULL", VRCHR) + ", " + ifN(NOMBRE,"NULL", VRCHR) + ", " + ifN(APELLIDO,"NULL", VRCHR) + ", " + ifN(CARGO,"NULL", VRCHR) + ", " + ifN(EMPRESA,"NULL", VRCHR) + ", " + ifN(PATENTE,"NULL", VRCHR) + ", " + ifN(NOMBRE_DISPOSITIVO,"NULL", VRCHR) + ", " + ifN(FECHA_CAPTURA,"NULL", VRCHR) + ")"
        );
        db.close();
    }
    private void registrarCapturaLicencia(String RUT, String NOMBRE, String APELLIDO, String ESTADO_CAPTURA, String FECHA_CAPTURA, String NOMBRE_DISPOSITIVO) {
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL(
            "INSERT INTO licenciasCapturadas(RUT, NOMBRE, APELLIDO, ESTADO_CAPTURA, " +
                    "FECHA_CAPTURA, NOMBRE_DISPOSITIVO) " +
            "VALUES(" + ifN(RUT, "NULL", VRCHR) + ", " +
                    ifN(NOMBRE,"NULL", VRCHR) + ", " +
                    ifN(APELLIDO,"NULL", VRCHR) + ", " +
                    ifN(ESTADO_CAPTURA,"NULL", VRCHR) + ", " +
                    ifN(FECHA_CAPTURA,"NULL", VRCHR) + ", " +
                    ifN(NOMBRE_DISPOSITIVO,"NULL", VRCHR) + ")"
        );
        db.close();
    }
    private void registrarRechazoLicencia(String RUT, String LST_LICENCIA,
                                          String MOTIVO_RECHAZO, String tipo_captura,
                                          String nombre_dispositivo, String FECHA_CAPTURA) {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("RUT", RUT);
        values.put("LST_LICENCIA", LST_LICENCIA);
        values.put("MOTIVO_RECHAZO", MOTIVO_RECHAZO);
        values.put("tipo_captura", tipo_captura);
        values.put("nombre_dispositivo", nombre_dispositivo);
        values.put("FECHA_CAPTURA", FECHA_CAPTURA);
        db.insert("licenciasRechazado",null,values);
        db.close();
    }
    private String motivoRechazo(int num){
        String res = "";
        switch (num){
            case 0:
                res = "";
                break;
            case 1:
                res = "En Revision";
                break;
            case 2:
                res = "Acreditado";
                break;
            case 3:
                res = "No Acreditado";
                break;
            case 4:
                res = "Bloqueo";
                break;
            case 5:
                res = "No acreditado";
                break;
        }
        return res;
    }
    public static boolean isNumeric(String s)
    {
        if (s == null || s.equals("")) {
            return false;
        }
        return s.chars().allMatch(Character::isDigit);
    }
}