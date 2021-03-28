package com.bunbeauty.papakarlo.ui.view

import android.content.Context
import android.content.res.ColorStateList
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
) : LinearLayout(context, attributeSet, defStyleAttr), Customizable {

    var countChangeListener: CountChangeListener? = null
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
        button.backgroundTintList = ColorStateList.valueOf(buttonColor)
        button.setTextColor(buttonTextColor)

        button.setOnClickListener(onClickListener)

        return button
    }

    private fun onPlus(view: View) {
        count++
        countChangeListener?.onCountIncreased()
        countTextView.text = count.toString()
    }

    private fun onMinus(view: View) {
        if (count == 0) {
            return
        }

        count--
        countChangeListener?.onCountDecreased()
        countTextView.text = count.toString()
    }

    private fun createTextView(): TextView {
        val textView = TextView(context)

        textView.layoutParams = LayoutParams(elementSize, elementSize, 1f)
        textView.gravity = CENTER
        textView.text = count.toString()

        return textView
    }

    interface CountChangeListener {
        fun onCountIncreased()
        fun onCountDecreased()
    }

    companion object {
        private const val DEFAULT_BUTTON_SIZE = 39.5f
        private const val DEFAULT_BUTTON_COLOR = R.color.white
        private const val DEFAULT_BUTTON_TEXT_COLOR = R.color.white
    }

}