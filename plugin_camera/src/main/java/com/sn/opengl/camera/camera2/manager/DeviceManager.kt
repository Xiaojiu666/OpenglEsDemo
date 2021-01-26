package com.sn.opengl.camera.camera2.manager

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.params.StreamConfigurationMap
import android.util.Size
import android.view.WindowManager
import java.util.*

/**
 * Use for get basic camera info, not for open camera
 */
open class DeviceManager(context: Context) {

    companion object {
        const val TAG = "DeviceManager"
    }

    var cameraManager: CameraManager? = null

    init {
        cameraManager =
            context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    /**
     * 根据相机ID 获取特点对象
     *  CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL
     */
    fun getCharacteristics(cameraId: String?): CameraCharacteristics? {
        try {
            return cameraManager?.getCameraCharacteristics(cameraId!!)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 可使用相机列表
     */
    val cameraIdList: Array<String>?
        get() {
            try {
                return cameraManager?.cameraIdList
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            return null
        }


//    open fun getPreviewSizeList(map: StreamConfigurationMap): Array<String?>? {
//        val supportSize = map.getOutputSizes(
//            SurfaceTexture::class.java
//        )
////        com.smewise.camera2.utils.CameraUtil.sortCamera2Size(supportSize)
////        val sizeStr = arrayOfNulls<String>(supportSize.size)
////        for (i in supportSize.indices) {
////            sizeStr[i] = supportSize[i].width
////                .toString() + com.smewise.camera2.utils.CameraUtil.SPLIT_TAG + supportSize[i]
////                .height
////        }
//        return sizeStr
//    }

//     open fun sortCamera2Size(sizes: Array<Size>) {
//         val comparator: Comparator<Size?> =
//             object : Comparator<Size?> {
//
//                 override fun compare(p0: Size?, p1: Size?): Int {
//                     return p1.width * o2.height - o1.width * o1.height
//                 }
//             }
//         Arrays.sort(sizes, comparator)
////        Arrays.sort(sizes, comparator)
//    }
    /**
     * For camera open/close event
     */
    abstract class CameraEvent {
        fun onDeviceOpened(device: CameraDevice?) {
            // default empty implementation
        }

        fun onAuxDeviceOpened(device: CameraDevice?) {
            // default empty implementation
        }

        fun onDeviceClosed() {
            // default empty implementation
        }
    }


}