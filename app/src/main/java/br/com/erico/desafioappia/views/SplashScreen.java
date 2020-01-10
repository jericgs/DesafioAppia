package br.com.erico.desafioappia.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import br.com.erico.desafioappia.MainActivity;
import br.com.erico.desafioappia.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                screens();
            }
        }, 2000);

    }

    public void screens(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}