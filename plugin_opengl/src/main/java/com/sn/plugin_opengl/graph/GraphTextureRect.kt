package com.sn.plugin_opengl.graph

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.opengl.GLES20
import android.util.Log
import com.sn.plugin_opengl.filter.Filter
import java.io.IOException
import java.io.InputStream
import java.nio.FloatBuffer


/**
 * 图片纹理正方形
 *
 */
class GraphTextureRect(context: Context) : Filter(context) {
    var textureId: Int = -1
    val TAG = "GraphTextureRect"
    var vertexString: String? = "texture/texture.vert"
    var fragmentString: String? = "texture/texture.frag"
    var vPostion: Int = -1
    var ourColor: Int = -1
    var glUniformTexture = -1
    private var glAttribTextureCoordinate = -1
    var vColor: Int = -1
    var verTextRect = floatArrayOf(
        -1.0f, -1.0f,
        1.0f, -1.0f,
        -1.0f, 1.0f,
        1.0f, 1.0f
    )
    var bitmap: Bitmap? = null

    init {
        bitmap = getImageFromAssetsFile("ic_launcher.png")
    }


    fun setImageBitmap(bitmap: Bitmap?) {

        if (bitmap == null) {
            return
        }
        var resizedBitmap: Bitmap? = null
//        if (bitmap.width % 2 == 1) {
//            resizedBitmap = Bitmap.createBitmap(
//                bitmap.width + 1, bitmap.height,
//                Bitmap.Config.ARGB_8888
//            )
//            resizedBitmap.density = bitmap.density
//            val can = Canvas(resizedBitmap)
//            can.drawARGB(0x00, 0x00, 0x00, 0x00)
//            can.drawBitmap(bitmap, 0f, 0f, null)
//        } else {
//        }
        textureId = loadTexture(
            bitmap
        )
        resizedBitmap?.recycle()
    }

    private fun getImageFromAssetsFile(fileName: String): Bitmap? {
        var image: Bitmap? = null
        val am: AssetManager = context.resources.assets
        try {
            val `is`: InputStream = am.open(fileName)
            image = BitmapFactory.decodeStream(`is`)
            `is`.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }


    override fun onInit() {
        createProgram(vertexString!!, fragmentString!!)
        vPostion = GLES20.glGetAttribLocation(glProgramId, "vPosition")
        glUniformTexture = GLES20.glGetUniformLocation(glProgramId, "inputImageTexture")
        glAttribTextureCoordinate =
            GLES20.glGetAttribLocation(glProgramId, "inputTextureCoordinate")
        setImageBitmap(bitmap)
    }

    override fun onDrawFrame(
        textureId1: Int,
        verTextBuffer: FloatBuffer,
        textureBuffer: FloatBuffer
    ) {
        Log.e(
            TAG,
            "onDrawFrame glProgramId$glProgramId , textureId$textureId ,  vPosition $vPostion ,glAttribTextureCoordinate$glAttribTextureCoordinate"
        )
        GLES20.glUseProgram(glProgramId)
        GLES20.glEnableVertexAttribArray(vPostion)
        GLES20.glEnableVertexAttribArray(glAttribTextureCoordinate)
        GLES20.glVertexAttribPointer(
            vPostion,
            2,
            GLES20.GL_FLOAT,
            false,
            0,
            verTextBuffer
        )
        GLES20.glVertexAttribPointer(
            glAttribTextureCoordinate,
            2,
            GLES20.GL_FLOAT,
            false,
            0,
            textureBuffer
        )
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLES20.glUniform1i(glUniformTexture, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(vPostion)
        GLES20.glDisableVertexAttribArray(glAttribTextureCoordinate)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    }


    override fun getVertexBuffer(): FloatArray {
        return verTextRect
    }


}
