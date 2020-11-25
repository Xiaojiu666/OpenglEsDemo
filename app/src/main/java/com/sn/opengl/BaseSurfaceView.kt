package com.sn.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent
import com.sn.opengl.coords.VertexCoords
import com.sn.opengl.graphical.*

/**
 * Created by GuoXu on 2020/11/19 19:09.
 */
class BaseSurfaceView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {

    var matrixDemoRender: MatrixDemoRender? = null

    init {
        setEGLContextClientVersion(2)
        val graphicalAttribute = GraphicalAttribute()
        graphicalAttribute.setVertexCoords(VertexCoords.triangleCoords)
        matrixDemoRender = MatrixDemoRender()
        setRenderer(matrixDemoRender)
    }

    private val TOUCH_SCALE_FACTOR = 180.0f / 320
    private var previousX = 0f
    private var previousY = 0f

    override fun onTouchEvent(e: MotionEvent): Boolean {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        val x: Float = e.getX()
        val y: Float = e.getY()
        when (e.getAction()) {
            MotionEvent.ACTION_MOVE -> {
                var dx = x - previousX
                var dy = y - previousY
                // reverse direction of rotation above the mid-line
                if (y > height / 2) {
                    dx = dx * -1
                }

                // reverse direction of rotation to left of the mid-line
                if (x < width / 2) {
                    dy = dy * -1
                }
                matrixDemoRender?.mAngle = matrixDemoRender?.mAngle!! +
                        (dx + dy) * TOUCH_SCALE_FACTOR
                requestRender()
            }
        }
        previousX = x
        previousY = y
        return true
    }
}