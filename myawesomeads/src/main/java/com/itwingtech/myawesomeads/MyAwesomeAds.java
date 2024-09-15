package com.itwingtech.myawesomeads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
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

    // Default values
    private Boolean isAds = false;
    private Boolean isBanner = true;
    private Boolean isNative = true;
    private Boolean isInterstitial = true;
    private Boolean isAppOpen = true;
    private Boolean isRewarded = true;
    private String checkStart = "open";
    private String bannerKey = "ca-app-pub-3940256099942544/9214589741";
    private String interstitialKey = "ca-app-pub-3940256099942544/1033173712";
    private String nativeKey = "ca-app-pub-3940256099942544/1044960115";
    private String appOpenKey = "ca-app-pub-3940256099942544/9257395921";
    private String rewardedKey = "/6499/example/rewarded";
    private String oneSingleKey = "0ff9bd-1a9-467-81e1-e293";
    private int adInterval = 3;
    private int animationRaw = R.raw.loading;


    // Private constructor to prevent instantiation
    private MyAwesomeAds() {
    }

    // Method to get the singleton instance
    public static MyAwesomeAds getInstance() {
        if (instance == null) {
            instance = new MyAwesomeAds();
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


    public static void loadInitDatabase(Activity activity) {
        Admob_Ads.LoadFirebaseAdsData(activity, getApplicationName(activity));
    }

    public static void loadInitDatabase(Activity activity, String databaseName) {
        Admob_Ads.LoadFirebaseAdsData(activity, databaseName);
    }

    public static String getApplicationName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
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

    public static void loadAppOpenAd(Activity activity, onAdShowed onAdShowed) {
        if (SharedPref.getIsAds(activity) && SharedPref.getIsAppOpen(activity)) {
            OpenAppAd.showAdIfAvailable(activity, onAdShowed);
        }
    }

    public static void loadSplash(Activity activity, long DelayTime, onAdShowed onAdShowed) {
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

    }


    // Setters with return type of MyAwesomeAds for chaining
    public MyAwesomeAds setAds(Boolean ads) {
        isAds = ads;
        return this;
    }

    public MyAwesomeAds setBanner(Boolean banner) {
        isBanner = banner;
        return this;
    }

    public MyAwesomeAds setNative(Boolean aNative) {
        isNative = aNative;
        return this;
    }

    public MyAwesomeAds setInterstitial(Boolean interstitial) {
        isInterstitial = interstitial;
        return this;
    }

    public MyAwesomeAds setAppOpen(Boolean appOpen) {
        isAppOpen = appOpen;
        return this;
    }

    public MyAwesomeAds setRewarded(Boolean rewarded) {
        isRewarded = rewarded;
        return this;
    }

    public MyAwesomeAds setCheckStart(String checkStart) {
        this.checkStart = checkStart;
        return this;
    }

    public MyAwesomeAds setBannerKey(String bannerKey) {
        this.bannerKey = bannerKey;
        return this;
    }

    public MyAwesomeAds setInterstitialKey(String interstitialKey) {
        this.interstitialKey = interstitialKey;
        return this;
    }

    public MyAwesomeAds setNativeKey(String nativeKey) {
        this.nativeKey = nativeKey;
        return this;
    }

    public MyAwesomeAds setAppOpenKey(String appOpenKey) {
        this.appOpenKey = appOpenKey;
        return this;
    }

    public MyAwesomeAds setRewardedKey(String rewardedKey) {
        this.rewardedKey = rewardedKey;
        return this;
    }

    public MyAwesomeAds setOneSingleKey(String oneSingleKey) {
        this.oneSingleKey = oneSingleKey;
        return this;
    }

    public MyAwesomeAds setAdInterval(int adInterval) {
        this.adInterval = adInterval;
        return this;
    }

    public MyAwesomeAds setAnimationRaw(int animationRaw) {
        this.animationRaw = animationRaw;
        return this;
    }

    // Getter methods (unchanged)
    public Boolean getAds() {
        return isAds;
    }

    public Boolean getBanner() {
        return isBanner;
    }

    public Boolean getNative() {
        return isNative;
    }

    public Boolean getInterstitial() {
        return isInterstitial;
    }

    public Boolean getAppOpen() {
        return isAppOpen;
    }

    public Boolean getRewarded() {
        return isRewarded;
    }

    public String getCheckStart() {
        return checkStart;
    }

    public String getBannerKey() {
        return bannerKey;
    }

    public String getInterstitialKey() {
        return interstitialKey;
    }

    public String getNativeKey() {
        return nativeKey;
    }

    public String getAppOpenKey() {
        return appOpenKey;
    }

    public String getRewardedKey() {
        return rewardedKey;
    }

    public String getOneSingleKey() {
        return oneSingleKey;
    }

    public int getAdInterval() {
        return adInterval;
    }

    public int getAnimationRaw() {
        return animationRaw;
    }
}
