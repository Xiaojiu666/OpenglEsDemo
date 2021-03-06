package com.sn.plugin_opengl.view

import android.content.Context
import android.util.AttributeSet
import com.sn.plugin_opengl.GPUImageRender
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.graph.GraphColorRect
import com.sn.plugin_opengl.graph.GraphColorTriangle
import com.sn.plugin_opengl.graph.GraphTextureRect

class MyGlSurfaceView(context: Context?, attrs: AttributeSet? = null) :
    AutoFitGlSurfaceView(context, attrs) {

    var filter: Filter? = null
    var baseRender: GPUImageRender? = null

    init {
        setEGLContextClientVersion(2)
        filter = GraphTextureRect(getContext())
        filter?.let {
            baseRender = GPUImageRender(it)
            setRenderer(baseRender)
           renderMode = RENDERMODE_CONTINUOUSLY
//             renderMode = RENDERMODE_WHEN_DIRTY
        }

    }

    fun setNewFilter(newGLFilter: Filter) {
        baseRender?.setNetGlFilter(newGLFilter)
    }
}