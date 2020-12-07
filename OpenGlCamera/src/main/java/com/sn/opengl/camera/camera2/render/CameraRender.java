package com.sn.opengl.camera.camera2.render;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

import com.sn.opengl.camera.camera2.Camera2Loader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by GuoXu on 2020/9/9 17:07.
 */
public class CameraRender implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    private static final String TAG = "CameraRender";
    public Activity mContext;
    private int[] texture;
    private CameraDrawer1 mDrawer1;

    public CameraRender(Activity mContext) {
        this.mContext = mContext;
    }

    private SurfaceTexture mSurfaceTexture;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (mRenderListener != null) {
            mRenderListener.onSurfaceCreated(gl, config);
        }
        texture = new int[1];
        GLES20.glGenTextures(1, texture, 0);
        // OES纹理坐标配置 绘制YUV格式图片
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        mDrawer1 = new CameraDrawer1();
        mSurfaceTexture = new SurfaceTexture(texture[0]);
        mSurfaceTexture.setDefaultBufferSize(4000, 3000);
        //监听有新图像到来
        mSurfaceTexture.setOnFrameAvailableListener(this);
        new Camera2Loader.Builder((Activity) mContext)
                .setCameraId("0")
                .setRatio(new Size(1, 1))
                .setPreviewSurface(new Surface(mSurfaceTexture))
                .openCamera();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.e(TAG, "onSurfaceChanged");
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e(TAG, "onDrawFrame");
        GLES20.glClearColor(0, 0, 0, 0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        mSurfaceTexture.updateTexImage();
        mDrawer1.draw(texture[0], false);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        Log.e(TAG, "onFrameAvailable");
    }

    public RenderListener mRenderListener;

    public void setRenderListener(RenderListener mRenderListener) {
        this.mRenderListener = mRenderListener;
    }

    interface RenderListener {
        void onSurfaceCreated(GL10 var1, EGLConfig var2);

        void onSurfaceChanged(GL10 var1, int var2, int var3);

        void onDrawFrame(GL10 var1);
    }
}
