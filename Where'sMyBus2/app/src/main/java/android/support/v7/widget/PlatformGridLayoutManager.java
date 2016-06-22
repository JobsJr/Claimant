package android.support.v7.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by rajeevkr on 6/21/16.
 */


public class PlatformGridLayoutManager extends GridLayoutManager {

    public static final String TAG = PlatformGridLayoutManager.class.getSimpleName();

    public PlatformGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public PlatformGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    private static Method updateMeasurements;

    @Override
    public void setSpanCount(int spanCount) {
        super.setSpanCount(spanCount);
        if (mSet != null && mSet.length != spanCount) {
            View[] newSet = new View[spanCount];
            System.arraycopy(mSet, 0, newSet, 0, Math.min(mSet.length, spanCount));
            mSet = newSet;
        }
    }
}
