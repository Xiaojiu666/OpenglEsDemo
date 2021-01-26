package com.sn.plugin_opengl.filter

import android.content.Context
import android.opengl.GLES20
import com.sn.plugin_opengl.utils.ShaderUtils
import java.nio.FloatBuffer

open class Filter(var context: Context) {

    companion object {
        const val NO_TEXTURE = 0

    }

    val CUBE = floatArrayOf(
        1f,1f, 0.0f,  // top
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

    open fun onInit(){

    }

    fun createProgram(vertexString: String, fragmentString: String) {
        glProgramId = ShaderUtils.createProgram(
            context.resources,
            vertexString,
            fragmentString
        )
    }

    fun getVertexBuffer(): FloatArray {
        return CUBE
    }

    fun getTextureBuffer(): FloatArray {
        return TEXTURE_NO_ROTATION
    }

    fun getProgram(): Int {
        return glProgramId
    }


}