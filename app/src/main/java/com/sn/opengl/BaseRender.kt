package com.sn.opengl

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import java.nio.ByteBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by GuoXu on 2020/11/19 19:16.
 *
 */
abstract class BaseRender : GLSurfaceView.Renderer {


    private var baseProgram: Int = 0
    var baseSurfaceView: BaseSurfaceView? = null

    @Volatile
    var mAngle = 0f

    override fun onDrawFrame(p0: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
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

    abstract fun surfaceChanged(width: Int, height: Int)

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


    fun setTextureImage(bitmap: Bitmap) {
        val tmp = ByteArray(bitmap.width * bitmap.height * 4)
        val wrap = ByteBuffer.wrap(tmp)
        wrap.position(0)
        val byteCount: Int = bitmap.byteCount
        bitmap.copyPixelsToBuffer(wrap) //默认都是3264*2448
        wrap.position(0)
    }


    //绘制坐标范围
    var basevertexData = floatArrayOf(
        0F, 0f, 0.0f,  // top
        1f, 0f, 0.0f, // bottom left
        0F, 0f, 0.0f,  // top
        0f, 1f, 0.0f, // bottom left
        0F, 0f, 0.0f,  // top
        0f, 0f, 1f // bottom left
    )

    val baseColor = floatArrayOf(1f, 0f, 0f, 1.0f)
}

