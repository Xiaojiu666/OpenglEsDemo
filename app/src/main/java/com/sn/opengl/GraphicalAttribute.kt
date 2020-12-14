package com.sn.opengl

import java.nio.FloatBuffer

/**
 * Created by GuoXu on 2020/11/20 15:05.
 */
class GraphicalAttribute {
    var vertexCoordsBuffer: FloatBuffer? = null
    var vertextCoords: FloatArray? = null

    fun setVertexCoords(vertexCoords: FloatArray) {
        this.vertextCoords = vertexCoords;
        vertexCoordsBuffer = Utils.ArrayToBuffer(vertexCoords)
    }

}