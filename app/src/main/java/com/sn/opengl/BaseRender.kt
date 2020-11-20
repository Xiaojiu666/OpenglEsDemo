package com.sn.opengl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by GuoXu on 2020/11/19 19:16.
 *
 */
abstract class BaseRender : GLSurfaceView.Renderer {



    override fun onDrawFrame(p0: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        drawGraphical()
    }
    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        surfaceChanged(width, height)
    }


    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        // Set the background frame color
        GLES20.glClearColor(0f, 0f, 0f, 0.5f)
        initGl()
    }

    abstract fun surfaceChanged( width: Int, height: Int)

    abstract fun initGl()

    abstract fun drawGraphical()


    fun loadShader(type: Int, shaderCode: String?): Int {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        val shader = GLES20.glCreateShader(type)
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }

}