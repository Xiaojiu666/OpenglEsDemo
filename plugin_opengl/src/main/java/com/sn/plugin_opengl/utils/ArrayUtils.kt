package com.sn.plugin_opengl.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by GuoXu on 2020/11/20 14:23.
 *
 */
class ArrayUtils {
    companion object {
        fun transformFloat(array: FloatArray): FloatBuffer {
            return ByteBuffer.allocateDirect(array.size * 4).run {
                // use the device hardware's native byte order
                order(ByteOrder.nativeOrder())
                // create a floating point buffer from the ByteBuffer
                asFloatBuffer().apply {
                    // add the coordinates to the FloatBuffer
                    put(array)
                    // set the buffer to read the first coordinate
                    position(0)
                }
            }
        }
    }
}

