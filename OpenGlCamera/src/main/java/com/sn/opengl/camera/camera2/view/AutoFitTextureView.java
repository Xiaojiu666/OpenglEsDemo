package com.sn.opengl.camera.camera2.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

public class AutoFitTextureView extends TextureView {

    private static final String TAG = "AutoFitTextureView";
    private int ratioWidth = 0;
    private int ratioHeight = 0;
    public AutoFitTextureView(@NonNull Context context) {
        this(context, null);
    }

    public AutoFitTextureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitTextureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that is,
     * calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(final int width, final int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        ratioWidth = width;
        ratioHeight = height;
        getSurfaceTexture().setDefaultBufferSize(width, height);
        requestLayout();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == ratioWidth || 0 == ratioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * ratioWidth / ratioHeight) {
                Log.e(TAG, "onMeasure  ratioWidth" + ratioWidth + " ratioHeight" + ratioHeight);
                setMeasuredDimension(width, width * ratioHeight / ratioWidth);
                Log.e(TAG, "onMeasure  width" + width + " width * ratioHeight / ratioWidth" + width * ratioHeight / ratioWidth);
            } else {
                setMeasuredDimension(height * ratioWidth / ratioHeight, height);
                Log.e(TAG, "onMeasure  height * ratioWidth / ratioHeight" + height * ratioWidth / ratioHeight + "height" + height);
            }
        }
    }
}
