package com.sn.plugin_opengl.view

import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.Surface
/**
 * Created by GuoXu on 2020/12/7 15:17.
 */
open class AutoFitGlSurfaceView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs){


    companion object {
        const val TAG = "AutoFixGlSurfaceView"
    }

    private var mSurfaceTexture: SurfaceTexture? = null
    private var ratioWidth = 0
    private var ratioHeight = 0


    fun setAspectRatio(size: Size) {
        Log.e(TAG, "Size $size")
        require(!(width < 0 || height < 0)) { "Size cannot be negative." }
        ratioWidth = size.width
        ratioHeight = size.height
        holder.setFixedSize(height, width)
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (0 == ratioWidth || 0 == ratioHeight) {
            setMeasuredDimension(width, height)
        } else {
            Log.e(TAG, "onMeasure $ratioHeight , $ratioWidth")
            when {
                ratioWidth > ratioHeight -> {
                    val scaleHeight = width * ratioWidth / ratioHeight
                    Log.e(TAG, "onMeasure> $width , $scaleHeight")
                    setMeasuredDimension(width, scaleHeight)
                }
                ratioWidth < ratioHeight -> {
                    Log.e(TAG, "onMeasure< $width , $height")
                    setMeasuredDimension(width, height)
                }
                ratioWidth == ratioHeight -> {
                    Log.e(TAG, "onMeasure= $width , $width")
                    setMeasuredDimension(width, width)
                }
            }

//            val scaleHeight = height * ratioHeight / ratioWidth
//            val scaleWidth = width * ratioHeight / ratioWidth
//            if (width <= height) {
//                setMeasuredDimension(width, scaleHeight)
//            } else {
//                setMeasuredDimension(scaleHeight, height)
//            }
        }
    }
}


