package com.itwingtech.myapplication;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.itwingtech.myawesomeads.MyAwesomeAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAwesomeAds.init(this);

    }
}