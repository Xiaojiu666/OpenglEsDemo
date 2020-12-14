package com.sn.opengl.camera.camera2.draw

import android.content.Context
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.util.Log
import java.nio.FloatBuffer

class CameraDraw :
    BaseDraw(vertexShader, fragmentShader) {

    var mPositionHandle = 0
    var mTextureHandle = 0

    companion object {
        var vertexShader = "base/base.vert"
        var fragmentShader = "base/base.frag"
    }


    override fun onDraw(textureId: Int, cubeBuffer: FloatBuffer, textureBuffer: FloatBuffer) {
        Log.e(
            "onDraw ",
            "mProgram$mProgram mTextureHandle $mTextureHandle mPositionHandle $mPositionHandle "
        )
        GLES20.glUseProgram(mProgram) // 指定使用的program
        GLES20.glEnable(GLES20.GL_CULL_FACE) // 启动剔除
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId) // 绑定纹理

        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glVertexAttribPointer(
            mPositionHandle,
            2,
            GLES20.GL_FLOAT,
            false,
            8,
            cubeBuffer
        )
        GLES20.glEnableVertexAttribArray(mTextureHandle)
        GLES20.glVertexAttribPointer(
            mTextureHandle,
            2,
            GLES20.GL_FLOAT,
            false,
            8,
            textureBuffer
        )
        // 真正绘制的操作
        // 真正绘制的操作
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

        GLES20.glDisableVertexAttribArray(mPositionHandle)
        GLES20.glDisableVertexAttribArray(mTextureHandle)
    }

    override fun onInit(context: Context) {
        super.onInit(context)
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        mTextureHandle = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate")
    }
}