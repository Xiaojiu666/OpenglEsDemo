package com.sn.opengl

import android.app.Application
import android.content.Context

/**
 * Created by GuoXu on 2020/11/23 16:30.
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        val context: Context
            get() = context
    }
}