package com.sn.opengl.camera.camera2.view

import android.app.Activity
import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Size
import com.sn.opengl.camera.camera2.render.CameraRender

/**
 * Created by GuoXu on 2020/12/7 15:17.
 */
class AutoFixGlSurfaceView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {

    init {
        setEGLContextClientVersion(2)
        val glRender = CameraRender(context as Activity?)
        setRenderer(glRender)
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    private val mSurfaceTexture: SurfaceTexture? = null
    private var ratioWidth = 0
    private var ratioHeight = 0


    fun setAspectRatio(size: Size) {
        require(!(width < 0 || height < 0)) { "Size cannot be negative." }
        ratioWidth = size.width
        ratioHeight = size.height
        holder.setFixedSize(width, height)
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (0 == ratioWidth || 0 == ratioHeight) {
            setMeasuredDimension(width, height)
        } else {
            val scaleHeight = height * ratioWidth / ratioHeight
            val scaleWidth = width * ratioHeight / ratioWidth
            if (width < scaleHeight) {
                setMeasuredDimension(width, scaleWidth)
            } else {
                setMeasuredDimension(scaleHeight, height)
            }
        }
    }

}


