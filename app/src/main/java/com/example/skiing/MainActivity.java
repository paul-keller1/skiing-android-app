package com.example.skiing;

import android.content.Intent;
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
        loadingIcon.setProgress(0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int divide = 10;
                    for (int i = 1; i <= 100; i++) {
                        final int progress = i;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                loadingIcon.setProgress(progress);
                            }
                        });

                        Thread.sleep(3);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);

                            finish();
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}

