package com.sn.plugin_opengl.filter

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import com.sn.plugin_opengl.utils.ShaderUtils
import java.nio.FloatBuffer

open class Filter(var context: Context) {

    companion object {
        const val NO_TEXTURE = 0
        const val ONE_POINT_SIZE = 3  // 一个点的xyz
    }

    val CUBE = floatArrayOf(
        1f, 1f, 0.0f,  // top
        1f, -1f, 0.0f,  // bottom left
        -1f, -1f, 0.0f // bottom right
    )

    val TEXTURE_NO_ROTATION = floatArrayOf(
        0.0f, 1.0f,
        1.0f, 1.0f,
        0.0f, 0.0f,
        1.0f, 0.0f
    )

    var glProgramId = -1
    open fun onDrawFrame(textureId: Int, verTextBuffer: FloatBuffer, textureBuffer: FloatBuffer) {
    }

    open fun onInit() {

    }

    fun createProgram(vertexString: String, fragmentString: String) {
        glProgramId = ShaderUtils.createProgram(
            context.resources,
            vertexString,
            fragmentString
        )
    }

    open fun getVertexBuffer(): FloatArray {
        return CUBE
    }

    fun getTextureBuffer(): FloatArray {
        return TEXTURE_NO_ROTATION
    }

    fun getProgram(): Int {
        return glProgramId
    }

    fun destroy() {
        GLES20.glDeleteProgram(glProgramId)
        onDestroy()
    }

    open fun onDestroy() {}

    fun loadTexture(img: Bitmap): Int {
        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat()
        )
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR.toFloat()
        )
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE.toFloat()
        )
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE.toFloat()
        )
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, img, 0)
        img.recycle()
        return textures[0]
    }
}