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
    private var glUniformTexture = -1
    private var glAttribTextureCoordinate = -1
    private var imageWidthFactorLocation = -1
    private var imageHeightFactorLocation = -1
    private val pixel = 0f
    private var pixelLocation = -1

    override fun onDraw(textureId: Int, cubeBuffer: FloatBuffer, textureBuffer: FloatBuffer) {
        super.onDraw(textureId, cubeBuffer, textureBuffer)
//        Log.e(
//            "onDraw ",
//            "mProgram$mProgram glAttribPosition $glAttribPosition glAttribTextureCoordinate $glAttribTextureCoordinate "
//        )
//        Log.e(
//            "onDraw ",
//            "textureId$textureId,mProgram$mProgram "
//        )
        GLES20.glUseProgram(mProgram) // 指定使用的program
//        GLES20.glEnable(GLES20.GL_CULL_FACE) // 启动剔除


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
        // 真正绘制的操作
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId) // 绑定纹理
        GLES20.glUniform1i(glUniformTexture, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

        GLES20.glDisableVertexAttribArray(glAttribPosition)
        GLES20.glDisableVertexAttribArray(glAttribTextureCoordinate)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    }


    override fun onInit(context: Context) {
        super.onInit(context)
        glAttribPosition = GLES20.glGetAttribLocation(mProgram, "position")
        glAttribTextureCoordinate = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate")
        glUniformTexture = GLES20.glGetUniformLocation(mProgram, "inputImageTexture")
        imageWidthFactorLocation = GLES20.glGetUniformLocation(mProgram, "imageWidthFactor")
        imageHeightFactorLocation = GLES20.glGetUniformLocation(mProgram, "imageHeightFactor")
        pixelLocation = GLES20.glGetUniformLocation(mProgram, "pixel")
        Log.e(
            "onDraw ",
            "glAttribPosition$glAttribPosition ，glUniformTexture$glUniformTexture ，glAttribTextureCoordinate$glAttribTextureCoordinate " +
                    "，imageWidthFactorLocation$imageWidthFactorLocation ，imageHeightFactorLocation$imageHeightFactorLocation ，pixelLocation$pixelLocation ， "
        )
    }

    override fun onOutputSizeChanged(width: Int, height: Int) {
        super.onOutputSizeChanged(width, height)
        GLES20.glUniform1f(imageWidthFactorLocation, 1.0f / width)
        GLES20.glUniform1f(imageHeightFactorLocation, 1.0f / height)
        GLES20.glUniform1f(pixelLocation, 1.0f)
        Log.e("onOutputSizeChanged", "imageWidthFactorLocation${1.0f / width} ")
        Log.e("onOutputSizeChanged", "imageHeightFactorLocation${1.0f / height} ")
    }
}