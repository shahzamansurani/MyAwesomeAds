package com.itwingtech.myawesomeads;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itwingtech.myawesomeads.databinding.DialogLoadingBinding;
import com.itwingtech.myawesomeads.databinding.WatchadDialogBinding;
import com.onesignal.Continue;
import com.onesignal.OneSignal;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * @noinspection ALL
 */

public class Admob_Ads {
    private static InterstitialAd interstitialAd;
    private static NativeAd mNativeAd;
    private static int limit = 0;
    private static AdView adView;
    private static boolean adShowed = false;
    private static Dialog loadingDialog;
    private static RewardedAd rewardedAd;


//    public void InitApp(Application application) {
//
//    }
//
//
//    public static void InitAdmob(Activity activity) {
//
//    }

    public static boolean isNetworkAvailable(Activity context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public static void CheckCollapsableBanner(Activity activity, MyCollapsableBannerView myAdaptiveBannerView, String top_bottom) {
        boolean check = SharedPref.getIsBanner(activity);
        boolean isAds = SharedPref.getIsAds(activity);
        if (isAds && check && isNetworkAvailable(activity) && !adShowed) {
            myAdaptiveBannerView.setVisibility(View.VISIBLE);
            LoadCollapsableBanner(activity, myAdaptiveBannerView, top_bottom);
        } else {
            myAdaptiveBannerView.setVisibility(View.GONE);
        }
    }

    public static void CheckAdaptiveBanner(Activity activity, MyAdaptiveBannerView myAdaptiveBannerView) {
        boolean check = SharedPref.getIsBanner(activity);
        boolean isAds = SharedPref.getIsAds(activity);
        if (isAds && check && isNetworkAvailable(activity) && !adShowed) {
            myAdaptiveBannerView.setVisibility(View.VISIBLE);
            LoadAdaptiveBanner(activity, myAdaptiveBannerView);
        } else {
            myAdaptiveBannerView.setVisibility(View.GONE);
        }
    }


    public static void CheckNativeLarge(Activity activity, MyNativeLarge myNativeLarge) {
        boolean check = SharedPref.getIsNative(activity);
        boolean isAds = SharedPref.getIsAds(activity);
        if (isAds && check && isNetworkAvailable(activity) && !adShowed) {
            myNativeLarge.setVisibility(View.VISIBLE);
            LoadNativeAdLarge(activity, myNativeLarge);
        } else {
            myNativeLarge.setVisibility(View.GONE);
        }
    }

    public static void CheckNativeSmall(Activity activity, MyNativeSmall myNativeSmall) {
        boolean check = SharedPref.getIsNative(activity);
        boolean isAds = SharedPref.getIsAds(activity);
        if (isAds && check && isNetworkAvailable(activity) && !adShowed) {
            myNativeSmall.setVisibility(View.VISIBLE);
            LoadNativeAdSmall(activity, myNativeSmall);
        } else {
            myNativeSmall.setVisibility(View.GONE);
        }
    }

    public static void CheckInitLimit(Activity activity, onAdShowed onAdShowed) {
        boolean check = SharedPref.getIsInterstitial(activity);
        boolean isAds = SharedPref.getIsAds(activity);
        if (isAds && check && isNetworkAvailable(activity) && limit % SharedPref.getAdInterval(activity) == 0) {
            LoadInterstitiallimit(activity, onAdShowed);
        } else {
            onAdShowed.onAdShow();
            adShowed = false;
            limit++;
        }
    }

    public static void CheckInitWithoutLimit(Activity activity, onAdShowed onAdShowed) {
        boolean check = SharedPref.getIsInterstitial(activity);
        boolean isAds = SharedPref.getIsAds(activity);
        if (isAds && check && isNetworkAvailable(activity)) {
            LoadInterstitial(activity, onAdShowed);
        } else {
            onAdShowed.onAdShow();
            adShowed = false;
        }
    }

    public static void LoadInterstitial(Activity activity, onAdShowed onAdShowed) {
        showDialog(activity);
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, SharedPref.getsetAdmobInterstitialKey(activity), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd minterstitialAd) {
                interstitialAd = minterstitialAd;
                interstitialAd.show(activity);
                adShowed = true;
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        interstitialAd = null;
                        onAdShowed.onAdShow();
                        closeDialog();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        interstitialAd = null;
                        onAdShowed.onAdShow();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        interstitialAd = null;
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                interstitialAd = null;
                closeDialog();
                onAdShowed.onAdShow();
                adShowed = false;

            }
        });

    }

    public static void LoadInterstitiallimit(Activity activity, onAdShowed onAdShowed) {
        showDialog(activity);
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, SharedPref.getsetAdmobInterstitialKey(activity), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd minterstitialAd) {
                interstitialAd = minterstitialAd;
                interstitialAd.show(activity);
                limit++;
                adShowed = true;
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        interstitialAd = null;
                        onAdShowed.onAdShow();
                        closeDialog();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        interstitialAd = null;
                        onAdShowed.onAdShow();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        interstitialAd = null;
                    }

                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                interstitialAd = null;
                closeDialog();
                onAdShowed.onAdShow();
                adShowed = false;
                limit++;
            }
        });
    }


    private static AdSize getAdSize(Activity mActivity) {
        // Get the width of the screen in pixels
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        // Calculate the ad width in dp
        int adWidth = (int) (widthPixels / density);

        // Return the adaptive banner size
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mActivity, adWidth);
    }

    public static void LoadCollapsableBanner(Activity mActivity, MyCollapsableBannerView adContainer, String top_bottom) {
        if (adView != null) {
            adView.destroy();
        }
        showDialog(mActivity);
        adContainer.setVisibility(View.VISIBLE);
        try {
            adView = new AdView(mActivity);
            adView.setAdUnitId(SharedPref.getAdmobBannerKey(mActivity));
            AdSize adSize = getAdSize(mActivity);
            adView.setAdSize(adSize);
            Bundle extras = new Bundle();
            extras.putString("collapsible", top_bottom);
            adView.loadAd(new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build());
            adContainer.addView(adView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                    adView = null;
                    adContainer.setVisibility(View.GONE);
                    closeDialog();
                }

                @Override
                public void onAdLoaded() {
                    closeDialog();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void LoadAdaptiveBanner(Activity mActivity, MyAdaptiveBannerView adContainer) {
        if (adView != null) {
            adView.destroy();
        }
        adContainer.setVisibility(View.VISIBLE);
        showDialog(mActivity);
        try {
            adView = new AdView(mActivity);
            adView.setAdUnitId(SharedPref.getAdmobBannerKey(mActivity));
            AdSize adSize = getAdSize(mActivity);
            adView.setAdSize(adSize);
            adView.loadAd(new AdRequest.Builder().build());
            adContainer.addView(adView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                    adView = null;
                    adContainer.setVisibility(View.GONE);
                    closeDialog();
                }

                @Override
                public void onAdLoaded() {
                    closeDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void LoadNativeAdLarge(Activity mActivity, MyNativeLarge frameLayout) {
        showDialog(mActivity);
        AdLoader adLoader = new AdLoader.Builder(mActivity, SharedPref.getAdmobNativeKey(mActivity)).forNativeAd(nativeAd -> {
            NativeAdView adView = (NativeAdView) mActivity.getLayoutInflater().inflate(R.layout.native_admob_large, null);
            if (mNativeAd != null) {
                mNativeAd.destroy();
            }
            populateNativeAdView(nativeAd, adView);
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                frameLayout.setVisibility(View.GONE);
                closeDialog();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                frameLayout.setVisibility(View.VISIBLE);
                closeDialog();
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void LoadNativeAdSmall(Activity mActivity, MyNativeSmall frameLayout) {
        showDialog(mActivity);
        AdLoader adLoader = new AdLoader.Builder(mActivity, SharedPref.getAdmobNativeKey(mActivity)).forNativeAd(nativeAd -> {
            NativeAdView adView = (NativeAdView) mActivity.getLayoutInflater().inflate(R.layout.native_admob_small, null);
            if (mNativeAd != null) {
                mNativeAd.destroy();
            }
            populateNativeAdView(nativeAd, adView);
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                frameLayout.setVisibility(View.GONE);
                closeDialog();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                frameLayout.setVisibility(View.VISIBLE);
                closeDialog();
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView(adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        Objects.requireNonNull(adView.getMediaView()).setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView())).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = Objects.requireNonNull(nativeAd.getMediaContent()).getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (nativeAd.getMediaContent() != null && nativeAd.getMediaContent().hasVideoContent()) {

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        }
    }


    public static void LoadFirebaseAdsData(Context activity, String databaseName) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(databaseName);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPref.setAdInterval(activity, Integer.parseInt(Objects.requireNonNull(snapshot.child("ad_interval").getValue()).toString()));
                SharedPref.setAdmobBannerKey(activity, Objects.requireNonNull(snapshot.child("admob_banner").getValue()).toString());
                SharedPref.setAdmobInterstitialKey(activity, Objects.requireNonNull(snapshot.child("admob_interstitial").getValue()).toString());
                SharedPref.setAdmobNativeKey(activity, Objects.requireNonNull(snapshot.child("admob_native").getValue()).toString());
                SharedPref.setAdmobRewardedKey(activity, Objects.requireNonNull(snapshot.child("admob_rewarded").getValue()).toString());
                SharedPref.setAdmobAppOpenKey(activity, Objects.requireNonNull(snapshot.child("admob_appOpen").getValue()).toString());

                SharedPref.setCheckStart(activity, Objects.requireNonNull(snapshot.child("checkStart").getValue()).toString());

                SharedPref.setIsNative(activity, (Boolean) (snapshot.child("isAppOpenOn").getValue()));
                SharedPref.setIsBanner(activity, (Boolean) (snapshot.child("isBannerOn").getValue()));
                SharedPref.setIsInterstitial(activity, (Boolean) (snapshot.child("isInterstitialOn").getValue()));
                SharedPref.setIsNative(activity, (Boolean) (snapshot.child("isNativeOn").getValue()));
                SharedPref.setIsRewarded(activity, (Boolean) (snapshot.child("isRewardedOn").getValue()));
                SharedPref.setIsAds(activity, (Boolean) (snapshot.child("is_AdsOn").getValue()));

                SharedPref.setOneSingleKey(activity, Objects.requireNonNull(snapshot.child("one_single").getValue()).toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public static void showDialog(Activity activity) {
        loadingDialog = new Dialog(activity);
        DialogLoadingBinding binding = DialogLoadingBinding.inflate(LayoutInflater.from(activity));
        binding.lottieAnimationView.setAnimation(SharedPref.getAnimation(activity));
        Objects.requireNonNull(loadingDialog.getWindow()).setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        binding.getRoot().setPadding(-5, -5, -5, -5);
        loadingDialog.setContentView(binding.getRoot());
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setCancelable(false);
        loadingDialog.show();

    }

    public static void closeDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private static void ShowRewarded(Activity activity, onAdShowed onAdShowed) {
        showDialog(activity);
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(activity, SharedPref.getAdmobRewardedKey(activity),
                adRequest, new RewardedAdLoadCallback() {

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        closeDialog();
                        rewardedAd.show(activity, rewardItem -> {
//                            onAdShowed.onAdShow();
                            adShowed = true;
                            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdClicked() {
                                    rewardedAd = null;
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    rewardedAd = null;
                                    onAdShowed.onAdShow();
                                    adShowed = false;
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    rewardedAd = null;
                                    adShowed = false;
                                }

                                @Override
                                public void onAdImpression() {
                                    rewardedAd = null;
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    rewardedAd = null;
                                }
                            });
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        rewardedAd = null;
                        adShowed = false;
                        closeDialog();
                        adNotReadyDialog(activity, onAdShowed);
                    }
                });

    }

    public static void showRewardedDialog(Activity activity, onAdShowed onAdShowed) {
        Dialog watchAdDialog = new Dialog(activity);
        WatchadDialogBinding binding = WatchadDialogBinding.inflate(LayoutInflater.from(activity));
        watchAdDialog.setContentView(binding.getRoot());
        Objects.requireNonNull(watchAdDialog.getWindow()).setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        binding.btnWatch.setOnClickListener(v -> {
            ShowRewarded(activity, onAdShowed);
            watchAdDialog.dismiss();
        });
        binding.btnSkip.setOnClickListener(v -> {
            watchAdDialog.dismiss();
        });
        watchAdDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        watchAdDialog.setCancelable(false);
        watchAdDialog.show();
    }

    public static void adNotReadyDialog(Activity activity, onAdShowed onAdShowed) {
        Dialog watchAdDialog = new Dialog(activity);
        WatchadDialogBinding binding = WatchadDialogBinding.inflate(LayoutInflater.from(activity));
        watchAdDialog.setContentView(binding.getRoot());
        Objects.requireNonNull(watchAdDialog.getWindow()).setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        binding.btnWatch.setText("Try Again");
        binding.btnSkip.setText("Go Back");
        binding.dialogTitle.setText("Sorry Ad isn,t Ready Yet, Please Check your Internet Connection and Try Again. thanks");
        binding.btnWatch.setOnClickListener(v -> {
            ShowRewarded(activity, onAdShowed);
            watchAdDialog.dismiss();
        });
        binding.btnSkip.setOnClickListener(v -> {
            activity.onBackPressed();
            adShowed = false;
            watchAdDialog.dismiss();
        });
        watchAdDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        watchAdDialog.setCancelable(false);
        watchAdDialog.show();
    }

}