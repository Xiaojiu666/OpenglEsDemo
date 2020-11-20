package com.sn.opengl.shader

/**
 * Created by GuoXu on 2020/11/19 19:38.
 *
 */
object FragmentShader {

     val FragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"


}