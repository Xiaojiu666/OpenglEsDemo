package com.sn.opengl.shader

/**
 * Created by GuoXu on 2020/11/19 19:38.
 *
 */
object VertexShader {
    val VertexShaderBase = "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = vPosition;" +
            "}"

    val VertexShaderCode_MVPMatrix =
    // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"
     const val VERTEX_SHADER_TEXTURE = """
        attribute vec4 vPosition;
        attribute vec2 vCoordinate;
        varying vec2 aCoordinate;
        void main() {
        gl_Position = vPosition;
        aCoordinate = vCoordinate;
        }
        """
    //带纹理的顶点着色器
    val VertexShaderCode_Texture =
        "attribute vec4 av_Position;\n" +
                "attribute vec2 af_Position;\n" +
                "varying vec2 v_texPo;\n" +
                "void main() \n" +
                "    v_texPo = af_Position;\n" +
                "    gl_Position = av_Position;\n" +
                "}"
}