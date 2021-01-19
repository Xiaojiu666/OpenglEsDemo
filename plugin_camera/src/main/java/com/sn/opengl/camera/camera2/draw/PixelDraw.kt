package com.sn.opengl.camera.camera2.draw

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import java.nio.FloatBuffer

class PixelDraw :
    BaseDraw(vertexShader, fragmentShader) {
    companion object {
        var vertexShader = "pixel/pixel.vert"
        var fragmentShader = "pixel/pixel.frag"
    }

    private var glAttribPosition = -1
    private var glAttribTextureCoordinate = -1

    override fun onDraw(textureId: Int, cubeBuffer: FloatBuffer, textureBuffer: FloatBuffer) {
        super.onDraw(textureId, cubeBuffer, textureBuffer)
        GLES20.glUseProgram(mProgram) // 指定使用的program
        GLES20.glEnable(GLES20.GL_CULL_FACE) // 启动剔除

        GLES20.glEnableVertexAttribArray(glAttribPosition)
        GLES20.glVertexAttribPointer(
            glAttribPosition,
            2,
            GLES20.GL_FLOAT,
            false,
            8,
            cubeBuffer
        )
        GLES20.glEnableVertexAttribArray(glAttribTextureCoordinate)
        GLES20.glVertexAttribPointer(
            glAttribTextureCoordinate,
            2,
            GLES20.GL_FLOAT,
            false,
            8,
            textureBuffer
        )

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(glAttribPosition)
        GLES20.glDisableVertexAttribArray(glAttribTextureCoordinate)
    }


    override fun onInit(context: Context) {
        super.onInit(context)
        glAttribPosition = GLES20.glGetAttribLocation(mProgram, "vPosition")
        glAttribTextureCoordinate = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate")
    }

    override fun onOutputSizeChanged(width: Int, height: Int) {
        super.onOutputSizeChanged(width, height)
    }
}