package com.sn.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.sn.opengl.coords.VertexCoords
import com.sn.opengl.graphical.MatrixNormalSquare
import com.sn.opengl.graphical.NormalSquare
import com.sn.opengl.graphical.NormalSquareTexture
import com.sn.opengl.graphical.RotateSquare

/**
 * Created by GuoXu on 2020/11/19 19:09.
 */
class BaseSurfaceView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {
    init {
        setEGLContextClientVersion(2)
        val graphicalAttribute = GraphicalAttribute()
        graphicalAttribute.setVertexCoords(VertexCoords.triangleCoords)
        //       setRenderer(NormalSquareTexture(graphicalAttribute, getContext()))
        setRenderer(NormalSquare(graphicalAttribute))
//        setRenderer(MatrixNormalSquare(graphicalAttribute))
//        setRenderer(RotateSquare(graphicalAttribute))
    }
}