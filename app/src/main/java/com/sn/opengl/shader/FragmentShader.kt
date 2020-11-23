package com.sn.opengl.shader

/**
 * Created by GuoXu on 2020/11/19 19:38.
 *
 */
object FragmentShader {

    const val FragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"

    val FragmentShaderCode_Texture = "precision mediump float;\n" +
            "    varying vec2 v_texPo;\n" +
            "    uniform sampler2D sTexture;\n" +
            "    void main() {\n" +
            "        gl_FragColor=texture2D(sTexture, v_texPo);\n" +
            "    }"


}