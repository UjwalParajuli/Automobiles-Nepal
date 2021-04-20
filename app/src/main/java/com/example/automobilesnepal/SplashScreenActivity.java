package com.example.automobilesnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.automobilesnepal.utils.SharedPrefManager;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private Button button_get_started;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        button_get_started = findViewById(R.id.button_get_started);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    button_get_started.setVisibility(View.VISIBLE);
                    button_get_started.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SplashScreenActivity.this, SignUpActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }


            }
        },SPLASH_TIME_OUT);

    }
}