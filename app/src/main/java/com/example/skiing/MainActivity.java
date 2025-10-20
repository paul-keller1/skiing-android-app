package com.example.skiing;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingIcon; // Loading icon
    private ImageView startIcon;

    private final Handler handler = new Handler(Looper.getMainLooper());// Start icon
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadingIcon = findViewById(R.id.loading_icon);
        startIcon = findViewById(R.id.start_icon);

        loadingIcon.setVisibility(View.VISIBLE);
        startIcon.setVisibility(View.VISIBLE);

        startLoading();

    }

    private void startLoading() {
        // Disable the button and show the progress bar
        loadingIcon.setVisibility(View.VISIBLE);
        loadingIcon.setProgress(0);

        // Use a background thread to simulate work
        new Thread(new Runnable() {
            @Override
            public void run() {
                // This loop simulates the progress from 1 to 100
                int divide = 10;
                for (int i = 1; i <= 100*divide; i++) {
                    final int progress = i;

                    // Post updates to the UI thread using the handler
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingIcon.setProgress(progress/divide);
                        }
                    });

                    try {
                        // This delay simulates work being done, like in the 'delay(100)'
                        Thread.sleep(30/divide); // 30ms * 100 = 3 seconds total
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // After the loop finishes, re-enable the button on the UI thread

            }
        }).start(); // Start the new thread
    }





}

