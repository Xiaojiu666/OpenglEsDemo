package com.sn.opengl.graphical

import android.opengl.GLES20
import android.opengl.GLES20.GL_DEPTH_BUFFER_BIT
import android.opengl.Matrix
import android.util.Log
import com.sn.opengl.BaseRender
import com.sn.opengl.MyApp
import com.sn.opengl.ShaderUtils
import com.sn.opengl.Utils


/**
 * Created by GuoXu on 2020/11/19 19:49.
 */
class MatrixDemoRender : BaseRender() {

    var triangleCoords = floatArrayOf( //


        -0.5f, 0.5f, 0.5f,         //上面
        -0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, 0.5f,
        0.5f, 0.5f, -0.5f,
        -0.5f, 0.5f, 0.5f,

        -0.5f, -0.5f, 0.5f,         //下面
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, 0.5f,

        -0.5f, 0.5f, -0.5f,               //正面
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        -0.5f, 0.5f, -0.5f,               //正面
        0.5f, -0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,


        -0.5f, 0.5f, 0.5f,               //正面
        -0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,               //正面
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,


        -0.5f, 0.5f, -0.5f,               //左
        -0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, 0.5f,
        -0.5f, 0.5f, -0.5f,
        -0.5f, -0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,

        0.5f, 0.5f, -0.5f,               //左
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f

//        0f, 1f, 0.0f,  // top
//        -1f, 0f, 0.0f,  // bottom left
//        0f, 0f, 1f, // bottom right
//
//        0f, 1f, 0.0f,  // top
//        1f, 0f, 0.0f, // bottom right
//        0f, 0f, 1f, // bottom right
//
//        -1f, 0f, 0.0f,  // bottom left
//        1f, 0f, 0.0f, // bottom right
//        0f, 0f, 1f, // bottom right
//
//        0f, 1f, 0.0f,  // top
//        -1f, 0f, 0.0f,  // bottom left
//        1f, 0f, 0.0f, // bottom right
//
//        0f, 1f, 0.0f,  // top
//        -1f, 0f, 0.0f,  // bottom left
//        1f, 0f, 0.0f // bottom right


    )


    var color = floatArrayOf(
        0.5f, 0.5f, 0.5f, 1f,
        0.5f, 0.5f, 0.5f, 1f,
        0.5f, 0.5f, 0.5f, 1f,
        0.5f, 0.5f, 0.5f, 1f,
        0.5f, 0.5f, 0.5f, 1f,
        0.5f, 0.5f, 0.5f, 1f,

        0f, 1f, 0f, 1f,
        0f, 1f, 0f, 1f,
        0f, 1f, 0f, 1f,
        0f, 1f, 0f, 1f,
        0f, 1f, 0f, 1f,
        0f, 1f, 0f, 1f,

        0f, 0f, 1f, 1f,
        0f, 0f, 1f, 1f,
        0f, 0f, 1f, 1f,
        0f, 0f, 1f, 1f,
        0f, 0f, 1f, 1f,
        0f, 0f, 1f, 1f,

        1f, 0f, 0f, 1f,
        1f, 0f, 0f, 1f,
        1f, 0f, 0f, 1f,
        1f, 0f, 0f, 1f,
        1f, 0f, 0f, 1f,
        1f, 0f, 0f, 1f,

        1f, 1f, 1f, 1f,
        1f, 1f, 1f, 1f,
        1f, 1f, 1f, 1f,
        1f, 1f, 1f, 1f,
        1f, 1f, 1f, 1f,
        1f, 1f, 1f, 1f,


        0f, 1f, 1f, 1f,
        0f, 1f, 1f, 1f,
        0f, 1f, 1f, 1f,
        0f, 1f, 1f, 1f,
        0f, 1f, 1f, 1f,
        0f, 1f, 1f, 1f,
        0f, 1f, 1f, 1f

    )

    val TAG: String = "MatrixNormalSquare"
    var vertexSize: Int = 0
    var mProgram: Int = 0

    var matrix: FloatArray? = null
    var lambMatrix: FloatArray? = null
    var mViewMatrix: FloatArray? = null
    var projectionMatrix: FloatArray? = null
    override fun surfaceChanged(width: Int, height: Int) {
        val ratio = height.toFloat() / width
        var lx = 0.5f
        var ly = 0f
        var lz = 0f
        matrix = getOriginalMatrix()
        lambMatrix = getOriginalMatrix()
        mViewMatrix = getOriginalMatrix()
        projectionMatrix = getOriginalMatrix()

        //用于缩放,移动
        Matrix.scaleM(matrix, 0, 0.5f * ratio, 0.5f, 1f)
        Matrix.translateM(lambMatrix, 0, lx, ly, lz)
        Matrix.scaleM(lambMatrix, 0, 0.5f * ratio, 0.5f, 1f)
        //设置相机位置在球中心
        //Matrix.perspectiveM(mProjectMatrix,0,90,ratio,0f,300f);
        //设置相机位置在球上
//        Matrix.setLookAtM(mViewMatrix, 0, 1f, 2f,2f, 0.5f, 0.5f,0.5F, 0f,0f, 0.0f);

        // Calculate the projection and view transformation
//        Matrix.multiplyMM(matrix, 0, projectionMatrix, 0, mViewMatrix, 0)

    }

    override fun initGl() {
        mProgram = ShaderUtils.createProgram(
            MyApp.context!!.resources,
            "matrix/matrix.vert",
            "matrix/matrix.frag"
        )
        vertexSize = triangleCoords.size.div(Utils.COORDS_VERTEX_THREE)

        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        cameraHandle = GLES20.glGetAttribLocation(mProgram, "uCameraMatrix")
//        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor")
        GLES20.glEnable(GLES20.GL_DEPTH_TEST) //Z缓冲
    }

    fun getOriginalMatrix(): FloatArray? {
        return floatArrayOf(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
    }

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0
    private var cameraHandle: Int =0

    // Use to access and set the view transformation
    private var vPMatrixHandle: Int = 0



    override fun drawGraphical() {
        GLES20.glClear(GL_DEPTH_BUFFER_BIT)
//        Matrix.rotateM(matrix, 0, 1f, 0f, 1f, 0f)
//        Matrix.setRotateM(matrix, 0, 1f, 0f, 1f, 0f)
        Matrix.rotateM(matrix, 0, 1f, 0.5f, 0.5F, 0.5f)
        Matrix.rotateM(lambMatrix, 0, -1f, 0.5f, 0.5F, 0.5f)

        GLES20.glUseProgram(mProgram)
        GLES20.glEnableVertexAttribArray(positionHandle)
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            positionHandle,
            Utils.COORDS_VERTEX_THREE,
            GLES20.GL_FLOAT,
            false,
            Utils.COORDS_VERTEX_THREE * 4,
            Utils.ArrayToBuffer(triangleCoords)
        )

        //设置绘制三角形的颜色
//        GLES20.glUniform4fv(mColorHandle, 2, color, 0);
        GLES20.glEnableVertexAttribArray(mColorHandle)
        Log.e(TAG, "mColorHandle $mColorHandle")
        GLES20.glVertexAttribPointer(
            mColorHandle, 4,
            GLES20.GL_FLOAT, false,
            0, Utils.ArrayToBuffer(color)
        )
        //直接设置color属性的值
        //GLES20.glUniform4fv(mColorHandle, 1, color, 0)
        //将投影和视图变换传递给着色器
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, matrix, 0)
        GLES20.glDrawArrays(
            GLES20.GL_TRIANGLES,
            0,
            vertexSize
        )
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, lambMatrix, 0)
        GLES20.glDrawArrays(
            GLES20.GL_TRIANGLES,
            0,
            vertexSize
        )
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}