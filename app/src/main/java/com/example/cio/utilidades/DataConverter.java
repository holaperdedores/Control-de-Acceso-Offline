package com.example.cio.utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class DataConverter {

    public static byte[] convertImage2ByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArray2Image(byte[] array){
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    public static String getStringImagen(Bitmap bmp){
        String encodedImage = Base64.encodeToString(convertImage2ByteArray(bmp), Base64.DEFAULT);
        return encodedImage;
    }

}
