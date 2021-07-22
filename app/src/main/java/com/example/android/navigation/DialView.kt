package com.example.android.navigation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private enum class FanSpeed(val label: Int) {
        OFF(R.string.fan_off),
        LOW(R.string.fan_low),
        MEDIUM(R.string.fan_medium),
        HIGH(R.string.fan_high);

        fun next() = when(this) {
            OFF -> LOW
            LOW -> MEDIUM
            MEDIUM -> HIGH
            HIGH -> OFF
        }
    }

    private var radius = 0.0f
    private var fanSpeed = FanSpeed.OFF
    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
//      familyName can not be an empty string, otherwise Typeface.create() throws a Null Exception
        typeface = Typeface.create("l", Typeface.BOLD)
    }

    init{
        isClickable = true
    }

    override fun performClick(): Boolean {
        // Allows other users to add behaviour to DialView with onClickListener()
        if(super.performClick()) return true

        fanSpeed = fanSpeed.next()
        contentDescription = resources.getString(fanSpeed.label)

        //invalidate() invalidates DialView view with causes onDraw() to be executed
        invalidate()
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (min(w, h) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = if(fanSpeed == FanSpeed.OFF) Color.GRAY else Color.GREEN

        // Draw the dial disk
        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), radius, paint)

        // Draw the indicator circle.
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed, markerRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius/12, paint)
        // Draw the text labels.
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for (i in FanSpeed.values()) {
            pointPosition.computeXYForSpeed(i, labelRadius)
            val label = resources.getString(i.label)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }

    private fun PointF.computeXYForSpeed(pos: FanSpeed, radius: Float) {
        // Angles are in radians.
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }
}
