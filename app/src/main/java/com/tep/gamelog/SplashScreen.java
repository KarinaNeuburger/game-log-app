package com.tep.gamelog;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000; // Parâmetro que determina o tempo de exibição da tela de abertura

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash); // Atribui o layout para a tela de abertura
        // Executa a thread
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class); // Troca para a Main
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}