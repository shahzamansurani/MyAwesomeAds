package com.itwingtech.myawesomeads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.CountDownTimer;

public class MyAwesomeAds {
    public static String appName = "PrefName";

    public static void initializeApp(Application application) {
        Admob_Ads.InitApp(application);
    }

    public static void initialize(Activity activity) {
        appName = activity.getPackageName();
        OpenAppAd.LoadOpenApp(activity);
    }

    public static void dialogAnimation(Activity activity, int animationRaw) {
        SharedPref.setAnimation(activity, animationRaw);
    }

    public static void initializeDatabase(Activity activity, String databaseName) {
        Admob_Ads.LoadFirebaseAdsData(activity, databaseName);
    }

    public static void initializeDatabase(Activity activity) {
        Admob_Ads.LoadFirebaseAdsData(activity, getApplicationName(activity));
    }

    public static String getApplicationName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static void initializeDefValues(Boolean isAds, Boolean isBanner, Boolean isNative, Boolean isInterstitial, Boolean isAppOpen, Boolean isRewarded, String checkStart, String bannerKey, String interstitialKey, String nativeKey, String appOpenKey, String rewardedKey, String oneSingleKey, int adInterval) {
        new defValues(isAds, isBanner, isNative, isInterstitial, isAppOpen, isRewarded, checkStart, bannerKey, interstitialKey, nativeKey, appOpenKey, rewardedKey, oneSingleKey, adInterval);
    }

    public static void loadInterstitialLimit(Activity activity, onAdShowed onAdShowed) {
        Admob_Ads.CheckInitLimit(activity, onAdShowed);
    }

    public static void loadInterstitialWithoutLimit(Activity activity, onAdShowed onAdShowed) {
        Admob_Ads.CheckInitWithoutLimit(activity, onAdShowed);
    }

    public static void loadCollapsableBanner(Activity activity, MyCollapsableBannerView myAdaptiveBannerView, String top_bottom) {
        Admob_Ads.CheckCollapsableBanner(activity, myAdaptiveBannerView, top_bottom);
    }

    public static void loadAdaptiveBanner(Activity activity, MyAdaptiveBannerView myAdaptiveBannerView) {
        Admob_Ads.CheckAdaptiveBanner(activity, myAdaptiveBannerView);
    }

    public static void loadNativeLarge(Activity activity, MyNativeLarge myNativeLarge) {
        Admob_Ads.CheckNativeLarge(activity, myNativeLarge);
    }

    public static void loadNativeSmall(Activity activity, MyNativeSmall myNativeSmall) {
        Admob_Ads.CheckNativeSmall(activity, myNativeSmall);
    }

    public static void loadRewarded(Activity activity, onAdShowed onAdShowed) {
        Admob_Ads.showRewardedDialog(activity, onAdShowed);
    }

    public static void loadOpenApp(Application application) {
        new AppOpenManager(application);
    }

    public static void loadSplash(Activity activity, long DelayTime, onAdShowed onAdShowed) {
        new CountDownTimer(DelayTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (SharedPref.getIsAds(activity) && SharedPref.getCheckStart(activity).equals("open")) {
                    OpenAppAd.showAdIfAvailable(activity, onAdShowed);
                } else if (SharedPref.getIsAds(activity) && SharedPref.getCheckStart(activity).equals("int")) {
                    Admob_Ads.LoadInterstitial(activity, onAdShowed);
                } else {
                    onAdShowed.onAdShow();
                }
            }
        }.start();

    }

}
