package com.sn.opengl.graphical

import android.opengl.GLES20
import android.opengl.Matrix
import android.util.Log
import com.sn.opengl.BaseRender
import com.sn.opengl.MyApp
import com.sn.opengl.ShaderUtils
import com.sn.plugin_opengl.utils.ArrayUtils

import kotlin.math.cos


/**
 * Created by GuoXu on 2020/11/19 19:49.
 *  用于测试 观察矩阵 计算后的 Camera Space
 *  参考资料 : https://blog.csdn.net/jamesshaoya/article/details/54342241
 */
class CameraViewMatrixDemoRender : BaseRender() {

    var color = floatArrayOf(
        1f, 0f, 0f, 1f,
        1f, 0f, 0f, 1f,
        0f, 1f, 0f, 1f,
        0f, 1f, 0f, 1f,
        0f, 0f, 1f, 1f,
        0f, 0f, 1f, 1f
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
        //https://blog.csdn.net/jamesshaoya/article/details/54342241
//        CameraMatrix()
    }

    var span = (Math.PI / 180).toFloat()
    var i = 0
    /**
     * 旋转相机矩阵
     */
    private fun CameraMatrix() {
        i += 1
        if (i >= 360) {
            i = 0
        }
        val a = span * i
        var eyeX = (cos(a.toDouble())).toFloat() * 2
        var eyeZ = Math.sin(a.toDouble()).toFloat() * 2
        Log.e(TAG, "eyeX $eyeX , eyeZ $eyeZ")
        //投影转换矩阵
        Matrix.frustumM(projectionMatrix, 0, -1f, 1f, -1f, 1f, 2f, 100f)
        //视见转换矩阵
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, 0f, eyeZ, 0f, 0f, 0f, 0f, 1f, 0f)
        Matrix.multiplyMM(matrix, 0, projectionMatrix, 0, mViewMatrix, 0)
    }


    override fun initGl() {
        mProgram = ShaderUtils.createProgram(
            MyApp.context!!.resources,
            "cameramatrix/cameramatrix.vert",
            "cameramatrix/cameramatrix.frag"
        )
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor")
    }

    fun getOriginalMatrix(): FloatArray? {
        return floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    }

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0
    private var cameraHandle: Int = 0

    // Use to access and set the view transformation
    private var vPMatrixHandle: Int = 0


    override fun drawGraphical() {
//        Matrix.rotateM(matrix, 0, 1f, 0.5f, 0.5f, 0f)
        // Add program to OpenGL ES environment

        CameraMatrix()
        GLES20.glUseProgram(mProgram)
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glEnableVertexAttribArray(mColorHandle)
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            positionHandle,
            3,
            GLES20.GL_FLOAT,
            false,
            3 * 4,
            ArrayUtils.transformFloat(basevertexData)
        )
        GLES20.glVertexAttribPointer(
            mColorHandle, 4,
            GLES20.GL_FLOAT, false,
            0,  ArrayUtils.transformFloat(color)
        )

        // get handle to fragment shader's vColor member
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, matrix, 0)
        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, baseColor, 0)
        GLES20.glLineWidth(5f)
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, 6)
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(mColorHandle)
    }
}