package com.example.at_project_final;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DisplayImageActivity extends AppCompatActivity {

    private String extractedText;
    private TextToSpeech textToSpeech;
    private Button btnStartTextToSpeech;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int defaultTextSize = sharedPreferences.getInt("textSize", 50);

        extractedText = getIntent().getStringExtra("extractedText");
        TextView textView = findViewById(R.id.textView);
        btnStartTextToSpeech = findViewById(R.id.btnStartTextToSpeech);
        btnStop = findViewById(R.id.btnStop);

        textView.setText(extractedText);
        textView.setTextSize(defaultTextSize);

        btnStartTextToSpeech.setOnClickListener(v -> startTextToSpeech());
        btnStop.setOnClickListener(v -> stopTextToSpeech());
    }

    private void startTextToSpeech() {
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.speak(extractedText, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    private void stopTextToSpeech() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
