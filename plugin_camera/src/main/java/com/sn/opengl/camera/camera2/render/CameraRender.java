package com.sn.opengl.camera.camera2.render;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by GuoXu on 2020/9/9 17:07.
 */
public class CameraRender implements GLSurfaceView.Renderer{

    private static final String TAG = "CameraRender";
    public Activity mContext;
    private int[] texture= new int[1];
    private CameraDrawer1 mDrawer1;

    public CameraRender(Activity mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glGenTextures(1, texture, 0);
        // OES纹理坐标配置 绘制YUV格式图片
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        mDrawer1 = new CameraDrawer1();
        if (mRenderListener != null) {
            mRenderListener.onSurfaceTextureCreated(new SurfaceTexture(texture[0]));
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.e(TAG, "onSurfaceChanged");
        mDrawer1.setSize(width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e(TAG, "onDrawFrame");
        GLES20.glClearColor(0, 0, 0, 0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        if (mRenderListener != null) {
            mRenderListener.onDrawFrame();
        }

        mDrawer1.draw(texture[0], false);
    }


    public OnSurfaceTextureListener mRenderListener;

    public void setRenderListener(OnSurfaceTextureListener mRenderListener) {
        this.mRenderListener = mRenderListener;
    }


    public interface OnSurfaceTextureListener {
        void onDrawFrame();

        void onSurfaceTextureCreated(SurfaceTexture surfaceTexture);
    }
}
