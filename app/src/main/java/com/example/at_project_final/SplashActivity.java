package com.example.at_project_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1500; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize fade-in animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Apply animation to ImageView
        ImageView imageViewSplash = findViewById(R.id.imageViewSplash);
        imageViewSplash.startAnimation(fadeIn);

        // Start MainActivity after delay
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
