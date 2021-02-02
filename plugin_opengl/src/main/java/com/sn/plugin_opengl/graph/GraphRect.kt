package com.sn.plugin_opengl.graph

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.utils.ArrayUtils
import java.nio.FloatBuffer
import kotlin.random.Random

/**
 * 正方形
 * https://blog.csdn.net/patient16/article/details/50540011  //draw三角形的 三种连线规则
 *
 */
class GraphRect(context: Context) : Filter(context) {
    val TAG = "GraphicalFilter"
    var vertexString: String? = "normal/normal.vert"
    var fragmentString: String? = "normal/normal.frag"
    var vPostion: Int = -1
    var ourColor: Int = -1
    var colorBuffer: FloatBuffer? = null
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    var vColor: Int = -1
    var verTextRect = floatArrayOf(
        -1f, 1f, 0f,
        -1f, -1f, 0f,
        1f, 1f, 0f,
        1f, -1f, 0f
    )

    override fun onInit() {
        Log.e(TAG, "onInit GraphColorTriangle")
        createProgram(vertexString!!, fragmentString!!)
        vPostion = GLES20.glGetAttribLocation(glProgramId, "vPosition")
        vColor = GLES20.glGetUniformLocation(glProgramId, "vColor")
        colorBuffer = getVColorBuffer()
    }

    override fun onDrawFrame(
        textureId: Int,
        verTextBuffer: FloatBuffer,
        textureBuffer: FloatBuffer
    ) {
        GLES20.glUseProgram(glProgramId)
        GLES20.glEnableVertexAttribArray(vPostion)
        ourColor = GLES20.glGetUniformLocation(glProgramId, "ourColor")
        GLES20.glUniform4fv(vColor, 1, color, 0)
        GLES20.glVertexAttribPointer(vPostion, getVertexBuffer().size / ONE_POINT_SIZE, GLES20.GL_FLOAT, false, ONE_POINT_SIZE * 4, verTextBuffer)
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
