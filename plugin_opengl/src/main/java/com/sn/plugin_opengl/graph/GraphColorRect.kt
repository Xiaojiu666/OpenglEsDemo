package com.sn.plugin_opengl.graph

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.utils.ArrayUtils
import java.nio.FloatBuffer
import kotlin.random.Random

/**
 * 彩色正方形
 *
 * */
class GraphColorRect(context: Context) : Filter(context) {
    val TAG = "GraphicalFilter"
    var vertexString: String? = "normal/color_triangle.vert"
    var fragmentString: String? = "normal/color_triangle.frag"
    var vPostion: Int = -1
    var zColor: Int = -1
    var ourColor: Int = -1
    var colorBuffer: FloatBuffer? = null

    //设置颜色
    var color = floatArrayOf(
        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f
        ,1.0f, 1.0f, 1.0f, 1.0f
//        1.0f, 1.0f, 1.0f, 1.0f,
//        0.0f, 0.0f, 0.0f, 1.0f
    )

    var verTextRect = floatArrayOf(
        -1f, 1f, 0f,
        -1f, -1f, 0f,
        1f, 1f, 0f
        , 1f, -1f, 0f
    )

    override fun onInit() {
        Log.e(TAG, "onInit GraphColorTriangle")
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
//        var rColor = Random.nextFloat()
        var rColor = 0.0f
        GLES20.glUseProgram(glProgramId)
        GLES20.glEnableVertexAttribArray(vPostion)
        GLES20.glEnableVertexAttribArray(zColor)
        ourColor = GLES20.glGetUniformLocation(glProgramId, "ourColor")
//        GLES20.glUniform1f(ourColor, rColor)
        GLES20.glVertexAttribPointer(
            vPostion,
            3,
            GLES20.GL_FLOAT,
            false,
            ONE_POINT_SIZE * 4,
            verTextBuffer
        )
        GLES20.glVertexAttribPointer(zColor, 4, GLES20.GL_FLOAT, false, 0, colorBuffer)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(vPostion)
    }


    override fun getVertexBuffer(): FloatArray {
        return verTextRect
    }

    fun getVColorBuffer(): FloatBuffer {
        return ArrayUtils.transformFloat(color)
    }
}
