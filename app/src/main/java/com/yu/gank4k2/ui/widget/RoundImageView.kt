package com.yu.gank4k2.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.yu.gank4k2.R
import java.util.*

/**
 * 支持圆角和圆形的imageview、默认为圆形
 * 如需使用圆角，在布局中使用属性：
 * app:shape_mode="round_rect"
 * app:round_radius="20dp"

 * @author yu
 * *         Create on 16/4/18.
 */
class RoundImageView : android.support.v7.widget.AppCompatImageView {

    private var mShapeMode = 0
    private var mRadius = 0f
    private var mShape: Shape? = null
    private var mPaint: Paint? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
            mShapeMode = a.getInt(R.styleable.RoundImageView_shape_mode, SHAPE_MODE_CIRCLE)// 默认为圆形
            mRadius = a.getDimension(R.styleable.RoundImageView_round_radius, 0f)
            a.recycle()
        }
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.isFilterBitmap = true
        mPaint!!.color = Color.BLACK
        mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            when (mShapeMode) {
                SHAPE_MODE_ROUND_RECT -> {
                }
                SHAPE_MODE_CIRCLE -> {
                    val min = Math.min(width, height)
                    mRadius = min.toFloat() / 2
                }
            }
            if (mShape == null) {
                val radius = FloatArray(8)
                Arrays.fill(radius, mRadius)
                mShape = RoundRectShape(radius, null, null)
            }
            mShape!!.resize(width.toFloat(), height.toFloat())
        }
    }

    override fun onDraw(canvas: Canvas) {
        val saveCount = canvas.saveCount
        canvas.save()
        super.onDraw(canvas)
        when (mShapeMode) {
            SHAPE_MODE_ROUND_RECT, SHAPE_MODE_CIRCLE -> if (mShape != null) {
                mShape!!.draw(canvas, mPaint)
            }
        }
        canvas.restoreToCount(saveCount)
    }

    companion object {

        private val SHAPE_MODE_ROUND_RECT = 1
        private val SHAPE_MODE_CIRCLE = 2
    }
}
