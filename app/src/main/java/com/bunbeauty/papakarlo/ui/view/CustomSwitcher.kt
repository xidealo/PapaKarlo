package com.bunbeauty.papakarlo.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bunbeauty.papakarlo.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class CustomSwitcher @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attributeSet, defStyleAttr), Customizable {

    var isLeft = true
        set(value) {
            field = value
            _isLeftLiveData.value = value
        }
    private val _isLeftLiveData = MutableLiveData(isLeft)
    val isLeftLiveData: LiveData<Boolean>
        get() = _isLeftLiveData

    /*private val backgroundWidth = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_backgroundWidth,
        DEFAULT_BACKGROUND_WIDTH
    )
    private val backgroundHeight = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_backgroundHeight,
        DEFAULT_BACKGROUND_HEIGHT
    )*/
    private val backgroundRadius = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_backgroundRadius,
        DEFAULT_BACKGROUND_RADIUS
    )
    private val switcherColor = getColor(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_switcherColor,
        DEFAULT_BACKGROUND_COLOR
    )
    private val switcherPadding = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_switcherPadding,
        DEFAULT_SWITCH_PADDING
    )
    private val buttonWidth = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_buttonWidth,
        DEFAULT_BUTTON_WIDTH
    )
    private val buttonHeight = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_buttonHeight,
        DEFAULT_BUTTON_HEIGHT
    )
    private val buttonRadius = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_buttonRadius,
        DEFAULT_BUTTON_RADIUS
    )
    private val buttonTextSize = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_buttonTextSize,
        DEFAULT_BUTTON_TEXT_SIZE
    )
    private val activeTextColor = getColor(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_activeTextColor,
        DEFAULT_ACTIVE_TEXT_COLOR
    )
    private val inactiveTextColor = DEFAULT_INACTIVE_TEXT_COLOR
        /*getColor(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_inactiveTextColor,
        DEFAULT_INACTIVE_TEXT_COLOR
    )*/
    private val activeColor = getColor(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_activeColor,
        DEFAULT_ACTIVE_COLOR
    )
    private val leftButtonText = getString(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_activeColor,
        DEFAULT_LEFT_BUTTON_TEXT
    )
    private val rightButtonText = getString(
        context,
        attributeSet,
        R.styleable.CustomSwitcher_activeColor,
        DEFAULT_RIGHT_BUTTON_TEXT
    )

    private val leftButton = createButton(leftButtonText, isLeft)
    private val rightButton = createButton(rightButtonText, !isLeft)

    init {
        leftButton.setOnClickListener(::onButtonClick)
        rightButton.setOnClickListener(::onButtonClick)

        val linearLayout = LinearLayout(context)
        linearLayout.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        linearLayout.backgroundTintList = ColorStateList.valueOf(switcherColor)
        linearLayout.setPadding(switcherPadding, switcherPadding, switcherPadding, switcherPadding)
        linearLayout.addView(leftButton)
        linearLayout.addView(rightButton)

        radius = backgroundRadius.toFloat()
        addView(linearLayout)
    }

    private fun createButton(text: String, isActive: Boolean): MaterialButton {
        val button = MaterialButton(context)

        button.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, buttonHeight, 1f)
        button.cornerRadius = buttonRadius
        button.text = text
        val buttonColor = if (isActive) activeColor else switcherColor
        button.backgroundTintList = ColorStateList.valueOf(buttonColor)
        val buttonTextColor = if (isActive) activeTextColor else inactiveTextColor
        button.setTextColor(buttonTextColor)
        button.setTextSize(COMPLEX_UNIT_PX, buttonTextSize.toFloat())

        return button
    }

    private fun onButtonClick(view: View) {
        toggleButton(leftButton, isLeft)
        toggleButton(rightButton, !isLeft)
        isLeft = !isLeft
    }

    private fun toggleButton(button: MaterialButton, isActive: Boolean) {
        if (isActive) {
            button.backgroundTintList = ColorStateList.valueOf(activeColor)
            button.setTextColor(activeTextColor)
        } else {
            button.backgroundTintList = ColorStateList.valueOf(switcherColor)
            button.setTextColor(inactiveTextColor)
        }
    }

    companion object {
        private const val DEFAULT_BACKGROUND_WIDTH = 240f
        private const val DEFAULT_BACKGROUND_HEIGHT = 32f
        private const val DEFAULT_BACKGROUND_RADIUS = 8f
        private const val DEFAULT_BACKGROUND_COLOR = 0xFF0
        private const val DEFAULT_SWITCH_PADDING = 4f
        private const val DEFAULT_IS_LEFT = true
        private const val DEFAULT_BUTTON_WIDTH =  0f//116f
        private const val DEFAULT_BUTTON_HEIGHT = 0f//24f
        private const val DEFAULT_BUTTON_RADIUS = 4f
        private const val DEFAULT_BUTTON_TEXT_SIZE = 16f
        private const val DEFAULT_ACTIVE_TEXT_COLOR = 0xFFF
        private const val DEFAULT_INACTIVE_TEXT_COLOR = 0x888
        private const val DEFAULT_ACTIVE_COLOR = 0x000
        private const val DEFAULT_LEFT_BUTTON_TEXT = "ON"
        private const val DEFAULT_RIGHT_BUTTON_TEXT = "OFF"
    }

}