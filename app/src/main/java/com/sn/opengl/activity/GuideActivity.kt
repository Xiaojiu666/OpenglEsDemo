package com.sn.opengl.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sn.opengl.R
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        btn_graph.setOnClickListener {
            startGraphActivity(GraphActivity().javaClass)
        }
    }

    private fun startGraphActivity(javaClass: Class<GraphActivity>) {
        var intent = Intent (this,GraphActivity().javaClass)
        startActivity(intent)
    }
}