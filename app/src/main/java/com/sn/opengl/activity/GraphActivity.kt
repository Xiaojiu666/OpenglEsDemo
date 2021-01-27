package com.sn.opengl.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sn.opengl.R
import com.sn.plugin_opengl.graph.GraphColorTriangle
import kotlinx.android.synthetic.main.activity_graph.*

class GraphActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        button2.setOnClickListener {
            myGlSurfaceView.setNewFilter(GraphColorTriangle(baseContext))
        }
    }
}