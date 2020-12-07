package com.sn.opengl.camera.camera2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Size
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback2
import android.view.SurfaceView
import com.sn.opengl.camera.R
import com.sn.opengl.camera.camera2.manager.DeviceCharacteristicManager
import com.sn.opengl.camera.camera2.manager.DeviceManager
import com.sn.opengl.camera.camera2.view.AutoFixGlSurfaceView

/**
 * Created by GuoXu on 2020/12/2 16:46.
 */
class CameraDemoActivity : AppCompatActivity() {

    var PREVIVEW_DEFAULT_SIZE = Size(1, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val glSurfaceView = findViewById<AutoFixGlSurfaceView>(R.id.glSurfaceView)
        val deviceManager = DeviceManager(baseContext)
        val deviceCharacteristicManager =
            DeviceCharacteristicManager(deviceManager.getCharacteristics("0"))
        val bestPreviewSize = deviceCharacteristicManager.getBestPreviewSize(PREVIVEW_DEFAULT_SIZE)
        glSurfaceView.setAspectRatio(bestPreviewSize)
    }

    fun surfaceCreated(holder: SurfaceHolder) {

    }


}