package com.sn.opengl.util

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import com.sn.opengl.menu.GraphType
import com.sn.opengl.menu.GraphType.*
import com.sn.plugin_opengl.filter.Filter
import com.sn.plugin_opengl.graph.Graph
import com.sn.plugin_opengl.graph.GraphColorRect
import com.sn.plugin_opengl.graph.GraphColorTriangle
import com.sn.plugin_opengl.graph.GraphRect
import java.util.*

object GraphTypeDialog {

    val TAG = "GraphTypeDialog"

    fun showDialog(context: Context,  listener: (filter: Filter) -> Unit) {
        Log.e("TAG", "showDialog")
        val filters = FilterList().apply {
            addFilter("三角形", TRIANGLE)
            addFilter("正方形", RECT)
            addFilter("彩色三角形", TRIANGLE_COLOR)
            addFilter("彩色正方形", RECT_COLOR)
        }
        val builder = AlertDialog.Builder(context)
        builder.setTitle("请选择一个图形")
        builder.setItems(filters.names.toTypedArray()) { _, item ->
            listener(createFilterForType(context, filters.filters[item]))
        }
        builder.create().show()
    }


    private fun createFilterForType(context: Context, type: GraphType): Filter {
        return when (type) {
            TRIANGLE -> Graph(context)
            RECT -> GraphRect(context)
            TRIANGLE_COLOR -> GraphColorTriangle(context)
            RECT_COLOR ->GraphColorRect(context)
        }
    }



    private class FilterList {
        val names: MutableList<String> = LinkedList()
        val filters: MutableList<GraphType> = LinkedList()

        fun addFilter(name: String, filter: GraphType) {
            names.add(name)
            filters.add(filter)
        }
    }

}