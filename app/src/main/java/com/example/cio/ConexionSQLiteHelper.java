package com.example.cio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cio.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_CONFIGURACIONES);
        db.execSQL(Utilidades.CREAR_TABLA_PERSONAL);
        db.execSQL(Utilidades.CREAR_TABLA_PERSONAL_CAPTURADO);
        db.execSQL(Utilidades.CREAR_TABLA_ESPECIALES);
        db.execSQL(Utilidades.CREAR_TABLA_ESPECIALES_CAPTURADOS);
        db.execSQL(Utilidades.CREAR_TABLA_LICENCIAS);
        db.execSQL(Utilidades.CREAR_TABLA_LICENCIAS_CAPTURADAS);
        db.execSQL(Utilidades.CREAR_TABLA_SINCRONIZACION);
        db.execSQL(Utilidades.CREAR_TABLA_VEHICULOS);
        db.execSQL(Utilidades.CREAR_TABLA_VEHICULOS_CAPTURADAS);
        db.execSQL(Utilidades.CREAR_TABLA_PASAJEROS_CAPTURADOS);
        db.execSQL(Utilidades.CREAR_TABLA_VISITA_TECNICA);
        db.execSQL(Utilidades.CREAR_TABLA_VISITA_TECNICA_CAP);
        db.execSQL(Utilidades.CREAR_TABLA_VISITA_ADMINIS);
        db.execSQL(Utilidades.CREAR_TABLA_VISITA_ADMINIS_CAP);
        db.execSQL(Utilidades.CREAR_TABLA_PERSONAL_RECHAZADO);
        db.execSQL(Utilidades.CREAR_TABLA_VEHICULOS_RECHAZADO);
        db.execSQL(Utilidades.CREAR_TABLA_ESPECIALES_RECHAZADO);
        db.execSQL(Utilidades.CREAR_TABLA_PASE_VISITA_RECHAZADO);
        db.execSQL(Utilidades.CREAR_TABLA_LICENCIAS_RECHAZADO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS configuraciones");
        db.execSQL("DROP TABLE IF EXISTS personal");
        db.execSQL("DROP TABLE IF EXISTS personalCapturado");
        db.execSQL("DROP TABLE IF EXISTS personalRechazado");
        db.execSQL("DROP TABLE IF EXISTS vehiculos");
        db.execSQL("DROP TABLE IF EXISTS vehiculosCapturadas");
        db.execSQL("DROP TABLE IF EXISTS vehiculosRechazado");
        db.execSQL("DROP TABLE IF EXISTS pasajerosCapturadas");
        db.execSQL("DROP TABLE IF EXISTS paseVisitaRechazado");
        db.execSQL("DROP TABLE IF EXISTS visitaTecnica");
        db.execSQL("DROP TABLE IF EXISTS visitaTecnicaCap");
        db.execSQL("DROP TABLE IF EXISTS visitaAdminis");
        db.execSQL("DROP TABLE IF EXISTS visitaAdminisCap");
        db.execSQL("DROP TABLE IF EXISTS especiales");
        db.execSQL("DROP TABLE IF EXISTS especialesCapturados");
        db.execSQL("DROP TABLE IF EXISTS especialesRechazado");
        db.execSQL("DROP TABLE IF EXISTS licencias");
        db.execSQL("DROP TABLE IF EXISTS licenciasCapturadas");
        db.execSQL("DROP TABLE IF EXISTS licenciasRechazado");
        db.execSQL("DROP TABLE IF EXISTS sincronizacion");
        onCreate(db);
    }
}
