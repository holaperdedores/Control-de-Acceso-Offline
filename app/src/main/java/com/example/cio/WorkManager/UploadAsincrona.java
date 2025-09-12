package com.example.cio.WorkManager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.cio.ConexionSQLiteHelper;
import com.example.cio.utilidades.Cargar;
import com.example.cio.utilidades.Descarga;

public class UploadAsincrona extends Worker {

    public UploadAsincrona(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();
        ConexionSQLiteHelper conn;
        conn = new ConexionSQLiteHelper(getApplicationContext(), "DB_CIO", null, 3);
        String tipo = getInputData().getString("tipo");
        assert tipo != null;
        new Cargar(conn,applicationContext,Integer.parseInt(tipo));
        return Result.success();
    }
}

