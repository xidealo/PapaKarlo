package com.bunbeauty.papakarlo.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import com.bunbeauty.papakarlo.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class CustomSwitcher @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attributeSet, defStyleAttr), Customizable {

    var switchListener: SwitchListener? = null

    var isLeft: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                updateButtons(value)
            }
        }

    //        getColor(
//        context,
//        attributeSet,
//        R.styleable.CustomSwitcher,
//        R.styleable.CustomSwitcher_backgroundColor,
//        DEFAULT_BACKGROUND_COLOR
//    )
    private val buttonColor = getColor(
        context,
        attributeSet,
        R.styleable.CustomSwitcher,
        R.styleable.CustomSwitcher_buttonColor,
        DEFAULT_BUTTON_COLOR
    )
    private val switcherMainTextColor = getColor(
        context,
        attributeSet,
        R.styleable.CustomSwitcher,
        R.styleable.CustomSwitcher_switcherMainTextColor,
        DEFAULT_BUTTON_TEXT_COLOR
    )
    private val switcherAlternativeTextColor = getColor(
        context,
        attributeSet,
        R.styleable.CustomSwitcher,
        R.styleable.CustomSwitcher_switcherAlternativeTextColor,
        DEFAULT_ALTERNATIVE_TEXT_COLOR
    )
    private val leftButtonText = getString(
        context,
        attributeSet,
        R.styleable.CustomSwitcher,
        R.styleable.CustomSwitcher_leftButtonText,
        DEFAULT_TEXT
    )
    private val rightButtonText = getString(
        context,
        attributeSet,
        R.styleable.CustomSwitcher,
        R.styleable.CustomSwitcher_rightButtonText,
        DEFAULT_TEXT
    )
    private val switcherHeight = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher,
        R.styleable.CustomSwitcher_switcherHeight,
        DEFAULT_BUTTON_HEIGHT
    )
    private val switcherButtonMargin = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher,
        R.styleable.CustomSwitcher_switcherButtonMargin,
        DEFAULT_BUTTON_MARGIN
    )
    private val switcherButtonRadius = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.CustomSwitcher,
        R.styleable.CustomSwitcher_switcherButtonRadius,
        DEFAULT_BUTTON_RADIUS
    )

    private val leftButton = createButton(leftButtonText, true)
    private val rightButton = createButton(rightButtonText, false)

    init {
        //backgroundTintList = ColorStateList.valueOf(backgroundColor)

        val linearLayout = LinearLayout(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            addView(leftButton)
            addView(rightButton)
        }
        addView(linearLayout)
    }

    private fun createButton(buttonText: String, isLeftButton: Boolean): MaterialButton {
        return MaterialButton(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, switcherHeight, 1f).apply {
                setPadding(0, 0, 0, 0)
                setMargins(
                    switcherButtonMargin,
                    switcherButtonMargin,
                    switcherButtonMargin,
                    switcherButtonMargin
                )
            }
            stateListAnimator = null
            insetTop = 0
            insetBottom = 0
            val textColor = if (isLeftButton) {
                switcherMainTextColor
            } else {
                switcherAlternativeTextColor
            }
            setTextColor(ColorStateList.valueOf(textColor))
            text = buttonText
            cornerRadius = switcherButtonRadius
            val backgroundColor = if (isLeftButton) {
                buttonColor
            } else {
                Color.TRANSPARENT
            }
            backgroundTintList = ColorStateList.valueOf(backgroundColor)
            setOnClickListener {
                updateButtons(isLeftButton)
                isLeft = isLeftButton
                switchListener?.onSwitched(isLeftButton)
            }
        }
    }

    private fun updateButtons(isLeftButton: Boolean) {
        if (isLeftButton) {
            leftButton.makeActive()
            rightButton.makeInactive()
        } else {
            leftButton.makeInactive()
            rightButton.makeActive()
        }
    }

    private fun MaterialButton.makeActive() {
        backgroundTintList = ColorStateList.valueOf(buttonColor)
        setTextColor(switcherMainTextColor)
    }

    private fun MaterialButton.makeInactive() {
        backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        setTextColor(switcherAlternativeTextColor)
    }

    interface SwitchListener {
        fun onSwitched(isLeft: Boolean)
    }

    companion object {
        private const val DEFAULT_BUTTON_COLOR = 0xFFB3BA
        private const val DEFAULT_BUTTON_TEXT_COLOR = 0xFFB3BA
        private const val DEFAULT_ALTERNATIVE_TEXT_COLOR = 0xFFB3BA
        private const val DEFAULT_TEXT = "-"
        private const val DEFAULT_BUTTON_MARGIN = 0f
        private const val DEFAULT_BUTTON_HEIGHT = 32f
        private const val DEFAULT_BUTTON_RADIUS = 4f
    }

}