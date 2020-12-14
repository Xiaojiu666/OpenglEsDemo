package com.sn.opengl.camera.camera2.draw

import android.content.Context
import com.sn.plugin_opengl.utils.ShaderUtils.createProgram
import java.nio.FloatBuffer

open class BaseDraw(var vertexShader: String, var fragmentShader: String) {
     var mProgram = 0

    open fun onInit(context: Context) {
        mProgram = createProgram(
            context.resources, vertexShader
            , fragmentShader
        )
    }

    open fun onDraw(textureId:Int,cubeBuffer: FloatBuffer,textureBuffer: FloatBuffer){

    }

}