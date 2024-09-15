package com.itwingtech.myawesomeads;


import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;

public class MyCollapsableBannerView extends MaterialCardView {
    private Context context;
    private String bannerType;

    // Constructor for programmatic use
    public MyCollapsableBannerView(Context context) {
        super(context);
        init(context, null);
    }

    // Constructor used when inflating from XML
    public MyCollapsableBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    // Constructor used when inflating from XML with a style attribute
    public MyCollapsableBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    // Initialization method
    private void init(Context context, @Nullable AttributeSet attrs) {
        this.context = context;
        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Set up layout and child views (TextView)
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(CENTER);
        layout.setVerticalGravity(CENTER_VERTICAL);
        layout.setHorizontalGravity(CENTER_HORIZONTAL);
        layout.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Create a TextView for the banner text
        TextView textView = new TextView(context);
        textView.setText(R.string.collapsable_banner_text);
        textView.setGravity(CENTER);
        textView.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        // Add TextView to the LinearLayout
        layout.addView(textView);

        // Add the layout to the MaterialCardView
        addView(layout);
        bannerType = "top";


        // If there are attributes from XML, apply them
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyCollapsableBannerView);
            String bannerText = a.getString(R.styleable.MyCollapsableBannerView_collapsableBannerText);
            String bannerType = a.getString(R.styleable.MyCollapsableBannerView_collapsableBannerType);
            int textColor = a.getColor(R.styleable.MyCollapsableBannerView_collapsableBannerTextColor, 0);
            int backgroundColor = a.getColor(R.styleable.MyCollapsableBannerView_collapsableBannerBackgroundColor, 0);
            int strokeColor = a.getColor(R.styleable.MyCollapsableBannerView_collapsableBannerStrokeColor, 0);
            float strokeWidth = a.getDimension(R.styleable.MyCollapsableBannerView_collapsableBannerStrokeWidth, 0);
            float cornerRadius = a.getDimension(R.styleable.MyCollapsableBannerView_collapsableBannerCornerRadius, 0);

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
            if (bannerType != null) {
                this.bannerType = bannerType;
            }
            if (cornerRadius != 0) {
                // Retrieve corner radius from XML if provided, otherwise use default
                // Retrieve corner radius from XML, defaulting to 0 if not provided
                float adRadius = a.getDimension(R.styleable.MyCollapsableBannerView_collapsableBannerCornerRadius, 0);
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
//        int fixedHeightPx = (int) getResources().getDimension(com.intuit.sdp.R.dimen._50sdp);
//        int padding = (int) getResources().getDimension(com.intuit.sdp.R.dimen._3sdp);
        int fixedHeightPx = DimensionUtil.convertSdpToPx(context, 60);  // Convert 50sdp to px
        int padding = DimensionUtil.convertSdpToPx(context, 4);         // Convert 3sdp to px
        int customHeightMeasureSpec = MeasureSpec.makeMeasureSpec(fixedHeightPx + padding, MeasureSpec.EXACTLY);
        int customWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) + padding, MeasureSpec.EXACTLY);
        super.onMeasure(customWidthMeasureSpec, customHeightMeasureSpec);
        setPadding(padding, padding, padding, padding);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (context instanceof Activity) {
            if (bannerType != null && bannerType.equals("top")) {
                MyAwesomeAds.loadCollapsableBanner((Activity) context, MyCollapsableBannerView.this, "top");
            } else if (bannerType != null && bannerType.equals("bottom")) {
                MyAwesomeAds.loadCollapsableBanner((Activity) context, MyCollapsableBannerView.this, "bottom");
            } else {
                MyAwesomeAds.loadCollapsableBanner((Activity) context, MyCollapsableBannerView.this, "top");
            }

        }
    }
}