package com.example.cio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Alerta extends AppCompatActivity {

    ConstraintLayout constraintLayout1;
    ImageView imgSuccessIcon,
            imgWarningIcon,
            imgDangerIcon;
    TextView lblMensaje;

    String alerta_mensaje,
            alerta_class,
            alerta_time = "estatico";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);

        constraintLayout1 = findViewById(R.id.contraintLayout1);
        imgSuccessIcon = findViewById(R.id.imgSuccessIcon);
        imgWarningIcon = findViewById(R.id.imgWarningIcon);
        imgDangerIcon = findViewById(R.id.imgDangerIcon);
        lblMensaje = findViewById(R.id.lblMensaje);

        imgSuccessIcon.setVisibility(View.INVISIBLE);
        imgWarningIcon.setVisibility(View.INVISIBLE);
        imgDangerIcon.setVisibility(View.INVISIBLE);

        try {
            Bundle miBundle = this.getIntent().getExtras();

            if (miBundle != null) {
                alerta_mensaje = miBundle.getString("mensaje");
                alerta_class = miBundle.getString("class");
                try {
                    alerta_time = miBundle.getString("time");
                } catch (Exception e) {
                }

                lblMensaje.setText(alerta_mensaje);

                switch (alerta_class) {
                    case "success":
                        constraintLayout1.setBackgroundColor(getResources().getColor(R.color.green_success));
                        imgSuccessIcon.setVisibility(View.VISIBLE);
                        break;
                    case "warning":
                        constraintLayout1.setBackgroundColor(getResources().getColor(R.color.yellow_warning));
                        imgWarningIcon.setVisibility(View.VISIBLE);
                        break;
                    case "danger":
                        constraintLayout1.setBackgroundColor(getResources().getColor(R.color.red_danger));
                        imgDangerIcon.setVisibility(View.VISIBLE);
                        break;
                }
            }
            if(alerta_time != null) {
                if (!(alerta_time.equals("estatico"))) {
                    int timeout = 4000;
                    if (alerta_time.equals("short")) timeout = 1500;
                    new CountDownTimer(timeout, timeout) {
                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            finish();
                        }
                    }.start();
                }
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onclickVolver(View view) {
        finish();
    }
}