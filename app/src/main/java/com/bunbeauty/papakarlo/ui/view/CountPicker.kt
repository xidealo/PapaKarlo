package com.bunbeauty.papakarlo.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bunbeauty.papakarlo.R
import com.google.android.material.button.MaterialButton

class CountPicker @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    var countChangeCallback: CountChangeCallback? = null
    var count = 0
    set(value) {
        field = value
        countTextView.text = value.toString()
    }

    private val elementSize = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CountPicker_elementSize,
        DEFAULT_BUTTON_SIZE
    )

    private val buttonColor = getColor(
        context,
        attributeSet,
        R.styleable.CountPicker_buttonColor,
        DEFAULT_BUTTON_COLOR
    )

    private val buttonTextColor = getColor(
        context,
        attributeSet,
        R.styleable.CountPicker_buttonTextColor,
        DEFAULT_BUTTON_TEXT_COLOR
    )

    private val plusButton = createButton("+", ::onPlus)
    private val minusButton = createButton("-", ::onMinus)
    private val countTextView = createTextView()

    init {
        addView(minusButton)
        addView(countTextView)
        addView(plusButton)
        isClickable = true

        gravity = CENTER
    }

    private fun createButton(text: String, onClickListener: OnClickListener): MaterialButton {
        val button = MaterialButton(context)

        button.layoutParams = LayoutParams(elementSize, elementSize, 1f).apply {
            z = 10f
        }
        button.text = text
        //button.backgroundTintList = ColorStateList.valueOf(buttonColor)
        button.setTextColor(buttonTextColor)

        button.setOnClickListener(onClickListener)

        return button
    }

    fun onPlus(view: View) {
        Log.d("test", "c " + count)
        count++
        countChangeCallback?.onCountIncreased()
        countTextView.text = count.toString()
    }

    fun onMinus(view: View) {
        if (count == 0) {
            return
        }

        count--
        countChangeCallback?.onCountDecreased()
        countTextView.text = count.toString()
    }

    private fun createTextView(): TextView {
        val textView = TextView(context)

        textView.layoutParams = LayoutParams(elementSize, elementSize, 1f)
        textView.gravity = CENTER
        textView.text = count.toString()

        return textView
    }

    fun getDimensionPixel(
        context: Context,
        attributeSet: AttributeSet?,
        attributeId: Int,
        defaultDip: Float
    ): Int {
        return if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.CountPicker)
            val dimensionPixel = typedArray.getLayoutDimension(
                attributeId,
                getPixels(defaultDip)
            )
            typedArray.recycle()

            dimensionPixel
        } else {
            getPixels(defaultDip)
        }
    }

    fun getInteger(
        context: Context,
        attributeSet: AttributeSet?,
        attributeId: Int,
        defaultInt: Int
    ): Int {
        return if (attributeSet != null) {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CountPicker)
            val integerValue = typedArray.getInteger(attributeId, defaultInt)
            typedArray.recycle()

            integerValue
        } else {
            defaultInt
        }
    }

    fun getPixels(dip: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resources.displayMetrics)
            .toInt()
    }

    private fun getColor(
        context: Context,
        attributeSet: AttributeSet?,
        attributeId: Int,
        defaultColor: Int
    ): Int {
        return if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.CountPicker)
            val color = typedArray.getColor(attributeId, defaultColor)
            typedArray.recycle()

            color
        } else {
            defaultColor
        }
    }

    interface CountChangeCallback {
        fun onCountIncreased()
        fun onCountDecreased()
    }

    companion object {
        private const val DEFAULT_COUNT = 0
        private const val DEFAULT_BUTTON_SIZE = 42f
        private const val DEFAULT_BUTTON_COLOR = R.color.white
        private const val DEFAULT_BUTTON_TEXT_COLOR = R.color.white
    }

}