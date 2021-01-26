package com.sn.opengl.camera.camera2

import android.app.Activity
import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.util.Size
import android.view.Surface

class CameraHelper(var mContext: Activity) {

    var cameraProxy: CameraProxy? = null
    var cameraManager: CameraManager? = null

    init {
        if (android.os.Build.VERSION.SDK_INT > 21) {
            cameraManager = mContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            cameraProxy = Camera2Proxy(cameraManager!!)
        } else {
            throw NullPointerException("Did not fit Camera1，/(ㄒoㄒ)/~~")
        }
    }

    fun openCamera(cameraId: String?) {
        cameraProxy?.openCamera(cameraId)
    }

    fun closeCamera() {
        cameraProxy?.closeCamera()
    }

    fun switchCamera(cameraId: String?) {
        cameraProxy?.switchCamera(cameraId)
    }

    fun cameraOrientation(surfaceRotation: SurfaceRotation) {
        cameraProxy?.cameraOrientation(surfaceRotation)
    }

    fun takePhoto(filePath: String?, openFlashback: Boolean) {
        cameraProxy?.takePhoto(filePath, openFlashback)
    }

    fun takePhoto(filePath: String?) {
        cameraProxy?.takePhoto(filePath)
    }

    fun recordVideo(filePath: String?) {
        cameraProxy?.recordVideo(filePath)
    }

    /*内部类*/
    inner  class Camera2Builder() {
        private var mPreviewSurface: Surface? = null
        private var cameraId: String? = null
        private var mImageReader: ImageReader? = null
        private var ratio: Size? = null

        fun setCameraId(cameraId: String?): Camera2Builder {
            this.cameraId = cameraId
            return this
        }

//        fun setPreviewSurface(mPreviewSurface: Surface?): Camera2Builder {
//            this.mPreviewSurface = mPreviewSurface
//            cameraProxy?.openCamera(cameraId)
//            return this
//        }

        fun openCamera(): CameraHelper {
            cameraProxy?.openCamera(cameraId)
            return this@CameraHelper
        }
    }
}