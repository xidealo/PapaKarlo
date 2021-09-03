package com.bunbeauty.papakarlo.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import com.bunbeauty.papakarlo.R
import com.google.android.material.card.MaterialCardView

class TextCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attributeSet, defStyleAttr), Customizable {

    var hintText = getString(
        context,
        attributeSet,
        R.styleable.TextCard,
        R.styleable.TextCard_android_hint,
        ""
    )
        set(value) {
            field = value
            hintTextView.text = value
        }

    var cardText = getString(
        context,
        attributeSet,
        R.styleable.TextCard,
        R.styleable.TextCard_android_text,
        ""
    )
        set(value) {
            field = value
            textView.text = value
        }

    private val textColor = getColor(
        context,
        attributeSet,
        R.styleable.TextCard,
        R.styleable.TextCard_android_textColor,
        DEFAULT_COLOR
    )

    private val hintTextColor = getColor(
        context,
        attributeSet,
        R.styleable.TextCard,
        R.styleable.TextCard_hintTextColor,
        DEFAULT_COLOR
    )

    private val innerMargin = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.TextCard,
        R.styleable.TextCard_innerMargin,
        DEFAULT_INNER_MARGIN
    )

    private val padding = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.TextCard,
        R.styleable.TextCard_android_padding,
        DEFAULT_PADDING
    )

    private val hintTextViewId = generateViewId()
    private var hintTextView: TextView = createHintTextView(context, hintText, hintTextColor)
    private var textView: TextView = createTextView(context, cardText, textColor)

    init {
        val constraintLayout = createConstraintLayout(context).apply {
            addView(hintTextView)
            addView(textView)
        }
        addView(constraintLayout)
    }

    private fun createConstraintLayout(context: Context): ConstraintLayout {
        return ConstraintLayout(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            setPadding(padding, padding, padding, padding)
        }
    }

    private fun createHintTextView(context: Context, textViewText: String, textColor: Int): TextView {
        return TextView(context).apply {
            id = hintTextViewId
            textSize = 12f
            layoutParams = ConstraintLayout.LayoutParams(0, WRAP_CONTENT).apply {
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }
            setTextColor(textColor)
            text = textViewText
        }
    }

    private fun createTextView(context: Context, textViewText: String, textColor: Int): TextView {
        return TextView(context).apply {
            textSize = 14f
            layoutParams = ConstraintLayout.LayoutParams(0, WRAP_CONTENT).apply {
                topToBottom = hintTextViewId
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                setMargins(0, innerMargin, 0, 0)
            }
            setTextColor(textColor)
            text = textViewText
        }
    }

    companion object {
        private const val DEFAULT_COLOR = 0
        private const val DEFAULT_PADDING = 0f
        private const val DEFAULT_INNER_MARGIN = 0f
    }


}