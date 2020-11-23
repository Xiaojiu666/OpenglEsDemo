package com.sn.opengl.graphical

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import android.util.Log
import com.sn.opengl.BaseRender
import com.sn.opengl.GraphicalAttribute
import com.sn.opengl.R
import com.sn.opengl.Utils
import com.sn.opengl.shader.FragmentShader
import com.sn.opengl.shader.VertexShader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


/**
 * Created by GuoXu on 2020/11/19 19:49.
 * 1. 根据顶点坐标绘制三角形
 * 2. 使用texCoords纹理坐标贴图
 */
class NormalSquareTexture(var graphicalAttribute: GraphicalAttribute, var context: Context) :
    BaseRender() {

    var textureBuffer: FloatBuffer? = null
    var vertexBuffer: FloatBuffer? = null
    var texCoords = floatArrayOf(
        1f, 1f, 0.0f, // 左下角
        1.0f, 0.0f, 0.0f, // 右下角
        0f, 0f, 0.0f // 上中
    )

    private val VERTEX_SHADER = """
        attribute vec4 vPosition;
        attribute vec2 vCoordinate;
        varying vec2 aCoordinate;
        void main() {
        gl_Position = vPosition;
        aCoordinate = vCoordinate;
        }
        """

    private val FRAGMENT_SHADER = """
        precision mediump float;
        uniform sampler2D vTexture;
        varying vec2 aCoordinate;
        void main() {
        gl_FragColor = texture2D(vTexture,aCoordinate);
        }
        """
    //绘制坐标范围
    var vertexData = floatArrayOf(
        -1f, -1f,
        1f, -1.0f,
        -1f, 1.0f,
        1f, 1f
    )
    var textureData = floatArrayOf(
        0f, 1f,
        1f, 1f,
        0f, 0f,
        1f, 0f
    )

    override fun surfaceChanged(width: Int, height: Int) {
    }

    var TAG = "NormalSquareTexture";
    override fun initGl() {
        val vertexShader: Int =
            loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER)
        val fragmentShader: Int =
            loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER)
        Log.e(TAG, "vertexShader $vertexShader ,fragmentShader $fragmentShader")
//        vertexSize =
//            graphicalAttribute.vertextCoords?.size?.div(Utils.COORDS_VERTEX_THREE)!!
        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram().also {
            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)
        }
        Log.e(TAG, "mProgram $mProgram")
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.size * 4)
            .order(ByteOrder.nativeOrder())//对齐，加快处理速度
            .asFloatBuffer()
            .put(vertexData);
        //指向0的位置
        vertexBuffer?.position(0);

        textureBuffer = ByteBuffer.allocateDirect(textureData.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(textureData);
        textureBuffer?.position(0);

        //纹理顶点
        textureHandle = GLES20.glGetAttribLocation(mProgram, "vCoordinate");
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        Log.e(TAG, "textureHandle $textureHandle ,positionHandle $positionHandle")
        val textureIds = IntArray(1)
        //创建纹理
        GLES20.glGenTextures(1, textureIds, 0)
        if (textureIds[0] == 0) {
            Log.e(TAG, "textureIds is null")
            return
        }

        textureId = textureIds[0]
        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        //环绕（超出纹理坐标范围）  （s==x t==y GL_REPEAT 重复）
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        //过滤（纹理像素映射到坐标点）  （缩小、放大：GL_LINEAR线性）
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_LINEAR
        );
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_LINEAR
        )

        val bitmap =
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher)
        if (bitmap == null) {
            Log.e(TAG, "bitmap is null")
            return
        }
        //设置纹理为2d图片
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle();
    }

    var positionHandle: Int = 0
    var textureHandle: Int = 0
    var vertexSize: Int = 4
    var mProgram: Int = 0
    var textureId: Int = 0


    override fun drawGraphical() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)
        // get handle to vertex shader's vPosition member
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glEnableVertexAttribArray(textureHandle)
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            positionHandle,
           2,
            GLES20.GL_FLOAT,
            false,
            Utils.COORDS_VERTEX_TWO * 4,
            vertexBuffer
        )

        //设置纹理位置值
        GLES20.glVertexAttribPointer(
            textureHandle,
           2,
            GLES20.GL_FLOAT,
            false,
            Utils.COORDS_VERTEX_TWO * 4,
            textureBuffer
        )

        GLES20.glDrawArrays(
            GLES20.GL_TRIANGLE_STRIP,
            0,
            4
        )
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(textureHandle)

    }

}