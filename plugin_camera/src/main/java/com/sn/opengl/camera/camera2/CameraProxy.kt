package com.sn.opengl.camera.camera2

import android.util.Size

/**
 * 相机代理接口
 * Created by GuoXu on 2020/9/6 11:20.
 */
interface CameraProxy {
    /**
     * 打开相机
     */
    fun openCamera(cameraId: String?)

    /**
     * 关闭相机
     */
    fun closeCamera()

    /**
     * 关闭相机
     */
    fun switchCamera(cameraId: String?)

    /**
     * 画面角度
     */
    fun cameraOrientation(surfaceRotation: SurfaceRotation)

    /**
     * 闪光灯
     */
    fun takePhoto(filePath: String?, openFlashback: Boolean)

    fun takePhoto(filePath: String?)

    fun recordVideo(filePath: String?)

//    abstract fun setPreviewSurface(holder: SurfaceHolder?)
//    abstract fun setPreviewSurface(surfaceTexture: SurfaceTexture?)
}