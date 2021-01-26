package com.sn.plugin_opengl.view

import android.content.Context
import android.util.AttributeSet
import com.sn.plugin_opengl.GPUImageRender
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.filter.GraphicalFilter
import java.io.File

class MyGlSurfaceView(context: Context?, attrs: AttributeSet? = null) :
    AutoFitGlSurfaceView(context, attrs) {

    init {
        setEGLContextClientVersion(2)
        var filter = GraphicalFilter(getContext())
        val baseRender = GPUImageRender(filter)
        setRenderer(baseRender)
        renderMode = RENDERMODE_CONTINUOUSLY
    }

}