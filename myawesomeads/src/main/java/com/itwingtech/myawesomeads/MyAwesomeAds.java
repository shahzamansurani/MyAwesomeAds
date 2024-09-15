package com.itwingtech.myawesomeads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.Continue;
import com.onesignal.OneSignal;

public class MyAwesomeAds {
    public static String appName = "PrefName";
    private static MyAwesomeAds instance;
    private final Context context;


    // Private constructor to enforce singleton pattern
    private MyAwesomeAds(AppCompatActivity activity) {
        this.context = activity.getApplicationContext();
    }

    public static MyAwesomeAds getInstance(AppCompatActivity activity) {
        if (instance == null) {
            instance = new MyAwesomeAds(activity);
            init(activity);
        }
        return instance;
    }

    public static void initApp(Application application) {
        if (SharedPref.getIsAds(application) && SharedPref.getIsAppOpen(application)) {
            new AppOpenManager(application);
        }
    }

    public static void init(AppCompatActivity activity) {
        appName = activity.getPackageName();
        OpenAppAd.LoadOpenApp(activity);
        FirebaseApp.initializeApp(activity);
        FirebaseAnalytics.getInstance(activity);
        new Thread(() -> MobileAds.initialize(activity, initializationStatus -> {
        })).start();
        OneSignal.initWithContext(activity, SharedPref.getOneSingleKey(activity));
        OneSignal.getNotifications().requestPermission(true, Continue.with(r -> {
        }));
        inAppUpdate(activity);
    }

    public static void inAppUpdate(AppCompatActivity activity) {
        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("TAG", "Update Success");
                    } else {
                        Log.d("TAG", "Update Cancelled");
                    }
                }
        );
        InAppUpdateManager.init(activity, activityResultLauncher);
    }

    public MyAwesomeAds setDatabase(Activity activity) {
        Admob_Ads.LoadFirebaseAdsData(activity, getApplicationName(activity));
        return this; // Return the current instance for chaining
    }


    public MyAwesomeAds setDatabase(Activity activity, String databaseName) {
        Admob_Ads.LoadFirebaseAdsData(activity, databaseName);
        return this; // Return the current instance for chaining
    }


    public static String getApplicationName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }


    public MyAwesomeAds loadInterstitialLimit(Activity activity, onAdShowed onAdShowed) {
        Admob_Ads.CheckInitLimit(activity, onAdShowed);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds loadInterstitialWithoutLimit(Activity activity, onAdShowed onAdShowed) {
        Admob_Ads.CheckInitWithoutLimit(activity, onAdShowed);
        return this; // Return the current instance for chaining
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


    public MyAwesomeAds loadRewardedAd(Activity activity, onAdShowed onAdShowed) {
        Admob_Ads.showRewardedDialog(activity, onAdShowed);
        return this; // Return the current instance for chaining
    }



    public MyAwesomeAds loadAppOpenAd(Activity activity, onAdShowed onAdShowed) {
        if (SharedPref.getIsAds(activity) && SharedPref.getIsAppOpen(activity)) {
            OpenAppAd.showAdIfAvailable(activity, onAdShowed);
        }
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds loadSplash(Activity activity, long DelayTime, onAdShowed onAdShowed) {
        new CountDownTimer(DelayTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (SharedPref.getIsAds(activity) && SharedPref.getIsAppOpen(activity) && SharedPref.getCheckStart(activity).equals("open")) {
                    OpenAppAd.showAdIfAvailable(activity, onAdShowed);
                } else if (SharedPref.getIsAds(activity) && SharedPref.getCheckStart(activity).equals("int")) {
                    Admob_Ads.LoadInterstitial(activity, onAdShowed);
                } else {
                    onAdShowed.onAdShow();
                }
            }
        }.start();
        return this; // Return the current instance for chaining
    }

    // Setters for ad states
    public MyAwesomeAds setAds(boolean isAdsOn) {
        SharedPref.setIsAds(context, isAdsOn);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setIsBanner(boolean isBanner) {
        SharedPref.setIsBanner(context, isBanner);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setIsInterstitial(boolean isInterstitial) {
        SharedPref.setIsInterstitial(context, isInterstitial);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setIsNative(boolean isNative) {
        SharedPref.setIsNative(context, isNative);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setIsRewarded(boolean isRewarded) {
        SharedPref.setIsRewarded(context, isRewarded);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setIsAppOpen(boolean isAppOpen) {
        SharedPref.setIsAppOpen(context, isAppOpen);
        return this; // Return the current instance for chaining
    }

    // Setters for AdMob keys
    public MyAwesomeAds setAdmobBannerKey(String bannerId) {
        SharedPref.setAdmobBannerKey(context, bannerId);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setAdmobInterstitialKey(String interstitialId) {
        SharedPref.setAdmobInterstitialKey(context, interstitialId);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setAdmobNativeKey(String nativeId) {
        SharedPref.setAdmobNativeKey(context, nativeId);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setAdmobRewardedKey(String rewardedId) {
        SharedPref.setAdmobRewardedKey(context, rewardedId);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setAdmobAppOpenKey(String appOpenId) {
        SharedPref.setAdmobAppOpenKey(context, appOpenId);
        return this; // Return the current instance for chaining
    }

    // Setters for other configuration options
    public MyAwesomeAds setAdInterval(int interval) {
        SharedPref.setAdInterval(context, interval);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setCheckStart(String checkStart) {
        SharedPref.setCheckStart(context, checkStart);
        return this; // Return the current instance for chaining
    }

    public MyAwesomeAds setAnimation(int animation) {
        SharedPref.setAnimation(context, animation);
        return this; // Return the current instance for chaining
    }
}
