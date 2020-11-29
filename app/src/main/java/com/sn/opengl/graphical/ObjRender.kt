package com.sn.opengl.graphical

import android.opengl.GLES20
import com.sn.opengl.BaseRender
import com.sn.opengl.BaseSurfaceView
import com.sn.opengl.MyApp
import com.sn.opengl.obj.LoadUtil

/**
 * Created by GuoXu on 2020/11/27 16:01.
 *
 */
class ObjRender : BaseRender() {
    override fun surfaceChanged(width: Int, height: Int) {
    }

    override fun initGl() {

    }

    override fun drawGraphical() {
        //设置屏幕背景色RGBA
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //打开背面剪裁
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        //初始化变换矩阵
        MatrixState.setInitStack();
        //加载要绘制的物体
        lovo = LoadUtil.loadFromFile(
            "ch.obj", MyApp.context?.resources,baseSurfaceView
        )
    }
}