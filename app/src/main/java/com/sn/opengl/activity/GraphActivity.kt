package com.sn.opengl.activity

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.sn.opengl.R
import com.sn.opengl.menu.GraphType
import com.sn.opengl.util.GraphTypeDialog
import com.sn.plugin_opengl.graph.Graph
import kotlinx.android.synthetic.main.activity_graph.*
import java.util.*

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
//        button2.setOnClickListener {
//            myGlSurfaceView.setNewFilter(Graph(baseContext))
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_graph, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.menu_select_graph -> {
                GraphTypeDialog.showDialog(this@GraphActivity,listener = {
                    myGlSurfaceView.setNewFilter(it)
                })
            }
        }
        return true
    }

}