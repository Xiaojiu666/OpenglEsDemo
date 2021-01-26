package com.sn.opengl.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Size
import android.view.Menu
import android.view.MenuItem
import android.view.Surface
import com.sn.opengl.R
import com.sn.opengl.camera.camera2.Camera2Proxy
import com.sn.opengl.camera.camera2.CameraHelper
import com.sn.opengl.camera.camera2.manager.DeviceCharacteristicManager
import com.sn.opengl.camera.camera2.manager.DeviceManager
import com.sn.opengl.camera.camera2.view.AutoFitGlSurfaceView
import permissions.dispatcher.RuntimePermissions


/**
 * Created by GuoXu on 2020/12/2 16:46.
 */
@RuntimePermissions
class CameraDemoActivity : AppCompatActivity(),
    AutoFitGlSurfaceView.GlSurfaceViewListener {

    var PREVIVEW_DEFAULT_SIZE = Size(3, 4)
    var bestPreviewSize: Size? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val glSurfaceView = findViewById<AutoFitGlSurfaceView>(R.id.glSurfaceView)
        CameraHelper(this).Camera2Builder().setCameraId("0").openCamera()
//        glSurfaceView.setGlSurfaceViewListener(this)
//        initGlSurfaceView(glSurfaceView)
    }

    private fun initGlSurfaceView(glSurfaceView: AutoFitGlSurfaceView) {
        val deviceManager = DeviceManager(baseContext)
        val deviceCharacteristicManager =
            DeviceCharacteristicManager(deviceManager.getCharacteristics("0"))
        bestPreviewSize = deviceCharacteristicManager.getBestPreviewSize(PREVIVEW_DEFAULT_SIZE)
        glSurfaceView.setAspectRatio(bestPreviewSize!!)
    }

    override fun onSurfaceCreated(surface: Surface) {

//        Camera2Proxy.Builder(this@CameraDemoActivity)
//            .setCameraId("0")
//            .setRatio(Size(1, 1))
//            .setPreviewSurface(surface)
//            .openCamera()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toobar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}