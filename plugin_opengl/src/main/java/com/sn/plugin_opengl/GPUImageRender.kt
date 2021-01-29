package com.sn.plugin_opengl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.utils.ArrayUtils
import java.nio.FloatBuffer
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GPUImageRender(var glFilter: Filter) : GLSurfaceView.Renderer {

    private val backgroundRed = 0f
    private val backgroundGreen = 0f
    private val backgroundBlue = 0f
    private var runOnDraw: Queue<Runnable>? = null
    private var runOnDrawEnd: Queue<Runnable>? = null

    init {
        runOnDraw = LinkedList<Runnable>()
        runOnDrawEnd = LinkedList()
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        runAll(runOnDraw!!)
        glFilter.onDrawFrame(0, getVertexBuffer(), getTextureBuffer())
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.e(TAG, "onSurfaceChanged widht:$width height:$height")
        GLES20.glViewport(0, 0, width, height)
        glFilter.onInit()
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.e(TAG, "onSurfaceCreated")
        GLES20.glClearColor(backgroundRed, backgroundGreen, backgroundBlue, 1f)
    }

    fun getVertexBuffer(): FloatBuffer {
        return ArrayUtils.transformFloat(glFilter.getVertexBuffer())
    }

    fun getTextureBuffer(): FloatBuffer {
        return ArrayUtils.transformFloat(glFilter.getTextureBuffer())
    }

    companion object {
        const val TAG = "GPUImageRender"
    }

    fun setNetGlFilter(filter: Filter) {
        runOnDraw(Runnable {
            glFilter.destroy()
            glFilter = filter
            glFilter.onInit()
            GLES20.glUseProgram(glFilter.getProgram())
        })
    }

    //事件消息队列
    private fun runAll(queue: Queue<Runnable>) {
        synchronized(queue) {
            while (!queue.isEmpty()) {
                queue.poll()?.run()
            }
        }
    }

    fun runOnDraw(runnable: Runnable?) {
        synchronized(runOnDraw!!) { runOnDraw!!.add(runnable) }
    }

}