package com.itwingtech.myawesomeads;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class AppOpenManager implements DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks {
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAds = false;
    private final Application myApplication;
    private Activity currentActivity;


    public AppOpenManager(Application myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        fetchAd();
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        if (SharedPref.getIsAppOpen(myApplication) && SharedPref.getIsAds(myApplication) && Admob_Ads.isNetworkAvailable(myApplication)) {
            showAdIfAvailable();
        }
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    public void onStart() {
//        showAdIfAvailable();
//    }

    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }
        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                AppOpenManager.this.appOpenAd = appOpenAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
//                Log.d("POENAD", "onAdFailedToLoad: " + loadAdError.getMessage());
            }
        };
        AdRequest adRequest = getAdRequest();
        AppOpenAd.load(
                myApplication,
                SharedPref.getAdmobAppOpenKey(myApplication), adRequest, loadCallback);

    }

    public void showAdIfAvailable() {
        if (!isShowingAds && isAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            isShowingAds = true;
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAds = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);
        } else
            fetchAd();
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdAvailable() {
        return appOpenAd != null;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;

    }
}
