package com.sn.opengl.camera.camera2

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.hardware.camera2.CameraCaptureSession.CaptureCallback
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.WindowManager
import com.sn.opengl.camera.camera2.manager.DeviceManager
import java.util.*

/**
 * Created by GuoXu on 2020/12/2 15:03.
 */
class Camera2Loader(var mContext: Activity) : DeviceManager(mContext), CameraLoader {

    var mPreviewSurface: Surface? = null
    var cameraId: String? = "0"
    var resolutionRatio: Size? = null
    var openFlashLamp = false
    private var mCameraDevice: CameraDevice? = null
    private var mCameraCaptureSession: CameraCaptureSession? = null

    /**
     * 相机回调线程
     */
    private val handlerThread = HandlerThread("Camera2Thread")
    private var mCameraHandler: Handler? = null
    private var mImageReader: ImageReader? = null

    companion object {
        private const val TAG = "Camera2manager"
    }

    init {
        initThread()
    }

    private fun initThread() {
        handlerThread.start()
        mCameraHandler = Handler(handlerThread.looper)
    }

    override fun openCamera(cameraId: String?) {
        try {
            if (ActivityCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e(TAG, "No permission.CAMERA ")
                return
            }
            cameraManager!!.openCamera(cameraId!!, mStateCallBack, mCameraHandler)
            Log.e(TAG, "openCamera $cameraId")
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    /**
     * CAMER2 相机连接状态回调
     */
    private val mStateCallBack: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
           Log.e(TAG, "onOpened")
            mCameraDevice = camera
            createCaptureSession(camera)
        }

        override fun onDisconnected(camera: CameraDevice) {
            closeCamera()
            Log.e(TAG, "onDisconnected")
        }

        override fun onError(camera: CameraDevice, error: Int) {
            closeCamera()
            Log.e(TAG, "onError $error")
        }
    }

    fun sendRepeatingRequest(
        request: CaptureRequest?,
        callback: CaptureCallback?, handler: Handler?
    ) {
        try {
            mCameraCaptureSession!!.setRepeatingRequest(request!!, callback, handler)
        } catch (e: CameraAccessException) {
            Log.e(
                TAG,
                "send repeating request error:" + e.message
            )
        } catch (e: IllegalStateException) {
            Log.e(
                TAG,
                "send repeating request error:" + e.message
            )
        }
    }

    private fun createCaptureSession(cameraDevice: CameraDevice) {
        if (mPreviewSurface == null) {
            Log.e(TAG, "mPreviewSurface is null")
            return
        }
        val captureRequestBuilder: CaptureRequest.Builder
        try {
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(mPreviewSurface!!) // 将CaptureRequest的构建器与Surface对象绑定在一起
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AE_MODE,
                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
            )
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AF_MODE,
                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
            ) // 自动对焦
            val surfaces =
                ArrayList<Surface>()
            surfaces.add(mPreviewSurface!!)
            if (mImageReader != null) {
                surfaces.add(mImageReader!!.surface)
            }
            try {
                cameraDevice.createCaptureSession(
                    surfaces,
                    object : CameraCaptureSession.StateCallback() {
                        override fun onConfigured(session: CameraCaptureSession) {
                            mCameraCaptureSession = session
                            sendRepeatingRequest(
                                captureRequestBuilder.build(),
                                null,
                                mCameraHandler
                            )
                        }
                        override fun onConfigureFailed(session: CameraCaptureSession) {
                            Log.e(TAG, "开启预览会话失败")
                        }
                    },
                    mCameraHandler
                )
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun closeCamera() {
        if (mCameraCaptureSession != null) {
            mCameraCaptureSession!!.close()
            mCameraCaptureSession = null
        }
        if (mCameraDevice != null) {
            mCameraDevice!!.close()
            mCameraDevice = null
        }
        if (mImageReader != null) {
            mImageReader!!.close()
            mImageReader = null
        }
    }

    override fun switchCamera(cameraId: String?) {}


    override val cameraOrientation: Int
        get() = 0

    override val cameraPreViews: Array<Size?>?
        get() = arrayOfNulls(0)

    override val cameraIds: List<String>?
        get() = null


    /*静态内部类*/
    class Builder(val mContext: Activity) {
        private var mPreviewSurface: Surface? = null
        private var cameraId: String? = null
        private var openFlashLamp = false
        private var mImageReader: ImageReader? = null
        private var ratio: Size? = null
        fun setImageReader(mImageReader: ImageReader?): Builder {
            this.mImageReader = mImageReader
            return this
        }

        fun setPreviewSurface(mPreviewSurface: Surface?): Builder {
            this.mPreviewSurface = mPreviewSurface
            return this
        }

        fun setCameraId(cameraId: String?): Builder {
            this.cameraId = cameraId
            return this
        }

        fun setOpenFlashLamp(openFlashLamp: Boolean): Builder {
            this.openFlashLamp = openFlashLamp
            return this
        }

        /**
         *  View画面比例 自动适配相机分辨率
         */
        fun setRatio(ratio: Size): Builder {
            this.ratio = ratio
            return this
        }

        fun openCamera(): Camera2Loader {
            val camera2Loader = Camera2Loader(mContext)
            camera2Loader.mPreviewSurface = mPreviewSurface
            camera2Loader.openFlashLamp = openFlashLamp
            camera2Loader.resolutionRatio = ratio
            camera2Loader.openCamera(cameraId)
            return camera2Loader
        }
    }
}