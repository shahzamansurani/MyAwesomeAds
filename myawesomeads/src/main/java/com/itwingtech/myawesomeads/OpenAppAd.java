package com.itwingtech.myawesomeads;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;


public class OpenAppAd {
    private static AppOpenAd appOpenAd = null;
    private static boolean isLoadingAd = false;
    private static boolean isShowingAd = false;

    private static long loadTime = 0;

    public OpenAppAd() {
    }

    public static void LoadOpenApp(Activity activity) {
        if (SharedPref.getIsAppOpen(activity) && SharedPref.getIsAds(activity) && Admob_Ads.isNetworkAvailable(activity)) {
            loadAd(activity);
        }
    }

    public static void loadAd(Context context) {
        // Do not load ad if there is an unused ad or one is already loading.
        if (isLoadingAd || isAdAvailable()) {
            return;
        }
        isLoadingAd = true;
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                context,
                SharedPref.getAdmobAppOpenKey(context),
                request,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd ad) {
                        appOpenAd = ad;
                        isLoadingAd = false;
                        loadTime = (new Date()).getTime();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        isLoadingAd = false;
                    }
                });
    }

    private static boolean wasLoadTimeLessThanNHoursAgo() {
        long dateDifference = (new Date()).getTime() - loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * (long) 4));
    }

    private static boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo();
    }


    public static void showAdIfAvailable(@NonNull Activity activity, @NonNull onAdShowed onAdShowed) {
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            return;
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable()) {
            onAdShowed.onAdShow();
            loadAd(activity);
            return;
        }
        appOpenAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Set the reference to null so isAdAvailable() returns false.
                        appOpenAd = null;
                        isShowingAd = false;
                        onAdShowed.onAdShow();
                        loadAd(activity);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        appOpenAd = null;
                        isShowingAd = false;
                        onAdShowed.onAdShow();
                        loadAd(activity);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }
                });

        isShowingAd = true;
        appOpenAd.show(activity);
    }

}


