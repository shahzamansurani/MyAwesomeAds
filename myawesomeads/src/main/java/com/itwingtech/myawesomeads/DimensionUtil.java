package com.itwingtech.myawesomeads;

import android.content.Context;
import android.util.TypedValue;

public class DimensionUtil {

    public static int convertSdpToPx(Context context, float sdpValue) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sdpValue,
                context.getResources().getDisplayMetrics()
        );
    }
}
