package com.example.cio.WorkManager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.cio.ConexionSQLiteHelper;
import com.example.cio.utilidades.CargarDescarga;

public class CargaAsincrona extends Worker {

    int clase = 1;
    public CargaAsincrona(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(getApplicationContext(), "DB_CIO", null, 1);
        String tipo = getInputData().getString("tipo");
        assert tipo != null;
        new CargarDescarga(conn,applicationContext,Integer.parseInt(tipo));
        return Result.success();
    }
}

