package com.sn.plugin_opengl.graph

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.sn.plugin_opengl.filter.Filter
import java.nio.FloatBuffer

/**
 * 形状--默认基类
 * 三角形
 */
class Graph(context: Context) : Filter(context) {

    val TAG = "GraphicalFilter"
    var vertexString: String? = "normal/normal.vert"
    var fragmentString: String? = "normal/normal.frag"
    var vPostion: Int = -1
    var vColor: Int = -1
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    override fun onInit() {
        createProgram(vertexString!!, fragmentString!!)
        vPostion = GLES20.glGetAttribLocation(glProgramId, "vPosition")
        vColor = GLES20.glGetUniformLocation(glProgramId, "vColor")
    }

    override fun onDrawFrame(
        textureId: Int,
        verTextBuffer: FloatBuffer,
        textureBuffer: FloatBuffer
    ) {
        GLES20.glUseProgram(glProgramId)
        if (textureId == NO_TEXTURE) {
        }
        GLES20.glEnableVertexAttribArray(vPostion)
        GLES20.glVertexAttribPointer(vPostion, 3, GLES20.GL_FLOAT, false, 3 * 4, verTextBuffer)
        GLES20.glUniform4fv(vColor, 1, color, 0)
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


}