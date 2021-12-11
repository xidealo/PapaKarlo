package com.bunbeauty.papakarlo.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import com.bunbeauty.papakarlo.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class CountPicker @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attributeSet, defStyleAttr), Customizable {

    var countChangeListener: CountChangeListener? = null
    var count = 0
        set(value) {
            field = value
            countTextView.text = value.toString()
        }

    private val elementWidth = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CountPicker,
        R.styleable.CountPicker_pickerWidth,
        DEFAULT_PICKER_WIDTH
    )

    private val elementHeight = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CountPicker,
        R.styleable.CountPicker_pickerHeight,
        DEFAULT_PICKER_HEIGHT
    )

    private val buttonColor = getColor(
        context,
        attributeSet,
        R.styleable.CountPicker,
        R.styleable.CountPicker_pickerColor,
        DEFAULT_PICKER_COLOR
    )

    private val buttonTextColor = getColor(
        context,
        attributeSet,
        R.styleable.CountPicker,
        R.styleable.CountPicker_buttonTextColor,
        DEFAULT_BUTTON_TEXT_COLOR
    )

    private val plusButton = createButton("+", ::onPlus)
    private val minusButton = createButton("−", ::onMinus)
    private val countTextView = createTextView()

    init {
        backgroundTintList = ColorStateList.valueOf(buttonColor)
        radius = context.resources.getDimensionPixelOffset(R.dimen.medium_radius).toFloat()
        setPadding(20, 20, 20, 20)

        val linearLayout = LinearLayout(context).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            addView(minusButton)
            addView(countTextView)
            addView(plusButton)
        }
        addView(linearLayout)
    }

    private fun createButton(buttonText: String, onClickListener: OnClickListener): MaterialButton {
        return MaterialButton(context).apply {
            layoutParams = LayoutParams(elementWidth, elementHeight, 1).apply {
                z = 0f
                setPadding(0, 0, 0, 0)
            }
            stateListAnimator = null
            insetTop = 0
            insetBottom = 0
            setTextColor(ColorStateList.valueOf(buttonTextColor))
            text = getSpan(buttonText)
            backgroundTintList = ColorStateList.valueOf(buttonColor)
            cornerRadius = context.resources.getDimensionPixelOffset(R.dimen.medium_radius)
            setOnClickListener(onClickListener)
        }
    }

    private fun onPlus(view: View) {
        countChangeListener?.onCountIncreased()
        countTextView.text = count.toString()
    }

    private fun onMinus(view: View) {
        countChangeListener?.onCountDecreased()
        countTextView.text = count.toString()
    }

    private fun createTextView(): TextView {
        return TextView(context).apply {
            setBackgroundColor(buttonColor)
            layoutParams = LayoutParams(WRAP_CONTENT, elementHeight, 1)
            gravity = CENTER
            setTextColor(ColorStateList.valueOf(buttonTextColor))
            text = getSpan(count.toString())
        }
    }

    private fun getSpan(text: String): SpannableString {
        val spanText = SpannableString(text)
        spanText.setSpan(StyleSpan(Typeface.BOLD), 0, text.length, 0)
        return spanText
    }

    interface CountChangeListener {
        fun onCountIncreased()
        fun onCountDecreased()
    }

    companion object {
        private const val DEFAULT_PICKER_WIDTH = 96f
        private const val DEFAULT_PICKER_HEIGHT = 42f
        private const val DEFAULT_PICKER_COLOR = R.color.orange
        private const val DEFAULT_BUTTON_TEXT_COLOR = R.color.black
    }

}