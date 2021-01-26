package com.sn.opengl.camera.camera2

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.hardware.camera2.CameraCaptureSession.CaptureCallback
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.support.annotation.NonNull
import android.util.Log
import android.util.Size
import android.view.Surface
import java.util.*

/**
 * Created by GuoXu on 2020/12/2 15:03.
 */
class Camera2Proxy(var cameraManager: CameraManager) : CameraProxy {

    //    var mPreviewSurface: Surface? = null
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


    private var mPreviewRequest: CaptureRequest? = null

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

    @SuppressLint("MissingPermission")
    override fun openCamera(cameraId: String?) {
        try {
            cameraManager.openCamera(cameraId!!, mStateCallBack, mCameraHandler)
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

    private var imageReader: ImageReader? = null
    private fun createCaptureSession(cameraDevice: CameraDevice) {
//        if (mPreviewSurface == null) {
//            Log.e(TAG, "mPreviewSurface is null")
//            return
//        }
        imageReader =
            ImageReader.newInstance(1920, 1080, ImageFormat.YUV_420_888, 2).apply {
                setOnImageAvailableListener({ reader ->
                    Log.e(TAG, "image");
                    val image = reader?.acquireNextImage() ?: return@setOnImageAvailableListener
                    image.close()
                }, null)
            }
        try {
            val surfaces = ArrayList<Surface>()
            surfaces.add(imageReader!!.surface)
            if (mImageReader != null) {
                surfaces.add(mImageReader!!.surface)
            }
            try {
                cameraDevice.createCaptureSession(surfaces, mSessionStateCallback, mCameraHandler)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private val mSessionStateCallback: CameraCaptureSession.StateCallback =
        object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(@NonNull session: CameraCaptureSession) {
                mCameraCaptureSession = session
                try {
                    mPreviewRequest = createPreviewRequest()
                    if (mPreviewRequest != null) {
                        session.setRepeatingRequest(mPreviewRequest!!, null, mCameraHandler)
                    } else {
                        Log.e(TAG, "captureRequest is null")
                    }
                } catch (e: CameraAccessException) {
                    Log.e(TAG, "onConfigured $e")
                }
            }

            override fun onConfigureFailed(@NonNull session: CameraCaptureSession) {
                Log.e(TAG, "onConfigureFailed")
            }
        }

    private fun createPreviewRequest(): CaptureRequest? {
        return if (null == mCameraDevice) null else
            try {
                val builder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                builder.addTarget(imageReader!!.surface)
                builder.set(
                    CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
                )
                builder.set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                ) // 自动对焦
                builder.build()
            } catch (e: CameraAccessException) {
                Log.e(TAG, e.message!!)
                null
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
    override fun cameraOrientation(surfaceRotation: SurfaceRotation) {
    }

    override fun takePhoto(filePath: String?, openFlashback: Boolean) {
    }

    override fun takePhoto(filePath: String?) {
    }

    override fun recordVideo(filePath: String?) {
    }


}