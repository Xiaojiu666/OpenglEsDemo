package com.sn.opengl.weight

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.Button
import com.sn.opengl.R

@SuppressLint("AppCompatCustomView")
class ShutterButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {

    var mPaint: Paint? = null
    var outerRadius = 0
    var innerRadius = 0
    var outerCircleColor = 0
    var innerCircleColor = 0
    var recordColor = 0
    var rect: RectF? = null
    val TAG = ShutterButton::class.java.simpleName
    val PHOTO_MODE = "photo_mode"
    val VIDEO_MODE = "video_mode"
    val VIDEO_RECORDING_MODE = "video_record_mode"

    var currentMode = PHOTO_MODE

    init {
        outerRadius = context.resources.getDimension(R.dimen.shutter_outer_radius).toInt()
        outerCircleColor = context.resources.getColor(R.color.outer_circle_color)
        innerCircleColor = context.resources.getColor(R.color.inner_circle_color)
        recordColor = context.resources.getColor(R.color.color_record)
        mPaint = Paint()
        mPaint!!.setAntiAlias(true)
        rect = RectF()
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        innerRadius = (width / 2 - 2.5 * outerRadius).toInt()
        val innerSmallRadius = width / 10
        rect!!.left = width / 2 - innerSmallRadius.toFloat()
        rect!!.top = height / 2 - innerSmallRadius.toFloat()
        rect!!.right = width / 2 + innerSmallRadius.toFloat()
        rect!!.bottom = height / 2 + innerSmallRadius.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        when (currentMode) {
            PHOTO_MODE -> {
                drawOuterCircle(canvas, outerCircleColor)
                drawInnerCircle(canvas, innerCircleColor)
            }
            VIDEO_MODE -> {
                drawOuterCircle(canvas, recordColor)
                drawInnerCircle(canvas, innerCircleColor)
            }
            VIDEO_RECORDING_MODE -> {
                drawOuterCircle(canvas, innerCircleColor)
                drawInnerCircle(canvas, recordColor)
            }
        }
    }

    fun setMode(mode: String) {
        currentMode = mode
        invalidate()
    }

    private fun drawOuterCircle(canvas: Canvas, color: Int) {
        mPaint!!.color = color
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = outerRadius.toFloat()
        canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            width / 2 - outerRadius.toFloat(),
            mPaint!!
        )
    }

    private fun drawInnerCircle(canvas: Canvas, color: Int) {
        mPaint!!.color = color
        mPaint!!.style = Paint.Style.FILL
        canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            innerRadius.toFloat(),
            mPaint!!
        )
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        alpha = if (enabled) {
            1.0f
        } else {
            0.5f
        }
    }

    override fun setClickable(clickable: Boolean) {
        super.setClickable(clickable)
        alpha = if (clickable) {
            1.0f
        } else {
            0.5f
        }
    }
}