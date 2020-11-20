package com.sn.opengl.graphical

import android.opengl.GLES20
import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log
import com.sn.opengl.BaseRender
import com.sn.opengl.GraphicalAttribute
import com.sn.opengl.Utils
import com.sn.opengl.shader.FragmentShader
import com.sn.opengl.shader.VertexShader


/**
 * Created by GuoXu on 2020/11/19 19:49.
 *
 */
class RotateSquare(var graphicalAttribute: GraphicalAttribute) : BaseRender() {


    val TAG: String = "MatrixNormalSquare"

    var vertexSize: Int = 0
    var mProgram: Int = 0
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    val vPMatrix = FloatArray(16)
    val projectionMatrix = FloatArray(16)
    val viewMatrix = FloatArray(16)
    val rotationMatrix = FloatArray(16)

    override fun surfaceChanged(width: Int, height: Int) {
        val ratio = width.toFloat() / height
        Log.e(TAG, "ratio $ratio")
        Matrix.frustumM(projectionMatrix, 0, ratio, -ratio, -1f, 1f, 3f, 7f)
    }

    override fun initGl() {
        val vertexShader: Int =
            loadShader(GLES20.GL_VERTEX_SHADER, VertexShader.VertexShaderCode_MVPMatrix)
        val fragmentShader: Int =
            loadShader(GLES20.GL_FRAGMENT_SHADER, FragmentShader.FragmentShaderCode)
        vertexSize =
            graphicalAttribute.vertextCoords?.size?.div(Utils.COORDS_VERTEX_THREE)!!
        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram().also {
            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(it)
        }
    }

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    // Use to access and set the view transformation
    private var vPMatrixHandle: Int = 0


    override fun drawGraphical() {
        val scratch = FloatArray(16)
        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        // Create a rotation transformation for the triangle
        val time = SystemClock.uptimeMillis() % 4000L
        val angle = 0.090f * time.toInt()
        Log.e(TAG, "angle $angle")
        // X\Y\Z -1~1  顺序不同
        Matrix.setRotateM(rotationMatrix, 0, angle, 1f, 0f, 0f)
        // Combine the rotation matrix with the projection and camera view
        // Note that the vPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)

        // Create a rotation transformation for the triangle
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)
        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle)
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            positionHandle,
            Utils.COORDS_VERTEX_THREE,
            GLES20.GL_FLOAT,
            false,
            Utils.COORDS_VERTEX_THREE * 4,
            graphicalAttribute.vertexCoordsBuffer
        )

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0)
        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        //将投影和视图变换传递给着色器
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, scratch, 0)
        GLES20.glDrawArrays(
            GLES20.GL_TRIANGLE_STRIP,
            0,
            vertexSize
        )
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}