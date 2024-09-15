package com.itwingtech.myawesomeads;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    private static final String PREF_NAME = MyAwesomeAds.appName;
    private static final String ISADS = MyAwesomeAds.appName + "isadson";
    private static final String ISBANNERON = MyAwesomeAds.appName + "isbanneron";
    private static final String ISNATIVEON = MyAwesomeAds.appName + "isnativeon";
    private static final String ISINTERSTITALON = MyAwesomeAds.appName + "isinterstitialon";
    private static final String ISREWARDEDON = MyAwesomeAds.appName + "isrewardedon";
    private static final String ISAPPOPENON = MyAwesomeAds.appName + "isappopenon";

    private static final String CHECKSTART = MyAwesomeAds.appName + "checkstart";
    private static final String ADMOB_BANNER = MyAwesomeAds.appName + "admob_banner";
    private static final String ADMOB_INTERSTITIAL = MyAwesomeAds.appName + "admob_interstitial";
    private static final String ADMOB_NATIVE = MyAwesomeAds.appName + "admob_native";
    private static final String ADMOB_APPOPEN = MyAwesomeAds.appName + "admob_openapp";
    private static final String ADMOB_REWARDED = MyAwesomeAds.appName + "admob_rewarded";
    private static final String ONE_SINGLE = MyAwesomeAds.appName + "one_single";
    public static final String ADINTERVAL = MyAwesomeAds.appName + "ad_interval";
    public static final String DIALOGANIM = MyAwesomeAds.appName + "dialog_anim";


    //  <--------------------Ads Switch------------------------>
    public static void setIsAds(Context context, Boolean Isbanneron) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(ISADS, Isbanneron);
        editor.apply();
    }

    public static boolean getIsAds(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getBoolean(ISADS, MyAwesomeAds.getInstance().getAds());
    }


    public static void setIsBanner(Context context, Boolean IsBanner) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(ISBANNERON, IsBanner);
        editor.apply();
    }

    public static boolean getIsBanner(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getBoolean(ISBANNERON, MyAwesomeAds.getInstance().getBanner());
    }

    public static void setIsInterstitial(Context context, Boolean IsInterstitial) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(ISINTERSTITALON, IsInterstitial);
        editor.apply();
    }

    public static boolean getIsInterstitial(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getBoolean(ISINTERSTITALON, MyAwesomeAds.getInstance().getInterstitial());
    }

    public static void setIsNative(Context context, Boolean IsNative) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(ISNATIVEON, IsNative);
        editor.apply();
    }

    public static boolean getIsNative(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getBoolean(ISNATIVEON, MyAwesomeAds.getInstance().getNative());
    }

    public static void setIsRewarded(Context context, Boolean IsRewarded) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(ISREWARDEDON, IsRewarded);
        editor.apply();
    }

    public static boolean getIsRewarded(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getBoolean(ISREWARDEDON, MyAwesomeAds.getInstance().getRewarded());
    }

    public static void setIsAppOpen(Context context, Boolean setIsAppOpen) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(ISAPPOPENON, setIsAppOpen);
        editor.apply();
    }

    public static boolean getIsAppOpen(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getBoolean(ISAPPOPENON, MyAwesomeAds.getInstance().getAppOpen());
    }

    //  <--------------------Settings------------------------>
    public static String getCheckStart(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getString(CHECKSTART, MyAwesomeAds.getInstance().getCheckStart());
        //int
        //open
    }

    public static void setCheckStart(Context context, String CheckStart) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(CHECKSTART, CheckStart);
        editor.apply();
    }

    public static void setAdInterval(Context context, int setAdInterval) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt(ADINTERVAL, setAdInterval);
        editor.apply();
    }

    public static int getAdInterval(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getInt(ADINTERVAL, MyAwesomeAds.getInstance().getAdInterval());
    }

    public static void setOneSingleKey(Context context, String OneSingleKey) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ONE_SINGLE, OneSingleKey);
        editor.apply();
    }

    public static String getOneSingleKey(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getString(ONE_SINGLE, MyAwesomeAds.getInstance().getOneSingleKey());
    }


    //  <--------------------Ads IDS------------------------>
    public static void setAdmobBannerKey(Context context, String AdmobBannerId) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ADMOB_BANNER, AdmobBannerId);
        editor.apply();
    }

    public static String getAdmobBannerKey(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getString(ADMOB_BANNER, MyAwesomeAds.getInstance().getBannerKey());
        //ca-app-pub-3940256099942544/9214589741
    }

    public static void setAdmobInterstitialKey(Context context, String setAdmobInterstitialKey) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ADMOB_INTERSTITIAL, setAdmobInterstitialKey);
        editor.apply();
    }

    public static String getsetAdmobInterstitialKey(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getString(ADMOB_INTERSTITIAL, MyAwesomeAds.getInstance().getInterstitialKey());
        //ca-app-pub-3940256099942544/1033173712
    }

    public static void setAdmobNativeKey(Context context, String AdmobNativeKey) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ADMOB_NATIVE, AdmobNativeKey);
        editor.apply();
    }

    public static String getAdmobNativeKey(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getString(ADMOB_NATIVE, MyAwesomeAds.getInstance().getNativeKey());
        //ca-app-pub-3940256099942544/2247696110
        //ca-app-pub-3940256099942544/1044960115
    }

    public static void setAdmobRewardedKey(Context context, String AdmobRewardedKey) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ADMOB_REWARDED, AdmobRewardedKey);
        editor.apply();
    }

    public static String getAdmobRewardedKey(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getString(ADMOB_REWARDED, MyAwesomeAds.getInstance().getRewardedKey());
        //ca-app-pub-3940256099942544/5354046379
    }

    public static void setAdmobAppOpenKey(Context context, String AdmobAppOpenKey) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ADMOB_APPOPEN, AdmobAppOpenKey);
        editor.apply();
    }

    public static String getAdmobAppOpenKey(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getString(ADMOB_APPOPEN, MyAwesomeAds.getInstance().getAppOpenKey());
    }


    public static void setAnimation(Context context, int animation) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt(DIALOGANIM, animation);
        editor.apply();
    }

    public static int getAnimation(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return pref.getInt(DIALOGANIM, MyAwesomeAds.getInstance().getAnimationRaw());
    }
}
