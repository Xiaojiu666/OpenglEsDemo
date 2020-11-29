package com.sn.opengl.camera.camera2

import android.graphics.SurfaceTexture
import android.util.Size
import android.view.SurfaceHolder

/**
 * Created by GuoXu on 2020/9/6 11:20.
 */
abstract class CameraLoader protected constructor() {
    /**
     * 打开相机
     */
    abstract fun openCamera()
    abstract fun closeCamera()
    abstract fun showLoading()
    abstract fun switchCamera()
    abstract val cameraOrientation: Int
    abstract val cameraPreViews: Array<Size?>?
    abstract fun setCameraId(cameraId: String?)
    abstract val cameraIds: List<String?>?
    abstract fun setPreviewSurface(holder: SurfaceHolder?)
    abstract fun setPreviewSurface(surfaceTexture: SurfaceTexture?)
}