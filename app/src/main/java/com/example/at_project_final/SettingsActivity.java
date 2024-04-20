package com.example.at_project_final;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView textSizeLabel;
    private SeekBar textSizeSeekBar;
    private TextView textColorLabel;
    private SeekBar textColorSeekBar;
    private View backgroundView;
    private SeekBar backgroundSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Retrieve references to UI elements
        textSizeLabel = findViewById(R.id.textSizeLabel);
        textSizeSeekBar = findViewById(R.id.textSizeSeekBar);
        textColorLabel = findViewById(R.id.textColorLabel);
        textColorSeekBar = findViewById(R.id.textColorSeekBar);
        backgroundView = findViewById(R.id.backgroundContrastLabel);
        backgroundSeekBar = findViewById(R.id.backgroundContrastSeekBar);

        // Restore SeekBar positions to the last adjusted values
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        textSizeSeekBar.setProgress(sharedPreferences.getInt("textSize", 50));
        textColorSeekBar.setProgress(sharedPreferences.getInt("textColor", 127));
        backgroundSeekBar.setProgress(sharedPreferences.getInt("background", 0));

        // Set up listeners for SeekBars
        textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Apply selected text size to textSizeLabel
                textSizeLabel.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });

        textColorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Apply selected text color to textColorLabel
                textColorLabel.setTextColor(Color.rgb(255 - progress, 255 - progress, 255 - progress)); // Invert the color
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });

        backgroundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Interpolate between black and white based on the progress
                int interpolatedColor = interpolateColor(Color.BLACK, Color.WHITE, progress / 100f);

                // Set the background color
                backgroundView.setBackgroundColor(interpolatedColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });

        Button btnApply = findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });
    }

    private void applyChanges() {
        // Apply changes to the settings
        int textSize = textSizeSeekBar.getProgress();
        int textColor = textColorSeekBar.getProgress();
        int background = backgroundSeekBar.getProgress();

        // Update default settings
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("textSize", textSize);
        editor.putInt("textColor", textColor);
        editor.putInt("background", background);
        editor.apply();

        // Apply changes to the UI immediately
        textSizeLabel.setTextSize(textSize);
        textColorLabel.setTextColor(Color.rgb(255 - textColor, 255 - textColor, 255 - textColor));
        int interpolatedColor = interpolateColor(Color.BLACK, Color.WHITE, background / 100f);
        backgroundView.setBackgroundColor(interpolatedColor);

        // Show a message to indicate that changes have been applied
        Toast.makeText(SettingsActivity.this, "Changes applied", Toast.LENGTH_SHORT).show();
    }


    private int interpolateColor(int colorStart, int colorEnd, float ratio) {
        float[] hsvStart = new float[3];
        float[] hsvEnd = new float[3];
        float[] hsvResult = new float[3];
        Color.RGBToHSV(Color.red(colorStart), Color.green(colorStart), Color.blue(colorStart), hsvStart);
        Color.RGBToHSV(Color.red(colorEnd), Color.green(colorEnd), Color.blue(colorEnd), hsvEnd);
        for (int i = 0; i < 3; i++) {
            // Interpolate each component separately
            hsvResult[i] = interpolate(hsvStart[i], hsvEnd[i], ratio);
        }
        return Color.HSVToColor(hsvResult);
    }

    private float interpolate(float a, float b, float ratio) {
        return a * (1 - ratio) + b * ratio;
    }
}
