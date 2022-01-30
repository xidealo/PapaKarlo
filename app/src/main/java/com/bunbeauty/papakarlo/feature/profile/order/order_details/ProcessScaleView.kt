package com.bunbeauty.papakarlo.feature.profile.order.order_details

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.custom_view.Customizable

class ProcessScaleView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet), Customizable {

    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val spaceWidth = 10f

    private val activeColor = getColor(
        context,
        attributeSet,
        R.styleable.ProcessScaleView,
        R.styleable.ProcessScaleView_activeColor,
        DEFAULT_ACTIVE_COLOR
    )

    private val inactiveColor = getColor(
        context,
        attributeSet,
        R.styleable.ProcessScaleView,
        R.styleable.ProcessScaleView_inactiveColor,
        DEFAULT_INACTIVE_COLOR
    )

    private val stepCount = getInteger(
        context,
        attributeSet,
        R.styleable.ProcessScaleView,
        R.styleable.ProcessScaleView_stepCount,
        DEFAULT_STEP_COUNT
    )

    var currentStep = 0
        set(value) {
            if (value in 0..stepCount) {
                field = value
            }
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (currentStep == 0) {
            paint.color = inactiveColor
        } else {
            paint.color = activeColor
        }
        val stepWidth = (width - spaceWidth * (stepCount - 1)) / stepCount
        val indent = stepWidth + spaceWidth
        val stepHeight = height.toFloat()
        val radius = stepHeight / 2f
        for (i in 1..stepCount) {
            if (i == currentStep + 1 && currentStep != 0) {
                paint.color = inactiveColor
            }
            canvas.drawRoundRect(
                (i - 1) * indent,
                0f,
                (i - 1) * indent + stepWidth,
                stepHeight,
                radius,
                radius,
                paint
            )
        }
    }

    companion object {
        private const val DEFAULT_STEP_COUNT = 1
        private const val DEFAULT_ACTIVE_COLOR = 0x000000
        private const val DEFAULT_INACTIVE_COLOR = 0xFFFFFF
    }
}