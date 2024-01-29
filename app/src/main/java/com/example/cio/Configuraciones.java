package com.example.cio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.cio.utilidades.DataConverter;
import com.example.cio.utilidades.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static com.example.cio.utilidades.Utilidades.ifN;

public class Configuraciones extends AppCompatActivity {

    TextView lblAppId;
    EditText txtWsConfiguraciones,
            txtUrlLogo;
    ImageView imgLogo;
    EditText txtWsInternoExterno,
            txtWsVisitas,
            txtWsTecnica,
            txtWsGrupal,
            txtWsTransportista,
            txtWsEmergencia,
            txtWsLicencias,
            txtWsMarcasVehiculos,
            txtWsTipoVehiculos,
            txtWsTipoLicencia,
            txtWsVehiculos,
            txtTiempo;
    SwitchCompat sHabilitarCamara,
            sHabilitarBtnIngresos,
            sHabilitarBtnSalidas,
            sHabilitarBtnVehiculos,
            sHabilitarBtnEspeciales,
            sHabilitarBtnVisitas,
            sHabilitarBtnTecnica,
            sHabilitarBtnGrupal,
            sHabilitarBtnTransportista,
            sHabilitarBtnEmergencia,
            sHabilitarBtnLicencias;

    ConexionSQLiteHelper conn;

    String CFGid_app,
            CFGwsConfiguraciones,
            CFGurl_logo;
    byte[] CFGimage_logo;
    String CFGwsInternoExterno,
            CFGwsVisitas,
            CFGwsTecnica,
            CFGwsGrupal,
            CFGwsTransportista,
            CFGwsEmergencia,
            CFGwsLicencias,
            CFGwsMarcaVehiculos,
            CFGwsTipoVehiculos,
            CFGwsTipoLicencia,
            CFGwsVehiculos,
            CFGwsIntExtRechazo,
            CFGwsVehicuRechazo,
            CFGwsGrupalRechazo,
            CFGwsPaseVisitaRechazo,
            CFGwsLicenciasRechazo,
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

    private static Bitmap bmpLogo = null;

    RequestQueue rq = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        lblAppId = findViewById(R.id.lblAppId);
        txtWsConfiguraciones = findViewById(R.id.txtWsConfiguraciones);
        txtUrlLogo = findViewById(R.id.txtUrlLogo);
        imgLogo = findViewById(R.id.imgLogo);
        txtWsInternoExterno = findViewById(R.id.txtWsInternoExterno);
        txtWsVisitas = findViewById(R.id.txtWsVisitas);
        txtWsTecnica = findViewById(R.id.txtWsTecnica);
        txtWsGrupal = findViewById(R.id.txtWsGrupal);
        txtWsTransportista = findViewById(R.id.txtWsTransportista);
        txtWsEmergencia = findViewById(R.id.txtWsEmergencia);
        txtWsLicencias = findViewById(R.id.txtWsLicencias);
        txtWsMarcasVehiculos = findViewById(R.id.txtWsMarcasVehiculos);
        txtWsTipoVehiculos = findViewById(R.id.txtWsTipoVehiculos);
        txtWsTipoLicencia = findViewById(R.id.txtWsTipoLicencia);
        txtWsVehiculos = findViewById(R.id.txtWsVehiculos);
        txtTiempo = findViewById(R.id.txtTiempo);
        sHabilitarCamara = findViewById(R.id.sHabilitarCamara);
        sHabilitarBtnIngresos = findViewById(R.id.sHabilitarBtnIngresos);
        sHabilitarBtnSalidas = findViewById(R.id.sHabilitarBtnSalidas);
        sHabilitarBtnVehiculos = findViewById(R.id.sHabilitarBtnVehiculos);
        sHabilitarBtnEspeciales = findViewById(R.id.sHabilitarBtnEspeciales);
        sHabilitarBtnVisitas = findViewById(R.id.sHabilitarBtnVisitas);
        sHabilitarBtnTecnica = findViewById(R.id.sHabilitarBtnTecnica);
        sHabilitarBtnGrupal = findViewById(R.id.sHabilitarBtnGrupal);
        sHabilitarBtnTransportista = findViewById(R.id.sHabilitarBtnTransportista);
        sHabilitarBtnEmergencia = findViewById(R.id.sHabilitarBtnEmergencia);
        sHabilitarBtnLicencias = findViewById(R.id.sHabilitarBtnLicencias);

        conn = new ConexionSQLiteHelper(this, "DB_CIO", null, 3);

        rq = Volley.newRequestQueue(getApplicationContext());

        //cargar configuraciones
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] campos = {"id_app", "url_wsConfiguraciones", "url_logo", "image_logo", "url_wsInternoExterno", "url_wsVisitas","url_wsTecnica", "url_wsTransportista", "url_wsEmergencia", "url_wsLicencias", "url_wsMarcaVehiculos", "url_wsTipoVehiculos", "url_wsTipoLicencia", "url_wsVehiculos", "tiempo", "habilitar_camara", "habilitar_btnIngresos", "habilitar_btnSalidas", "habilitar_btnVehiculos", "habilitar_btnEspeciales", "habilitar_btnVisitas","habilitar_btnTecnica", "habilitar_btnTransportista", "habilitar_btnEmergencia", "habilitar_btnLicencias","url_wsIntExt_Rechazo","url_wsVehicu_Rechazo","url_wsGrupal","habilitar_btnGrupal","url_wsEspeci_Rechazo","url_wsPaseVi_Rechazo","url_wsLicenc_Rechazo"};
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
            CFGwsTecnica = cursor.getString(6);
            CFGwsTransportista = cursor.getString(7);
            CFGwsEmergencia = cursor.getString(8);
            CFGwsLicencias = cursor.getString(9);
            CFGwsMarcaVehiculos = cursor.getString(10);
            CFGwsTipoVehiculos = cursor.getString(11);
            CFGwsTipoLicencia = cursor.getString(12);
            CFGwsVehiculos = cursor.getString(13);
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
            CFGwsGrupal = cursor.getString(27);
            CFGhabilitar_btnGrupal = cursor.getString(28);
            CFGwsGrupalRechazo = cursor.getString(29);
            CFGwsPaseVisitaRechazo = cursor.getString(30);
            CFGwsLicenciasRechazo = cursor.getString(31);

            lblAppId.setText(CFGid_app);
            txtWsConfiguraciones.setText(CFGwsConfiguraciones);
            txtUrlLogo.setText(CFGurl_logo);
            if (CFGimage_logo != null) {
                bmpLogo = DataConverter.convertByteArray2Image(CFGimage_logo);
                imgLogo.setImageBitmap(bmpLogo);
            }
            txtWsInternoExterno.setText(CFGwsInternoExterno);
            txtWsVisitas.setText(CFGwsVisitas);
            txtWsTecnica.setText(CFGwsTecnica);
            txtWsGrupal.setText(CFGwsGrupal);
            txtWsTransportista.setText(CFGwsTransportista);
            txtWsEmergencia.setText(CFGwsEmergencia);
            txtWsLicencias.setText(CFGwsLicencias);
            txtWsMarcasVehiculos.setText(CFGwsMarcaVehiculos);
            txtWsTipoVehiculos.setText(CFGwsTipoVehiculos);
            txtWsTipoLicencia.setText(CFGwsTipoLicencia);
            txtWsVehiculos.setText(CFGwsVehiculos);
            txtTiempo.setText(CFGtiempo);
            if(CFGhabilitar_camara.equals("1")){
                sHabilitarCamara.setChecked(true);
            }else{
                sHabilitarCamara.setChecked(false);
            }
            if(CFGhabilitar_btnIngresos.equals("1")){
                sHabilitarBtnIngresos.setChecked(true);
            } else {
                sHabilitarBtnIngresos.setChecked(false);
            }
            if(CFGhabilitar_btnSalidas.equals("1")){
                sHabilitarBtnSalidas.setChecked(true);
            } else {
                sHabilitarBtnSalidas.setChecked(false);
            }
            if(CFGhabilitar_btnVehiculos.equals("1")){
                sHabilitarBtnVehiculos.setChecked(true);
            } else {
                sHabilitarBtnVehiculos.setChecked(false);
            }
            if(CFGhabilitar_btnEspeciales.equals("1")){
                sHabilitarBtnEspeciales.setChecked(true);
            } else {
                sHabilitarBtnEspeciales.setChecked(false);
            }
            if(CFGhabilitar_btnVisitas.equals("1")){
                sHabilitarBtnVisitas.setChecked(true);
            } else {
                sHabilitarBtnVisitas.setChecked(false);
            }
            if(CFGhabilitar_btnTecnica.equals("1")){
                sHabilitarBtnTecnica.setChecked(true);
            } else {
                sHabilitarBtnTecnica.setChecked(false);
            }
            if(CFGhabilitar_btnGrupal.equals("1")){
                sHabilitarBtnGrupal.setChecked(true);
            } else {
                sHabilitarBtnGrupal.setChecked(false);
            }
            if(CFGhabilitar_btnTransportista.equals("1")){
                sHabilitarBtnTransportista.setChecked(true);
            } else {
                sHabilitarBtnTransportista.setChecked(false);
            }
            if(CFGhabilitar_btnEmergencia.equals("1")){
                sHabilitarBtnEmergencia.setChecked(true);
            } else {
                sHabilitarBtnEmergencia.setChecked(false);
            }
            if(CFGhabilitar_btnLicencias.equals("1")){
                sHabilitarBtnLicencias.setChecked(true);
            } else {
                sHabilitarBtnLicencias.setChecked(false);
            }
        }
    }

    public void onclickCargarImagen(View view) {
        cargarImagenLogo();
    }

    private void cargarImagenLogo() {
        try {
            String url = txtUrlLogo.getText().toString();
            if(url.trim().equals("")){
                Toast.makeText(this, "La url ingresada no es válida", Toast.LENGTH_LONG).show();
            }else {
                Glide.with(this)
                        .load(url)
                        .into(imgLogo);
            }
        }catch (Exception e){
            Toast.makeText(this, "La url ingresada no es válida", Toast.LENGTH_LONG).show();
        }
    }

    public void onclickBorrarImagen(View view) {
        imgLogo.setImageBitmap(null);
        bmpLogo = null;
        txtUrlLogo.setText("");
    }

    public void onclickGuardarConfiguracion(View view) {
        try {
            CFGwsConfiguraciones = txtWsConfiguraciones.getText().toString();
            CFGurl_logo = txtUrlLogo.getText().toString();
            imgLogo.setDrawingCacheEnabled(true);
            imgLogo.buildDrawingCache();
            Bitmap bmpImage = imgLogo.getDrawingCache();
            if(bmpImage != null){
                CFGimage_logo = DataConverter.convertImage2ByteArray(bmpImage);
            } else {
                CFGimage_logo = null;
            }
            CFGwsInternoExterno = txtWsInternoExterno.getText().toString();
            CFGwsVisitas = txtWsVisitas.getText().toString();
            CFGwsTecnica = txtWsTecnica.getText().toString();
            CFGwsGrupal = txtWsGrupal.getText().toString();
            CFGwsTransportista = txtWsTransportista.getText().toString();
            CFGwsEmergencia = txtWsEmergencia.getText().toString();
            CFGwsLicencias = txtWsLicencias.getText().toString();
            CFGwsMarcaVehiculos = txtWsMarcasVehiculos.getText().toString();
            CFGwsTipoVehiculos = txtWsTipoVehiculos.getText().toString();
            CFGwsTipoLicencia = txtWsTipoLicencia.getText().toString();
            CFGwsVehiculos = txtWsVehiculos.getText().toString();
            CFGtiempo = txtTiempo.getText().toString();
            CFGhabilitar_camara = ((sHabilitarCamara.isChecked())?"1":"0");
            CFGhabilitar_btnIngresos = ((sHabilitarBtnIngresos.isChecked())?"1":"0");
            CFGhabilitar_btnSalidas = ((sHabilitarBtnSalidas.isChecked())?"1":"0");
            CFGhabilitar_btnVehiculos = ((sHabilitarBtnVehiculos.isChecked())?"1":"0");
            CFGhabilitar_btnEspeciales = ((sHabilitarBtnEspeciales.isChecked())?"1":"0");
            CFGhabilitar_btnVisitas = ((sHabilitarBtnVisitas.isChecked())?"1":"0");
            CFGhabilitar_btnTecnica = ((sHabilitarBtnTecnica.isChecked())?"1":"0");
            CFGhabilitar_btnGrupal = ((sHabilitarBtnGrupal.isChecked())?"1":"0");
            CFGhabilitar_btnTransportista = ((sHabilitarBtnTransportista.isChecked())?"1":"0");
            CFGhabilitar_btnEmergencia = ((sHabilitarBtnEmergencia.isChecked())?"1":"0");
            CFGhabilitar_btnLicencias = ((sHabilitarBtnLicencias.isChecked())?"1":"0");

            SQLiteDatabase db = conn.getWritableDatabase();

            db.execSQL("DROP TABLE IF EXISTS configuraciones");
            db.execSQL(Utilidades.CREAR_TABLA_CONFIGURACIONES);

            ContentValues values = new ContentValues();
            values.put("id_app", CFGid_app);
            values.put("url_wsConfiguraciones", CFGwsConfiguraciones);
            values.put("url_logo", CFGurl_logo);
            if(CFGimage_logo != null){
                values.put("image_logo", CFGimage_logo);
            } else {
                values.putNull("image_logo");
            }
            values.put("url_wsInternoExterno", CFGwsInternoExterno);
            values.put("url_wsVisitas", CFGwsVisitas);
            values.put("url_wsTecnica", CFGwsTecnica);
            values.put("url_wsGrupal",CFGwsGrupal);
            values.put("url_wsTransportista", CFGwsTransportista);
            values.put("url_wsEmergencia", CFGwsEmergencia);
            values.put("url_wsLicencias", CFGwsLicencias);
            values.put("url_wsMarcaVehiculos", CFGwsMarcaVehiculos);
            values.put("url_wsTipoVehiculos", CFGwsTipoVehiculos);
            values.put("url_wsTipoLicencia", CFGwsTipoLicencia);
            values.put("url_wsVehiculos", CFGwsVehiculos);
            values.put("url_wsIntExt_Rechazo", CFGwsIntExtRechazo);
            values.put("url_wsVehicu_Rechazo",CFGwsVehicuRechazo);
            values.put("url_wsEspeci_Rechazo",CFGwsGrupalRechazo);
            values.put("url_wsPaseVi_Rechazo",CFGwsPaseVisitaRechazo);
            values.put("url_wsLicenc_Rechazo",CFGwsLicenciasRechazo);
            values.put("tiempo", CFGtiempo);
            values.put("habilitar_camara", CFGhabilitar_camara);
            values.put("habilitar_btnIngresos", CFGhabilitar_btnIngresos);
            values.put("habilitar_btnSalidas", CFGhabilitar_btnSalidas);
            values.put("habilitar_btnVehiculos", CFGhabilitar_btnVehiculos);
            values.put("habilitar_btnEspeciales", CFGhabilitar_btnEspeciales);
            values.put("habilitar_btnVisitas", CFGhabilitar_btnVisitas);
            values.put("habilitar_btnTecnica", CFGhabilitar_btnTecnica);
            values.put("habilitar_btnGrupal",CFGhabilitar_btnGrupal);
            values.put("habilitar_btnTransportista", CFGhabilitar_btnTransportista);
            values.put("habilitar_btnEmergencia", CFGhabilitar_btnEmergencia);
            values.put("habilitar_btnLicencias", CFGhabilitar_btnLicencias);
            db.insert("configuraciones", null, values);
            db.close();
            Toast.makeText(this, "Configuraciones actualizadas. Por favor reinicie la app para confirmar cambios", Toast.LENGTH_LONG).show();
            finish();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onclickVolver(View view) {
        finish();
    }

    public void onclickCargarWebservice(View view) {
        try {
            Map<String, String> parametros = new Hashtable<String, String>();
            //parametros.put("accion", "descargar-ggss");
            StringRequest stringRequest = new StringRequest(Request.Method.GET, txtWsConfiguraciones.getText().toString(),
                    response -> html2HashtableConfiguraciones(response),
                    error -> Toast.makeText(this, "Error al obtener configuraciones", Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros;
                }
            };
            rq.add(stringRequest);
        }catch (Exception e){ }
    }

    private void html2HashtableConfiguraciones(String texto){
        try {
            JSONArray jsonArray = new JSONArray(texto);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            txtUrlLogo.setText(ifN(jsonObject.getString("URL_LOGO")));
            txtWsInternoExterno.setText(ifN(jsonObject.getString("URL_PRUEBA_INTEXT")));
            txtWsVisitas.setText(ifN(jsonObject.getString("URL_VISITAS_ADMIN")));
            txtWsTecnica.setText(ifN(jsonObject.getString("URL_VISITA_TECNICA")));
            txtWsGrupal.setText(ifN(jsonObject.getString("URL_PRUEBA_VISITA")));
            txtWsTransportista.setText(ifN(jsonObject.getString("URL_PRUEBA_TRANSPO")));
            txtWsEmergencia.setText(ifN(jsonObject.getString("URL_PRUEBA_EMERGEN")));
            txtWsLicencias.setText(ifN(jsonObject.getString("URL_PRUEBA_LICENC")));
            txtWsMarcasVehiculos.setText(ifN(jsonObject.getString("URL_MARCASVEHICULO")));
            txtWsTipoVehiculos.setText(ifN(jsonObject.getString("URL_TIPO_VEHICULO")));
            txtWsTipoLicencia.setText(ifN(jsonObject.getString("URL_TIPO_LICENCIA")));
            txtWsVehiculos.setText(ifN(jsonObject.getString("URL_PRUEBA_VEHICU")));
            CFGwsIntExtRechazo = ifN(jsonObject.getString("URL_INTEXT_RECHAZO"));
            CFGwsVehicuRechazo = ifN(jsonObject.getString("URL_VEHICU_RECHAZO"));
            CFGwsGrupalRechazo = ifN(jsonObject.getString("URL_ESPECI_RECHAZO"));
            CFGwsPaseVisitaRechazo = ifN(jsonObject.getString("URL_VISITA_RECHAZO"));
            CFGwsLicenciasRechazo = ifN(jsonObject.getString("URL_LICENC_RECHAZO"));
            txtTiempo.setText(ifN(jsonObject.getString("TIEMPO")));
            sHabilitarCamara.setChecked(ifN(jsonObject.getString("HABILITAR_CAMARA")).equals("1"));
            try{
                if(ifN(jsonObject.getString("HABILITAR_BTNINGRESOS")).equals("1")) sHabilitarBtnIngresos.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNSALIDAS")).equals("1")) sHabilitarBtnSalidas.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNVEHICULOS")).equals("1")) sHabilitarBtnVehiculos.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNESPECIALES")).equals("1")) sHabilitarBtnEspeciales.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNVISITAS")).equals("1")) sHabilitarBtnVisitas.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNTECNICA")).equals("1")) sHabilitarBtnTecnica.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNGRUPAL")).equals("1")) sHabilitarBtnGrupal.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNTRANSPORTISTA")).equals("1")) sHabilitarBtnTransportista.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNEMERGENCIA")).equals("1")) sHabilitarBtnEmergencia.setChecked(true);
                if(ifN(jsonObject.getString("HABILITAR_BTNLICENCIAS")).equals("1")) sHabilitarBtnLicencias.setChecked(true);
            }catch (Exception e){ }
            cargarImagenLogo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}