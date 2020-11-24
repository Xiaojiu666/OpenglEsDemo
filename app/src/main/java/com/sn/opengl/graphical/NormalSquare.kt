package com.sn.opengl.graphical

import android.opengl.GLES20
import android.util.Log
import com.sn.opengl.*
import com.sn.opengl.shader.FragmentShader
import com.sn.opengl.shader.VertexShader

/**
 * Created by GuoXu on 2020/11/19 19:49.
 * 1. 根据顶点坐标绘制三角形
 * 2. 使用texCoords纹理坐标贴图
 */
class NormalSquare(var graphicalAttribute: GraphicalAttribute) : BaseRender() {
    var vertexSize: Int = 0
    var mProgram: Int = 0


    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    override fun surfaceChanged(width: Int, height: Int) {
    }


    override fun initGl() {
        mProgram = ShaderUtils.createProgram(
            MyApp.context!!.resources,
            "normal/normal.vert",
            "normal/normal.frag"
        )
        Log.e("TAG", "mProgram$mProgram")

        vertexSize =
            graphicalAttribute.vertextCoords?.size?.div(Utils.COORDS_VERTEX_THREE)!!
    }

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    override fun drawGraphical() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)
        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {
            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(it)

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(
                it,
                Utils.COORDS_VERTEX_THREE,
                GLES20.GL_FLOAT,
                false,
                Utils.COORDS_VERTEX_THREE * 4,
                graphicalAttribute.vertexCoordsBuffer
            )
            // get handle to fragment shader's vColor member
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
                // Set color for drawing the triangle
                GLES20.glUniform4fv(it, 1, color, 0)
            }

            // Draw the triangle

            GLES20.glDrawArrays(
                GLES20.GL_TRIANGLES,
                0,
                vertexSize
            )
            // Disable vertex array
            GLES20.glDisableVertexAttribArray(it)
        }
    }
}