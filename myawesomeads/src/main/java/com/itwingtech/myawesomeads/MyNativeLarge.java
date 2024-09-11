package com.itwingtech.myawesomeads;


import static android.view.Gravity.CENTER;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.card.MaterialCardView;


public class MyNativeLarge extends MaterialCardView {
    private Context context;

    public MyNativeLarge(Context context) {
        super(context);
        init(context, null);
    }

    public MyNativeLarge(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyNativeLarge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Set corner radius and elevation
        this.context = context;

        RelativeLayout layout = new RelativeLayout(context);
        layout.setGravity(CENTER);
        layout.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        TextView textView = new TextView(context);
        textView.setText("Native Ad Area...");
        textView.setGravity(CENTER);
        layout.addView(textView);


        NativeAdView nativeAdView = new NativeAdView(context);
        nativeAdView.setId(View.generateViewId());
        layout.addView(nativeAdView);

        addView(layout);

        // If there are attributes from XML, apply them
        if (attrs != null) {
            @SuppressLint("CustomViewStyleable")
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyNativeLarge);
            String bannerText = a.getString(R.styleable.MyNativeLarge_nativeLargeText);
            int textColor = a.getColor(R.styleable.MyNativeLarge_nativeLargeTextColor, 0);
            int backgroundColor = a.getColor(R.styleable.MyNativeLarge_nativeLargeBackgroundColor, 0);
            int strokeColor = a.getColor(R.styleable.MyNativeLarge_nativeLargeStrokeColor, 0);
            float strokeWidth = a.getDimension(R.styleable.MyNativeLarge_nativeLargeStrokeWidth, 0);
            float cornerRadius = a.getDimension(R.styleable.MyNativeLarge_nativeLargeCornerRadius, 0);


            // Apply custom attributes
            if (bannerText != null) {
                textView.setText(bannerText);
            }
            if (textColor != 0) {
                textView.setTextColor(textColor);
            }
            if (backgroundColor != 0) {
                setCardBackgroundColor(backgroundColor);
            }
            if (strokeColor != 0) {
                setStrokeColor(strokeColor);
            }
            if (strokeWidth != 0) {
                setStrokeWidth((int) strokeWidth);
            }
            if (cornerRadius != 0) {
                // Retrieve corner radius from XML if provided, otherwise use default
                // Retrieve corner radius from XML, defaulting to 0 if not provided
                float adRadius = a.getDimension(R.styleable.MyNativeLarge_nativeLargeCornerRadius, 0);
                setShapeAppearanceModel(
                        getShapeAppearanceModel()
                                .toBuilder()
                                .setAllCornerSizes(adRadius)
                                .build());
            }

            // Recycle the TypedArray to free up memory
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int fixedHeightPx = (int) getResources().getDimension(com.intuit.sdp.R.dimen._240sdp);
        int customHeightMeasureSpec = MeasureSpec.makeMeasureSpec(fixedHeightPx, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, customHeightMeasureSpec);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (context instanceof Activity) {
            MyAwesomeAds.loadNativeLarge((Activity) context, MyNativeLarge.this);
        }
    }

}

