package com.example.cio.utilidades;

import java.util.Calendar;

public class Utilidades {

    public static final String VRCHR = "'VRCHR'";

    public static final String CREAR_TABLA_CONFIGURACIONES = "CREATE TABLE configuraciones(id_configuracion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                        "id_app TEXT, " +
                                                                                        "url_wsConfiguraciones TEXT, " +
                                                                                        "url_logo TEXT, " +
                                                                                        "image_logo BLOB, " +
                                                                                        "url_wsInternoExterno TEXT, " +
                                                                                        "url_wsVisitas TEXT, " +
                                                                                        "url_wsTecnica TEXT, " +
                                                                                        "url_wsGrupal TEXT, " +
                                                                                        "url_wsTransportista TEXT, " +
                                                                                        "url_wsEmergencia TEXT, " +
                                                                                        "url_wsLicencias TEXT, " +
                                                                                        "url_wsMarcaVehiculos TEXT, " +
                                                                                        "url_wsTipoVehiculos TEXT, " +
                                                                                        "url_wsTipoLicencia TEXT, " +
                                                                                        "url_wsVehiculos TEXT, " +
                                                                                        "url_wsIntExt_Rechazo TEXT, " +
                                                                                        "url_wsVehicu_Rechazo TEXT, " +
                                                                                        "url_wsEspeci_Rechazo TEXT, " +
                                                                                        "url_wsPaseVi_Rechazo TEXT, " +
                                                                                        "url_wsLicenc_Rechazo TEXT, " +
                                                                                        "tiempo TEXT, " +
                                                                                        "habilitar_camara TEXT, " +
                                                                                        "habilitar_btnIngresos TEXT, " +
                                                                                        "habilitar_btnSalidas TEXT, " +
                                                                                        "habilitar_btnVehiculos TEXT, " +
                                                                                        "habilitar_btnEspeciales TEXT, " +
                                                                                        "habilitar_btnVisitas TEXT, " +
                                                                                        "habilitar_btnTecnica TEXT, " +
                                                                                        "habilitar_btnTransportista TEXT, " +
                                                                                        "habilitar_btnEmergencia TEXT, " +
                                                                                        "habilitar_btnLicencias TEXT, " +
                                                                                        "habilitar_btnGrupal TEXT" +
                                                                                        ")";
    public static final String CREAR_TABLA_PERSONAL = "CREATE TABLE personal(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                            "ID_PERSONA INTEGER, " +
                                                                            "RUT TEXT, " +
                                                                            "NOMBRE TEXT, " +
                                                                            "APELLIDO TEXT, " +
                                                                            "CARGO TEXT, " +
                                                                            "EMPRESA TEXT, " +
                                                                            "ESTADO INTEGER, " +
                                                                            "CONTRATO INTEGER, " +
                                                                            "INICIO_CONTRATO TEXT, " +
                                                                            "TERMINO_CONTRATO TEXT, " +
                                                                            "ID_EMPRESA INTEGER" +
                                                                            ")";
    public static final String CREAR_TABLA_PERSONAL_CAPTURADO = "CREATE TABLE personalCapturado(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                                "ID_PERSONA INTEGER, " +
                                                                                                "RUT TEXT, " +
                                                                                                "NOMBRE TEXT, " +
                                                                                                "APELLIDO TEXT, " +
                                                                                                "CARGO TEXT, " +
                                                                                                "EMPRESA TEXT, " +
                                                                                                "TIPO_CAPTURA TEXT, " +
                                                                                                "NOMBRE_DISPOSITIVO TEXT, " +
                                                                                                "FECHA_CAPTURA TEXT)";
    public static final String CREAR_TABLA_PERSONAL_RECHAZADO = "CREATE TABLE personalRechazado(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "RUT TEXT, " +
            "NOMBRE TEXT, " +
            "APELLIDO TEXT, " +
            "CARGO TEXT, " +
            "EMPRESA TEXT, " +
            "MOTIVO_RECHAZO TEXT, " +
            "TIPO_CAPTURA TEXT, " +
            "NOMBRE_DISPOSITIVO TEXT, " +
            "FECHA_CAPTURA TEXT)";
    public static final String CREAR_TABLA_ESPECIALES = "CREATE TABLE especiales(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                "ID_TIPO_PASE TEXT, " +
                                                                                "ID_PASE TEXT, " +
                                                                                "ID_LISTADO, " +
                                                                                "RUT TEXT, " +
                                                                                "NOMBRE TEXT, " +
                                                                                "APELLIDO TEXT, " +
                                                                                "CARGO TEXT, " +
                                                                                "EMPRESA TEXT, " +
                                                                                "PATENTE TEXT, " +
                                                                                "FECHA_PASE_INICIO TEXT, " +
                                                                                "ESTADO TEXT)";
    public static final String CREAR_TABLA_ESPECIALES_CAPTURADOS = "CREATE TABLE especialesCapturados(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                "ID_TIPO_PASE TEXT, " +
                                                                                "ID_PASE TEXT, " +
                                                                                "ID_LISTADO TEXT, " +
                                                                                "RUT TEXT, " +
                                                                                "NOMBRE TEXT, " +
                                                                                "APELLIDO TEXT, " +
                                                                                "CARGO TEXT, " +
                                                                                "EMPRESA TEXT, " +
                                                                                "PATENTE TEXT, " +
                                                                                "NOMBRE_DISPOSITIVO TEXT, " +
                                                                                "FECHA_CAPTURA TEXT)";
    public static final String CREAR_TABLA_ESPECIALES_RECHAZADO = "CREATE TABLE especialesRechazado(id_grupo INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID_PASE TEXT, " +
            "ID_TIPO_PASE TEXT, " +
            "MOTIVO_RECHAZO TEXT, " +
            "tipo_captura TEXT, " +
            "nombre_dispositivo TEXT, " +
            "FECHA_CAPTURA TEXT)";
    public static final String CREAR_TABLA_LICENCIAS = "CREATE TABLE licencias(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                "RUT TEXT, " +
                                                                                "NOMBRE TEXT, " +
                                                                                "APELLIDO TEXT, " +
                                                                                "LST_LICENCIA TEXT, " +
                                                                                "FECHA_VENCIMIENTO TEXT, " +
                                                                                "FECHA_RESTRICCION TEXT, " +
                                                                                "OBSERVACION TEXT, " +
                                                                                "FAENA TEXT)";
    public static final String CREAR_TABLA_LICENCIAS_CAPTURADAS = "CREATE TABLE licenciasCapturadas(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                                    "RUT TEXT, " +
                                                                                                    "NOMBRE TEXT, " +
                                                                                                    "APELLIDO TEXT, " +
                                                                                                    "ESTADO_CAPTURA TEXT, " +
                                                                                                    "FECHA_CAPTURA TEXT, " +
                                                                                                    "NOMBRE_DISPOSITIVO TEXT)";
    public static final String CREAR_TABLA_LICENCIAS_RECHAZADO = "CREATE TABLE licenciasRechazado(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "RUT TEXT, " +
            "LST_LICENCIA TEXT, " +
            "MOTIVO_RECHAZO TEXT, " +
            "tipo_captura TEXT, " +
            "nombre_dispositivo TEXT, " +
            "FECHA_CAPTURA TEXT)";
    public static final String CREAR_TABLA_SINCRONIZACION = "CREATE TABLE sincronizacion(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                        "estado TEXT)";

    public static final String CREAR_TABLA_TIPOVEHICULOS = "CREATE TABLE tipoVehiculos(id INTEGER PRIMARY KEY, " +
                                                                                        "vehiculo TEXT)";

    public static final String CREAR_TABLA_TIPOLICENCIA = "CREATE TABLE tipoLicencia(id INTEGER PRIMARY KEY, " +
                                                                                        "descripcion TEXT)";

    public static final String CREAR_TABLA_VEHICULOS = "CREATE TABLE vehiculos(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                                    "ID_VEHICULO INTEGER, "+
                                                                                    "marca TEXT, "+
                                                                                    "patente TEXT, " +
                                                                                    "year TEXT, " +
                                                                                    "modelo TEXT, " +
                                                                                    "id_estado TEXT, " +
                                                                                    "id_empresa TEXT)";
    public static final String CREAR_TABLA_VEHICULOS_CAPTURADAS = "CREATE TABLE vehiculosCapturadas(id_grupo INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_vehiculo TEXT, " +
            "patente TEXT, " +
            "tipo_captura TEXT, " +
            "nombre_dispositivo TEXT, " +
            "fecha TEXT)";
    public static final String CREAR_TABLA_VEHICULOS_RECHAZADO = "CREATE TABLE vehiculosRechazado(id_grupo INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_vehiculo TEXT, " +
            "patente TEXT, " +
            "id_empresa TEXT, " +
            "MOTIVO_RECHAZO TEXT, " +
            "tipo_captura TEXT, " +
            "nombre_dispositivo TEXT, " +
            "FECHA_CAPTURA TEXT)";
    public static final String CREAR_TABLA_PASAJEROS_CAPTURADOS = "CREATE TABLE pasajerosCapturadas(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_grupo INTEGER, " +
            "rut TEXT, " +
            "id_persona TEXT, " +
            "estado_acre TEXT, " +
            "tipo TEXT, " +
            "tipo_captura TEXT, " +
            "fecha TEXT)";
    public static final String CREAR_TABLA_VISITA_TECNICA = "CREATE TABLE visitaTecnica(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID_REGISTRO_VISITA INTEGER, " +
            "RUT_EMPRESA TEXT, " +
            "NOMBRE_EMPRESA TEXT, " +
            "FECHA_PASE_INICIO TEXT, " +
            "FECHA_PASE_TERMINO TEXT, " +
            "RUT_PASE TEXT, " +
            "NOMBRE_PASE TEXT)";
    public static final String CREAR_TABLA_VISITA_ADMINIS = "CREATE TABLE visitaAdminis(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID_REGISTRO_VISITA INTEGER, " +
            "RUT_EMPRESA TEXT, " +
            "NOMBRE_EMPRESA TEXT, " +
            "FECHA_PASE_INICIO TEXT, " +
            "FECHA_PASE_TERMINO TEXT, " +
            "RUT_PASE TEXT, " +
            "NOMBRE_PASE TEXT)";
    public static final String CREAR_TABLA_VISITA_ADMINIS_CAP = "CREATE TABLE visitaAdminisCap(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID_REGISTRO_VISITA INTEGER, " +
            "RUT_EMPRESA TEXT, " +
            "NOMBRE_EMPRESA TEXT, " +
            "FECHA_PASE_INICIO TEXT, " +
            "FECHA_PASE_TERMINO TEXT, " +
            "RUT_PASE TEXT, " +
            "NOMBRE_PASE TEXT)";
    public static final String CREAR_TABLA_VISITA_TECNICA_CAP = "CREATE TABLE visitaTecnicaCap(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID_REGISTRO_VISITA INTEGER, " +
            "TIPO_REGISTRO INTEGER, " +
            "NOMBRE_DISPOSITIVO TEXT, " +
            "FECHA_CAPTURA TEXT)";
    public static final String CREAR_TABLA_PASE_VISITA_RECHAZADO = "CREATE TABLE paseVisitaRechazado(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID_PASE TEXT, " +
            "ID_TIPO_PASE TEXT, " +
            "MOTIVO_RECHAZO TEXT, " +
            "ID_TIPO_ACCESO TEXT, " +
            "nombre_dispositivo TEXT, " +
            "FECHA_CAPTURA TEXT)";

    public static boolean fecha1MayorQueFecha2(String fecha1, String fecha2){
        boolean resultado = false;
        String year1, month1, day1, year2, month2, day2, f1, f2;
        int n1, n2;
        //0123456789
        //2021-12-31
        year1 = fecha1.substring(0, 4);
        month1 = fecha1.substring(5, 7);
        day1 = fecha1.substring(8, 10);
        year2 = fecha2.substring(0, 4);
        month2 = fecha2.substring(5, 7);
        day2 = fecha2.substring(8, 10);
        f1 = year1 + month1 + day1;
        f2 = year2 + month2 + day2;
        n1 = Integer.parseInt(f1);
        n2 = Integer.parseInt(f2);
        resultado = n1 > n2;
        return resultado;
    }

    public static String ifN(String dato){
        if(dato != null){
            if(!dato.isEmpty()) {
                if (!dato.trim().equals("")) {
                    return dato;
                }else{
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
    public static String ifN(String dato, String isNull){
        if(dato != null){
            if(!dato.isEmpty()) {
                if (!dato.trim().equals("")) {
                    return dato;
                }else{
                    return isNull;
                }
            } else {
                return isNull;
            }
        } else {
            return isNull;
        }
    }
    public static String ifN(String dato, String isNull, String notNull){
        if(dato != null){
            if(!dato.isEmpty()) {
                if (!dato.trim().equals("")) {
                    return ((notNull.equals(VRCHR))?"'" + dato + "'":notNull);
                }else{
                    return isNull;
                }
            } else {
                return isNull;
            }
        } else {
            return isNull;
        }
    }

    public static String fechaHoy(){
        String sDate = "";
        try{

            Calendar c = Calendar.getInstance();

            sDate = rellenarCeros(c.get(Calendar.DAY_OF_MONTH)) + "-" +
                    rellenarCeros(c.get(Calendar.MONTH) + 1) + "-" +
                    c.get(Calendar.YEAR);

        }catch(Exception e) {}
        return sDate;
    }
    public static String TIMESTAMP(){
        String sDate = "";
        try{
            Calendar c = Calendar.getInstance();

            sDate = c.get(Calendar.YEAR) + "-"
                    + rellenarCeros(c.get(Calendar.MONTH) + 1)
                    + "-" + rellenarCeros(c.get(Calendar.DAY_OF_MONTH))
                    + " " + rellenarCeros(c.get(Calendar.HOUR_OF_DAY))
                    + ":" + rellenarCeros(c.get(Calendar.MINUTE))
                    + ":" + rellenarCeros(c.get(Calendar.SECOND));
        }catch(Exception e) {}
        return sDate;
    }
    public static String TIMESTAMPdate(){
        String sDate = "";
        try{
            Calendar c = Calendar.getInstance();

            sDate = c.get(Calendar.YEAR)
                    + "-" + rellenarCeros(c.get(Calendar.MONTH) + 1)
                    + "-" + rellenarCeros(c.get(Calendar.DAY_OF_MONTH));
        }catch(Exception e) {}
        return sDate;
    }
    public static String generarAppID(){
        String fecha = "";
        try{
            Calendar c = Calendar.getInstance();

            fecha = c.get(Calendar.YEAR)
                    + rellenarCeros(c.get(Calendar.MONTH) + 1)
                    + rellenarCeros(c.get(Calendar.DAY_OF_MONTH))
                    + rellenarCeros(c.get(Calendar.HOUR_OF_DAY))
                    + rellenarCeros(c.get(Calendar.MINUTE))
                    + rellenarCeros(c.get(Calendar.SECOND));
        }catch(Exception e) {}
        return fecha;
    }
    private static String rellenarCeros(Integer dato){
        String resultado = "";
        try{
            String agregado = "0" + dato.toString();
            resultado = agregado.substring(agregado.length() - 2);
        }catch (Exception e){}
        return resultado;
    }
    public static String TIMESTAMP2SPA(){
        String resultado = TIMESTAMP();
        //0000000000111111111
        //0123456789012345678
        //2020-12-31 13:54:00
        return resultado.substring(8, 10) +
                "/" +
                resultado.substring(5, 7) +
                "/" +
                resultado.substring(0, 4) +
                resultado.substring(10, 19);
    }

    public static String date2spa(String date){
        String resultado = "";
        try {
            resultado = date.substring(8, 10) + "/" + resultado.substring(5, 7) + "/" + resultado.substring(0, 4);
            //0000000000111111111
            //0123456789012345678
            //2020-12-31 13:54:00
        }catch (Exception e){ }
        return resultado;
    }
}
