package com.sn.opengl

import android.app.Application
import android.content.Context
import kotlin.coroutines.coroutineContext

/**
 * Created by GuoXu on 2020/11/23 16:30.
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        var context: Context? = null
            private set
    }
}