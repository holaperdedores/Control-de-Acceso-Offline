package com.example.cio.utilidades;

import static com.example.cio.utilidades.Utilidades.VRCHR;
import static com.example.cio.utilidades.Utilidades.ifN;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cio.ConexionSQLiteHelper;
import com.example.cio.Control;
import com.example.cio.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Cargar {
    final static String TAG = "CargarDescarga";
    ConexionSQLiteHelper conn;

    String CFGid_app,
            CFGwsConfiguraciones,
            CFGurl_logo;
    byte[] CFGimage_logo;
    String CFGwsInternoExterno,
            CFGwsVisitas,
            CFGwsGrupal,
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

    RequestQueue rq;

    int clase;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public Cargar(ConexionSQLiteHelper conexionSQLiteHelper, Context context, int tipo){
        clase = tipo;
        //cargar o crear configuraciones app
        conn = conexionSQLiteHelper;
        rq = Volley.newRequestQueue(context);
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
                "url_wsPaseVi_Rechazo","url_wsLicenc_Rechazo","url_wsGrupal"};
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
            CFGwsGrupal = cursor.getString(31);
        } else {
            //valores iniciales
            String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
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
        sincronizacionUnlock();

        sync();
    }
    public void terminoTarea(int problem) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis();
                Date resultdate = new Date(millis);
                String dateString=sdf.format(resultdate);
                if(clase == 1){
                    MainActivity.dialogo.setVisibility(View.VISIBLE);
                    if(problem == 0){
                        MainActivity.tituloDialogo.setText("Actualización Exitosa");
                    }else{
                        MainActivity.tituloDialogo.setText("Error al actualizar");
                    }
                    MainActivity.textoDialogo.setText("Presione Cerrar para salir.");
                    MainActivity.progressDialogo.setVisibility(View.INVISIBLE);
                    MainActivity.buttonDialogo.setVisibility(View.VISIBLE);
                    MainActivity.fechaCarga.setText("Última Carga : "+dateString);
                }else{
                    Control.dialogo.setVisibility(View.VISIBLE);
                    Control.tituloDialogo.setText("Actualización Exitosa");
                    Control.textoDialogo.setText("Presione Cerrar para salir.");
                    Control.progressDialogo.setVisibility(View.INVISIBLE);
                    Control.buttonDialogo.setVisibility(View.VISIBLE);
                    Control.fechaCarga.setText("Última Carga : "+dateString);
                }

            }
        });
    }
    public void sincronizacionLock(){
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS sincronizacion");
        db.execSQL(Utilidades.CREAR_TABLA_SINCRONIZACION);
        db.execSQL(
                "INSERT INTO sincronizacion(estado) " +
                        "                    VALUES('1'   )"
        );
        db.close();
    }
    public void sincronizacionUnlock(){
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS sincronizacion");
        db.execSQL(Utilidades.CREAR_TABLA_SINCRONIZACION);
        db.close();
    }
    public boolean sincronizacionIsLocked(){
        boolean resultado = false;
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = new String[]{"estado"};
        Cursor cursor = db.query("sincronizacion", campos, null,null, null, null, null);
        if (cursor.getCount() > 0) resultado = true;
        cursor.close();
        db.close();
        return resultado;
    }
    public void sync() {
        if (!sincronizacionIsLocked()) {
            sincronizacionLock();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if(clase==1){
                        MainActivity.dialogo.setVisibility(View.VISIBLE);
                        MainActivity.tituloDialogo.setText("Espere");
                        MainActivity.textoDialogo.setText("Actualizando...");
                        MainActivity.progressDialogo.setVisibility(View.VISIBLE);
                        MainActivity.buttonDialogo.setVisibility(View.INVISIBLE);
                    }else{
                        Control.dialogo.setVisibility(View.VISIBLE);
                        Control.tituloDialogo.setText("Espere");
                        Control.textoDialogo.setText("Actualizando...");
                        Control.progressDialogo.setVisibility(View.VISIBLE);
                        Control.buttonDialogo.setVisibility(View.INVISIBLE);
                    }

                }
            });
            long millis = System.currentTimeMillis();
            Date resultdate = new Date(millis);
            String dateString=sdf.format(resultdate);
            LogUtils.LOGI(TAG, "Comienza la carga en "+ dateString);
            subirPersonalCapturado();
        }

    }
    public void subirPersonalCapturado() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"RUT", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "TIPO_CAPTURA", "NOMBRE_DISPOSITIVO", "FECHA_CAPTURA", "ID_PERSONA", "FECHA_CARGA"};
        Cursor cursor = db.query("personalCapturado", campos, null, null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de personal capturadas encontrados es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"RUT\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"ID_PERSONA\":" + ((cursor.getString(8)!=null)?"\"" + cursor.getString(8) + "\"":"null") + "," +
                    "\"NOMBRE\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"APELLIDO\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"CARGO\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"EMPRESA\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"TIPO_CAPTURA\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") + "," +
                    "\"NOMBRE_DISPOSITIVO\":" + ((cursor.getString(6)!=null)?"\"" + cursor.getString(6) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(7)!=null)?"\"" + cursor.getString(7) + "\"":"null") + "," +
                    "\"FECHA_CARGA\":" + ((cursor.getString(9)!=null)?"\"" + cursor.getString(9) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("internoexterno", jsonString);
        LogUtils.LOGI(TAG, "la url de INEXT es "+CFGwsInternoExterno);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsInternoExterno,
                    response -> {
                        if (response.contains("nalfa11")) {
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DROP TABLE IF EXISTS personalCapturado");
                            dbW.execSQL(Utilidades.CREAR_TABLA_PERSONAL_CAPTURADO);
                            dbW.close();
                            subirPersonalRechazado();
                            LogUtils.LOGI(TAG,"Carga correctamente la info");
                        }else{
                            LogUtils.LOGI(TAG,"Carga incorrecta Error "+response);
                            terminoTarea(1);
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else{
            subirPersonalRechazado();
        }
        db.close();
        cursor.close();
    }
    public void subirPersonalRechazado() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"TIPO_CAPTURA", "RUT", "NOMBRE_DISPOSITIVO", "MOTIVO_RECHAZO","FECHA_CAPTURA"};
        Cursor cursor = db.query("personalRechazado", campos, null, null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de personal rechazado encontrados es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_TIPO_ACCESO\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"ID_PERSONA_RUT\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"MAC_PDA\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"MSG\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("rechazo", jsonString);
        LogUtils.LOGI(TAG,"La URL de rechazo es "+CFGwsIntExtRechazo);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsIntExtRechazo,
                    response -> {
                        if (response.contains("nalfa11")) {
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DROP TABLE IF EXISTS personalRechazado");
                            dbW.execSQL(Utilidades.CREAR_TABLA_PERSONAL_RECHAZADO);
                            dbW.close();
                            subirVehiculoRechazado();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else{
            subirVehiculoRechazado();
        }
        db.close();
        cursor.close();
    }
    public void subirVehiculoRechazado() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"id_vehiculo", "patente", "id_empresa", "MOTIVO_RECHAZO","tipo_captura","nombre_dispositivo","FECHA_CAPTURA"};
        Cursor cursor = db.query("vehiculosRechazado", campos, null, null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de vehiculos rechazado encontrados es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_TIPO_ACCESO\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"ID_VEHICULO\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"MAC_PDA\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") + "," +
                    "\"MSG\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(6)!=null)?"\"" + cursor.getString(6) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("rechazo", jsonString);
        LogUtils.LOGI(TAG,"La URL de rechazo es "+CFGwsVehicuRechazo);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsVehicuRechazo,
                    response -> {
                        if (response.contains("nalfa11")) {
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DROP TABLE IF EXISTS vehiculosRechazado");
                            dbW.execSQL(Utilidades.CREAR_TABLA_VEHICULOS_RECHAZADO);
                            dbW.close();
                            subirVehiculoslCapturado();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else{
            subirVehiculoslCapturado();
        }
        db.close();
        cursor.close();
    }
    public void subirVehiculoslCapturado() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"id_grupo", "id_vehiculo", "tipo_captura", "nombre_dispositivo","fecha"};
        Cursor cursor = db.query("vehiculosCapturadas", campos, null, null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de vehiculos encontrados es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_REG_CON_VEHICULO\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"ID_VEHICULO\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"TIPO_REGISTRO\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"PDA_CONTROL\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        String[] campos2 = {"id", "id_grupo", "id_persona", "tipo", "fecha", "estado_acre"};
        Cursor cursor2 = db.query("pasajerosCapturadas", campos2, null, null, null, null, null);
        String jsonString2 = "";
        LogUtils.LOGI(TAG,"la cantidad de vehiculos detalle encontrados es "+cursor.getCount());
        while (cursor2.moveToNext()) {
            jsonString2 += ((!jsonString2.equals(""))?",":"") +
                    "{" +
                    "\"ID_REG_CON_VEHICULO_DETALLE\":" + ((cursor2.getString(0)!=null)?"\"" + cursor2.getString(0) + "\"":"null") + "," +
                    "\"ID_REG_CON_VEHICULO\":" + cursor2.getInt(1) + "," +
                    "\"ID_PERSONA\":" + ((cursor2.getString(2)!=null)?"\"" + cursor2.getString(2) + "\"":"null") + "," +
                    "\"ESTADO_ACRE\":" + ((cursor2.getString(5)!=null)?"\"" + cursor2.getString(5) + "\"":"null") + "," +
                    "\"TIPO_PERSONA\":" + ((cursor2.getString(3)!=null)?"\"" + cursor2.getString(3) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor2.getString(4)!=null)?"\"" + cursor2.getString(4) + "\"":"null") +
                    "}";
        }
        jsonString2 = "[" + jsonString2 + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("vehiculos", jsonString);
        parametros.put("pasajeros", jsonString2);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsVehiculos,
                    response -> {
                        if (response.contains("nalfa11")) {
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DROP TABLE IF EXISTS vehiculosCapturadas");
                            dbW.execSQL(Utilidades.CREAR_TABLA_VEHICULOS_CAPTURADAS);
                            dbW.execSQL("DROP TABLE IF EXISTS pasajerosCapturadas");
                            dbW.execSQL(Utilidades.CREAR_TABLA_PASAJEROS_CAPTURADOS);
                            dbW.close();
                            subirCapturadosVisitaTecnica();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        LogUtils.LOGE(TAG,"El error es "+error.toString());
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else{
            subirCapturadosVisitaTecnica();
        }
        cursor.close();
        db.close();
        cursor2.close();
    }
    public void subirCapturadosVisitaTecnica() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"ID_REGISTRO_VISITA", "TIPO_REGISTRO", "NOMBRE_DISPOSITIVO", "FECHA_CAPTURA"};
        Cursor cursor = db.query("visitaTecnicaCap", campos, null, null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de tecnicas es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_REGISTRO_VISITA\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"TIPO_REGISTRO\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"PDA\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") +
                    "}";
        }

        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("PASE", jsonString);
        LogUtils.LOGI(TAG,"la URL de visita admin es "+CFGwsVisitas);
        if (cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsVisitas,
                    response -> {
                        if (response.contains("nalfa11")) {
                            //eliminar registros de visita
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DELETE FROM visitaTecnicaCap");
                            dbW.close();
                            subirPaseVisitaRechazado();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                        LogUtils.LOGE(TAG,error.toString());
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rq.add(stringRequest);
        }else{
            subirPaseVisitaRechazado();
        }
        db.close();
        cursor.close();
    }
    public void subirPaseVisitaRechazado() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"ID_TIPO_ACCESO", "ID_PASE", "ID_TIPO_PASE", "nombre_dispositivo", "MOTIVO_RECHAZO","FECHA_CAPTURA"};
        Cursor cursor = db.query("paseVisitaRechazado", campos, null, null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de pase visita encontrados es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_TIPO_ACCESO\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"ID_PASE\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"ID_TIPO_PASE\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"MAC_PDA\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"MSG\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("rechazo", jsonString);
        LogUtils.LOGI(TAG,"La URL de rechazo es "+CFGwsPaseViRechazo);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsPaseViRechazo,
                    response -> {
                        if (response.contains("nalfa11")) {
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DROP TABLE IF EXISTS paseVisitaRechazado");
                            dbW.execSQL(Utilidades.CREAR_TABLA_PASE_VISITA_RECHAZADO);
                            dbW.close();
                            subirEspecialesCapturadosGrupal();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else {
            subirEspecialesCapturadosGrupal();
        }
        db.close();
        cursor.close();
    }
    public void subirEspecialesCapturadosGrupal() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"ID_PASE", "ID_LISTADO", "RUT", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "PATENTE", "NOMBRE_DISPOSITIVO", "FECHA_CAPTURA"};
        String[] p = {"1"};
        Cursor cursor = db.query("especialesCapturados", campos, "ID_TIPO_PASE = ?", p, null, null, null);
        String jsonString = "";
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_PASE\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"ID_LISTADO\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"RUT\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"NOMBRE\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"APELLIDO\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"CARGO\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") + "," +
                    "\"EMPRESA\":" + ((cursor.getString(6)!=null)?"\"" + cursor.getString(6) + "\"":"null") + "," +
                    "\"PATENTE\":" + ((cursor.getString(7)!=null)?"\"" + cursor.getString(7) + "\"":"null") + "," +
                    "\"NOMBRE_DISPOSITIVO\":" + ((cursor.getString(8)!=null)?"\"" + cursor.getString(8) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(9)!=null)?"\"" + cursor.getString(9) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("especiales", jsonString);
        LogUtils.LOGI(TAG,"URL para capturados grupal es "+CFGwsGrupal);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsGrupal,
                    response -> {
                        if (response.contains("nalfa11")) {
                            //eliminar registros de transportista
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DELETE FROM especialesCapturados WHERE ID_TIPO_PASE = '1'");
                            dbW.close();
                            subirEspecialesCapturadosEmergencia();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else{
            subirEspecialesCapturadosTransportista();
        }
        db.close();
        cursor.close();
    }
    public void subirEspecialesCapturadosTransportista() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"ID_PASE", "ID_LISTADO", "RUT", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "PATENTE", "NOMBRE_DISPOSITIVO", "FECHA_CAPTURA"};
        String[] p = {"4"};
        Cursor cursor = db.query("especialesCapturados", campos, "ID_TIPO_PASE = ?", p, null, null, null);
        String jsonString = "";
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_PASE\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"ID_LISTADO\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"RUT\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"NOMBRE\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"APELLIDO\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"CARGO\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") + "," +
                    "\"EMPRESA\":" + ((cursor.getString(6)!=null)?"\"" + cursor.getString(6) + "\"":"null") + "," +
                    "\"PATENTE\":" + ((cursor.getString(7)!=null)?"\"" + cursor.getString(7) + "\"":"null") + "," +
                    "\"NOMBRE_DISPOSITIVO\":" + ((cursor.getString(8)!=null)?"\"" + cursor.getString(8) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(9)!=null)?"\"" + cursor.getString(9) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("especiales", jsonString);
        LogUtils.LOGI(TAG,"URL para transporte es "+CFGwsTransportista);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsTransportista,
                    response -> {
                        if (response.contains("nalfa11")) {
                            //eliminar registros de transportista
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DELETE FROM especialesCapturados WHERE ID_TIPO_PASE = '4'");
                            dbW.close();
                            subirEspecialesCapturadosEmergencia();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else{
            subirEspecialesCapturadosEmergencia();
        }
        db.close();
        cursor.close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void subirEspecialesCapturadosEmergencia() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"ID_PASE", "ID_LISTADO", "RUT", "NOMBRE", "APELLIDO", "CARGO", "EMPRESA", "PATENTE", "NOMBRE_DISPOSITIVO", "FECHA_CAPTURA"};
        String[] p = {"3"};
        Cursor cursor = db.query("especialesCapturados", campos, "ID_TIPO_PASE = ?", p, null, null, null);
        String jsonString = "";
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_PASE\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"ID_LISTADO\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"RUT\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"NOMBRE\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"APELLIDO\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"CARGO\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") + "," +
                    "\"EMPRESA\":" + ((cursor.getString(6)!=null)?"\"" + cursor.getString(6) + "\"":"null") + "," +
                    "\"PATENTE\":" + ((cursor.getString(7)!=null)?"\"" + cursor.getString(7) + "\"":"null") + "," +
                    "\"NOMBRE_DISPOSITIVO\":" + ((cursor.getString(8)!=null)?"\"" + cursor.getString(8) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(9)!=null)?"\"" + cursor.getString(9) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("especiales", jsonString);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsEmergencia,
                    response -> {
                        if (response.contains("nalfa11")) {
                            //eliminar registros de emergencia
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DELETE FROM especialesCapturados WHERE ID_TIPO_PASE = '5'");
                            dbW.close();
                            subirEspecialRechazado();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else {
            subirEspecialRechazado();
        }
        db.close();
        cursor.close();
    }
    public void subirEspecialRechazado() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"tipo_captura", "ID_PASE", "ID_TIPO_PASE", "nombre_dispositivo", "MOTIVO_RECHAZO","FECHA_CAPTURA"};
        Cursor cursor = db.query("especialesRechazado", campos, null, null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de especiales rechazado encontrados es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_TIPO_ACCESO\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"ID_PASE\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"ID_TIPO_PASE\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"MAC_PDA\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"MSG\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("rechazo", jsonString);
        LogUtils.LOGI(TAG,"La URL de rechazo es "+CFGwsEspeciRechazo);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsEspeciRechazo,
                    response -> {
                        if (response.contains("nalfa11")) {
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DROP TABLE IF EXISTS especialesRechazado");
                            dbW.execSQL(Utilidades.CREAR_TABLA_ESPECIALES_RECHAZADO);
                            dbW.close();
                            subirLicenciasCapturadas();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else {
            subirLicenciasCapturadas();
        }
        db.close();
        cursor.close();
    }
    public void subirLicenciasCapturadas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"RUT", "NOMBRE", "APELLIDO", "ESTADO_CAPTURA", "FECHA_CAPTURA", "NOMBRE_DISPOSITIVO"};
        Cursor cursor = db.query("licenciasCapturadas", campos, null,null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de licencias encontrados es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"RUT\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"NOMBRE\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"APELLIDO\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"TIPO_CAPTURA\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"NOMBRE_DISPOSITIVO\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("licencias", jsonString);
        LogUtils.LOGI(TAG,"La URL de licencia es "+CFGwsLicencias);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsLicencias,
                    response -> {
                        if (response.contains("nalfa11")) {
                            //reiniciar tabla licenciasCapturadas
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DROP TABLE IF EXISTS licenciasCapturadas");
                            dbW.execSQL(Utilidades.CREAR_TABLA_LICENCIAS_CAPTURADAS);
                            dbW.close();
                            subirLicenciasRechazado();
                            //Toast.makeText(this, "Actualización completa", Toast.LENGTH_SHORT).show();
                        }else{
                            sincronizacionUnlock();
                        }
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else{
            subirLicenciasRechazado();
        }
        db.close();
        cursor.close();
    }
    public void subirLicenciasRechazado() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"tipo_captura", "RUT", "LST_LICENCIA", "nombre_dispositivo", "MOTIVO_RECHAZO","FECHA_CAPTURA"};
        Cursor cursor = db.query("licenciasRechazado", campos, null, null, null, null, null);
        String jsonString = "";
        LogUtils.LOGI(TAG,"la cantidad de licencias rechazado encontrados es "+cursor.getCount());
        while (cursor.moveToNext()) {
            jsonString += ((!jsonString.equals(""))?",":"") +
                    "{" +
                    "\"ID_TIPO_ACCESO\":" + ((cursor.getString(0)!=null)?"\"" + cursor.getString(0) + "\"":"null") + "," +
                    "\"RUT\":" + ((cursor.getString(1)!=null)?"\"" + cursor.getString(1) + "\"":"null") + "," +
                    "\"LST_LICENCIA\":" + ((cursor.getString(2)!=null)?"\"" + cursor.getString(2) + "\"":"null") + "," +
                    "\"MAC_PDA\":" + ((cursor.getString(3)!=null)?"\"" + cursor.getString(3) + "\"":"null") + "," +
                    "\"MSG\":" + ((cursor.getString(4)!=null)?"\"" + cursor.getString(4) + "\"":"null") + "," +
                    "\"FECHA_CAPTURA\":" + ((cursor.getString(5)!=null)?"\"" + cursor.getString(5) + "\"":"null") +
                    "}";
        }
        jsonString = "[" + jsonString + "]";
        Map<String, String> parametros = new Hashtable<String, String>();
        parametros.put("rechazo", jsonString);
        LogUtils.LOGI(TAG,"La URL de rechazo es "+CFGwsLicenciaRechazo);
        if(cursor.getCount()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CFGwsLicenciaRechazo,
                    response -> {
                        if (response.contains("nalfa11")) {
                            SQLiteDatabase dbW = conn.getWritableDatabase();
                            dbW.execSQL("DROP TABLE IF EXISTS licenciasRechazado");
                            dbW.execSQL(Utilidades.CREAR_TABLA_LICENCIAS_RECHAZADO);
                            dbW.close();
                            sincronizacionUnlock();
                        }
                        sincronizacionUnlock();
                    },
                    error -> {
                        sincronizacionUnlock();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }else{
            sincronizacionUnlock();
        }
        terminoTarea(0);
        db.close();
        cursor.close();
    }
}
