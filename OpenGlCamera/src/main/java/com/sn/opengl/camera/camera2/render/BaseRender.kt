package com.sn.opengl.camera.camera2.render

import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.sn.opengl.camera.camera2.draw.BaseDraw
import com.sn.plugin_opengl.utils.Array2BufferUtils.Companion.transformFloat
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class BaseRender(private val mDraw: BaseDraw, var context: Context) :
    GLSurfaceView.Renderer {
    private var glCubeBuffer: FloatBuffer? = null
    private var glTextureBuffer: FloatBuffer? = null
    private val texture = IntArray(1)

    companion object {
        private val CUBE = floatArrayOf(
            -1f, 1f,
            -1f, -1f,
            1f, 1f,
            1f, -1f
        )

        private val TEXTURE_NO_ROTATION = floatArrayOf(
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
        )
    }

    /**
     * 初始化 顶点坐标和纹理坐标
     */
    private fun initBuffer() {
        glCubeBuffer =
            transformFloat(CUBE)
        glTextureBuffer =
            transformFloat(TEXTURE_NO_ROTATION)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        mDraw.onInit(context)
        initBuffer()
        if (mRenderListener != null) {
            mRenderListener?.onSurfaceTextureCreated(SurfaceTexture(texture[0]))
        }
    }


    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        GLES20.glUseProgram(mDraw.mProgram)
        mDraw.onOutputSizeChanged(width, height)
    }

    override fun onDrawFrame(gl: GL10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        mRenderListener?.onDrawFrame()
        mDraw.onDraw(texture[0], glCubeBuffer!!, glTextureBuffer!!)
    }


    var mRenderListener: OnSurfaceTextureListener? = null

    open fun setRenderListener(mRenderListener: OnSurfaceTextureListener?) {
        this.mRenderListener = mRenderListener
    }


    interface OnSurfaceTextureListener {
        fun onDrawFrame()
        fun onSurfaceTextureCreated(surfaceTexture: SurfaceTexture?)
    }

}