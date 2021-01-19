package com.sn.opengl.camera.camera2

import android.graphics.SurfaceTexture
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Size
import android.view.Surface
import android.view.TextureView.SurfaceTextureListener
import com.sn.opengl.camera.R
import com.sn.opengl.camera.camera2.manager.DeviceCharacteristicManager
import com.sn.opengl.camera.camera2.manager.DeviceManager
import com.sn.opengl.camera.camera2.view.AutoFitGlSurfaceView
import com.sn.opengl.camera.camera2.view.AutoFitTextureView

/**
 * Created by GuoXu on 2020/12/2 16:46.
 */
class CameraDemoActivity : AppCompatActivity(),
    AutoFitGlSurfaceView.GlSurfaceViewListener {

    var PREVIVEW_DEFAULT_SIZE = Size(9, 16)
    var bestPreviewSize: Size? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val glSurfaceView = findViewById<AutoFitGlSurfaceView>(R.id.glSurfaceView)
        glSurfaceView.setGlSurfaceViewListener(this)
        initGlSurfaceView(glSurfaceView)
//        val textureView = findViewById<AutoFitTextureView>(R.id.textureView)
//        initTexttureView(textureView)
    }

    private fun initTexttureView(textureView: AutoFitTextureView) {
        val deviceManager = DeviceManager(baseContext)
        val deviceCharacteristicManager =
            DeviceCharacteristicManager(deviceManager.getCharacteristics("0"))
        bestPreviewSize = deviceCharacteristicManager.getBestPreviewSize(PREVIVEW_DEFAULT_SIZE)
        textureView.setSurfaceTextureListener(object : SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                textureView.setAspectRatio(bestPreviewSize!!.getWidth(), bestPreviewSize!!.getHeight())
                Camera2Loader.Builder(this@CameraDemoActivity)
                    .setCameraId("0")
                    .setRatio(Size(1, 1))
                    .setPreviewSurface(Surface(surface))
                    .openCamera()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
        })
    }

    private fun initGlSurfaceView(glSurfaceView: AutoFitGlSurfaceView) {
        val deviceManager = DeviceManager(baseContext)
        val deviceCharacteristicManager =
            DeviceCharacteristicManager(deviceManager.getCharacteristics("0"))
        bestPreviewSize = deviceCharacteristicManager.getBestPreviewSize(PREVIVEW_DEFAULT_SIZE)
        glSurfaceView.setAspectRatio(bestPreviewSize!!)
    }

    override fun onSurfaceCreated(surface: Surface) {
        Camera2Loader.Builder(this@CameraDemoActivity)
            .setCameraId("0")
            .setRatio(Size(1, 1))
            .setPreviewSurface(surface)
            .openCamera()
    }
}