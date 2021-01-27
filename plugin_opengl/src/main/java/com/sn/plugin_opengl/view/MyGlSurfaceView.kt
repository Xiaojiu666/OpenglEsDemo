package com.sn.plugin_opengl.view

import android.content.Context
import android.util.AttributeSet
import com.sn.plugin_opengl.GPUImageRender
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.graph.GraphBase
import com.sn.plugin_opengl.graph.GraphColorTriangle

class MyGlSurfaceView(context: Context?, attrs: AttributeSet? = null) :
    AutoFitGlSurfaceView(context, attrs) {

    var filter: Filter? = null
    var baseRender: GPUImageRender? = null

    init {
        setEGLContextClientVersion(2)
        filter = GraphColorTriangle(getContext())
        filter?.let {
            baseRender = GPUImageRender(it)
            setRenderer(baseRender)
            renderMode = RENDERMODE_CONTINUOUSLY
        }

    }

    fun setNewFilter(newGLFilter: Filter) {
        baseRender?.glFilter?.destroy()
        baseRender?.glFilter = newGLFilter
        baseRender?.glFilter?.onInit()
        requestRender()
    }


}