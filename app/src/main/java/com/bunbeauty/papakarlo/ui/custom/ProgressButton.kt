package com.bunbeauty.papakarlo.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.visible
import com.google.android.material.button.MaterialButton

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attributeSet, defStyleAttr), Customizable {

    private val buttonText = getString(
        context,
        attributeSet,
        R.styleable.ProgressButton,
        R.styleable.ProgressButton_buttonText,
        ""
    )
    private val buttonTextAllCaps = getBoolean(
        context,
        attributeSet,
        R.styleable.ProgressButton,
        R.styleable.ProgressButton_buttonTextAllCaps,
        false
    )
    private val buttonCornerRadius = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.ProgressButton,
        R.styleable.ProgressButton_buttonCornerRadius,
        DEFAULT_BUTTON_CORNER_RADIUS
    )
    private val progressButtonColor = getColor(
        context,
        attributeSet,
        R.styleable.ProgressButton,
        R.styleable.ProgressButton_progressButtonColor,
        DEFAULT_BUTTON_COLOR
    )
    private val progressButtonForegroundColor = getColor(
        context,
        attributeSet,
        R.styleable.ProgressButton,
        R.styleable.ProgressButton_progressButtonForegroundColor,
        DEFAULT_BUTTON_COLOR
    )
    private val progressButtonDisableForegroundColor = getColor(
        context,
        attributeSet,
        R.styleable.ProgressButton,
        R.styleable.ProgressButton_progressButtonDisableForegroundColor,
        progressButtonForegroundColor
    )

    private val button = createButton(context)
    private val progressBar = createProgressBar(context)

    init {
        addView(button)
        addView(progressBar)
    }

    private fun createButton(context: Context): MaterialButton {
        return MaterialButton(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT).apply {
                z = 0f
            }
            text = buttonText
            isAllCaps = buttonTextAllCaps
            backgroundTintList = ColorStateList.valueOf(progressButtonColor)
            cornerRadius = buttonCornerRadius
            setTextColor(progressButtonForegroundColor)
        }
    }

    private fun createProgressBar(context: Context): ProgressBar {
        return ProgressBar(context, null, android.R.attr.progressBarStyleSmall).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
                visibility = GONE
                z = 10f
            }
            indeterminateTintList = ColorStateList.valueOf(progressButtonForegroundColor)
        }
    }

    fun toggleLoading(isLoading: Boolean) {
        if (isLoading) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    fun showLoading() {
        button.text = ""
        progressBar.visible()
    }

    fun hideLoading() {
        button.text = buttonText
        progressBar.gone()
    }

    fun toggleEnabling(isEnabled: Boolean) {
        if (isEnabled) {
            enable()
        } else {
            disable()
        }
    }

    fun enable() {
        button.run {
            isEnabled = true
            alpha = 0.5f
            setTextColor(ColorStateList.valueOf(progressButtonForegroundColor))
        }
    }

    fun disable() {
        button.run {
            isEnabled = false
            alpha = 1f
            setTextColor(ColorStateList.valueOf(progressButtonDisableForegroundColor))
        }
    }

    fun setOnClickListener(onClickListener: (() -> Unit)) {
        button.setOnClickListener {
            onClickListener()
        }
    }

    companion object {
        const val DEFAULT_BUTTON_CORNER_RADIUS = 0f
        const val DEFAULT_BUTTON_COLOR = 0xaaaaaa
    }
}