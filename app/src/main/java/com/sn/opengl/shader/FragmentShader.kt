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


    const val FRAGMENT_SHADER_TEXTURE = """
        precision mediump float;
        uniform vec4 vColor;
        uniform sampler2D vTexture;
        varying vec2 aCoordinate;
        void main() {
        gl_FragColor = texture2D(vTexture,aCoordinate)* vColor ;
        }
        """

}