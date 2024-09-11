package com.itwingtech.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.itwingtech.myawesomeads.MyAwesomeAds;
import com.itwingtech.myawesomeads.onAdShowed;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAwesomeAds.initialize(this);
        MyAwesomeAds.loadSplash(this, 3000, () -> {
            Toast.makeText(this, "Splash Working", Toast.LENGTH_SHORT).show();
        });
    }
}