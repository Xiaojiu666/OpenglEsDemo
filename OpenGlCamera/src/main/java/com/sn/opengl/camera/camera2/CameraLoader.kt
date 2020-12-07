package com.sn.opengl.camera.camera2

import android.graphics.SurfaceTexture
import android.util.Size
import android.view.SurfaceHolder

/**
 * Created by GuoXu on 2020/9/6 11:20.
 */
interface CameraLoader {
    /**
     * 打开相机
     */
    abstract fun openCamera(cameraId: String?)
    abstract fun closeCamera()
    abstract fun switchCamera(cameraId: String?)
    abstract val cameraOrientation: Int
    abstract val cameraPreViews: Array<Size?>?
    abstract val cameraIds: List<String?>?
//    abstract fun setPreviewSurface(holder: SurfaceHolder?)
//    abstract fun setPreviewSurface(surfaceTexture: SurfaceTexture?)
}