package com.sn.plugin_opengl.graph

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.utils.ArrayUtils
import java.nio.FloatBuffer

/**
 * 彩色三角形
 *
 */
class GraphColorTriangle(context: Context) : Filter(context) {
    val TAG = "GraphicalFilter"
    var vertexString: String? = "normal/color_triangle.vert"
    var fragmentString: String? = "normal/color_triangle.frag"
    var vPostion: Int = -1
    var zColor: Int = -1
    var colorBuffer: FloatBuffer? = null

    //设置颜色
    var color = floatArrayOf(
        0.0f, 1.0f, 0.0f, 1.0f,
        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f
    )

    override fun onInit() {
        Log.e(TAG,"onInit GraphColorTriangle")
        createProgram(vertexString!!, fragmentString!!)
        vPostion = GLES20.glGetAttribLocation(glProgramId, "vPosition")
        zColor = GLES20.glGetAttribLocation(glProgramId, "zColor")
        colorBuffer = getVColorBuffer()
    }

    override fun onDrawFrame(
        textureId: Int,
        verTextBuffer: FloatBuffer,
        textureBuffer: FloatBuffer
    ) {
        Log.e(TAG, "glProgramId$glProgramId zColor $zColor vPostion$vPostion")
        GLES20.glUseProgram(glProgramId)
        GLES20.glEnableVertexAttribArray(vPostion)
        GLES20.glEnableVertexAttribArray(zColor)
        GLES20.glVertexAttribPointer(
            vPostion,
            3,
            GLES20.GL_FLOAT,
            false,
            3 * 4,
            verTextBuffer
        )
        //设置绘制三角形的颜色
        GLES20.glVertexAttribPointer(
            zColor, 4,
            GLES20.GL_FLOAT, false,
            0, colorBuffer
        )
        GLES20.glDrawArrays(
            GLES20.GL_TRIANGLES,
            0,
            3
        )
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(vPostion)
    }


    override fun getVertexBuffer(): FloatArray {
        return CUBE
    }

    fun getVColorBuffer(): FloatBuffer {
        return ArrayUtils.transformFloat(color)
    }
}
