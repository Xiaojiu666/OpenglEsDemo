package com.sn.plugin_opengl.graph

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.utils.ArrayUtils
import java.nio.FloatBuffer
import kotlin.random.Random

/**
 * 彩色三角形
 * https://blog.csdn.net/weixin_30788239/article/details/99263454?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control
 */
class GraphColorTriangle(context: Context) : Filter(context) {
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
        //传递变量，控制片元着色器 根据输入值变化rgba的颜色
        ourColor = GLES20.glGetUniformLocation(glProgramId, "ourColor")
        GLES20.glUniform1f(ourColor, rColor)
        GLES20.glVertexAttribPointer(vPostion, 3, GLES20.GL_FLOAT, false, 3 * 4, verTextBuffer)
        //设置绘制三角形的颜色
        GLES20.glVertexAttribPointer(zColor, 4, GLES20.GL_FLOAT, false, 0, colorBuffer)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)
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
