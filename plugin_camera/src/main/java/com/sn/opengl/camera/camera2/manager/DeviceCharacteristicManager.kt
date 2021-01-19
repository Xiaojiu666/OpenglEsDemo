package com.sn.opengl.camera.camera2.manager

import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.params.StreamConfigurationMap
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Size
import java.util.*

/**
 * Created by GuoXu on 2020/12/7 15:51.
 * 相机属性管理类
 */
class DeviceCharacteristicManager(var cameraCharacteristics: CameraCharacteristics?) {


    companion object {
        const val TAG = "DeviceCharacteristicManager"
    }

    /**
     *获取图片输出的尺寸和预览画面输出的尺寸
     *  getOutputSizes(ImageFormat.JPEG);
     *  getOutputSizes(SurfaceTexture.class）
     */
    fun getConfigMap(): StreamConfigurationMap? {
        try {
            return cameraCharacteristics?.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据比例 获取相机最佳分辨率
     * */
    fun getBestPreviewSize(size: Size): Size {
        var ratio = 0;
        ratio = if (size.width > size.height) {
            size.width * 10 / size.height * 10
        } else {
            size.height * 10 / size.width * 10
        }

        val configMap = getConfigMap()
        val supportSize: Array<Size>? = configMap?.getOutputSizes(
            SurfaceTexture::class.java
        )
        sortCamera2Size(supportSize)
        for (size in supportSize!!) {
            Log.e(TAG, "supportSize $size")
            var previewRatio = 0;
            previewRatio = if (size.width > size.height) {
                size.width * 10 / size.height * 10
            } else {
                size.height * 10 / size.width * 10
            }
            Log.e(TAG, "ratio $ratio  previewRatio$previewRatio")
            if (ratio == previewRatio) {
                Log.e(TAG, "bestSize $size")
                return size;
            }
        }
        return Size(1920, 1080)
    }

    /**
     * 根据分辨率排序
     */
    private fun sortCamera2Size(sizes: Array<Size>?) {
        val comparator: Comparator<Size?> =
            Comparator<Size?> { p0, p1 ->
                p1?.width?.times(p1.height)?.minus(p0?.width?.times(p0.height)!!)!!
            }
        Arrays.sort(sizes, comparator)
    }

}