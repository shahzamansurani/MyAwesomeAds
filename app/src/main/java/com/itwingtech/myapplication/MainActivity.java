package com.itwingtech.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itwingtech.myawesomeads.MyAwesomeAds;
import com.itwingtech.myawesomeads.onAdShowed;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MyAwesomeAds.init(this);
//        MyAwesomeAds.getInstance().setAppOpen(false);
    }

//    public void limit(View view) {
//        MyAwesomeAds.loadInterstitialLimit(MainActivity.this, new onAdShowed() {
//            @Override
//            public void onAdShow() {
//                Toast.makeText(MainActivity.this, "Limit Interstitial Showed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void nolimit(View view) {
//        MyAwesomeAds.loadInterstitialWithoutLimit(MainActivity.this, new onAdShowed() {
//            @Override
//            public void onAdShow() {
//                Toast.makeText(MainActivity.this, "NO Limit Interstitial Showed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void rewarded(View view) {
//        MyAwesomeAds.loadRewarded(MainActivity.this, new onAdShowed() {
//            @Override
//            public void onAdShow() {
//                Toast.makeText(MainActivity.this, "Rewarded Showed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void appopen(View view) {
//        MyAwesomeAds.loadSplash(MainActivity.this, 1000, new onAdShowed() {
//            @Override
//            public void onAdShow() {
//                Toast.makeText(MainActivity.this, "Appopen Showed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}