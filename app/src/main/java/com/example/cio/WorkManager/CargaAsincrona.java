package com.example.cio.WorkManager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.cio.ConexionSQLiteHelper;
import com.example.cio.utilidades.Descarga;

import java.util.concurrent.TimeUnit;

public class CargaAsincrona extends Worker {

    final  static String SYNC_DATA_WORK_NAME = "SINCRONIZAR_AUTOMATIC";
    public CargaAsincrona(
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
        new Descarga(conn,applicationContext,Integer.parseInt(tipo));
        PeriodicWorkRequest periodicSyncDataWork2 =
                new PeriodicWorkRequest.Builder(
                        UploadAsincrona.class,
                        60,
                        TimeUnit.MINUTES)
                        .addTag("SINCRONIZARBD")
                        .setInputData(createInputDataForClass())
                        .build();
        WorkManager
                .getInstance(getApplicationContext())
                .enqueueUniquePeriodicWork(
                        SYNC_DATA_WORK_NAME,
                        ExistingPeriodicWorkPolicy.KEEP,
                        periodicSyncDataWork2
                );
        return Result.success();
    }

    private Data createInputDataForClass() {
        Data.Builder builder = new Data.Builder();
        builder.putString("tipo", "1");
        return builder.build();
    }
}

