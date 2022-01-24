package com.bunbeauty.papakarlo.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.core.widget.TextViewCompat
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

    var icon = getDrawable(
        context,
        attributeSet,
        R.styleable.TextCard,
        R.styleable.TextCard_android_icon
    )
        set(value) {
            field = value
            imageView.setImageDrawable(value)
        }

    private val iconColor = getColor(
        context,
        attributeSet,
        R.styleable.TextCard,
        R.styleable.TextCard_textCardIconColor,
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
    private val imageViewId = generateViewId()
    private var hintTextView: TextView = createHintTextView(context, hintText)
    private var textView: TextView = createTextView(context, cardText)
    private var imageView: ImageView = createImageView(context)

    init {
        val constraintLayout = createConstraintLayout(context).apply {
            addView(hintTextView)
            addView(textView)
            addView(imageView)
        }
        addView(constraintLayout)
    }

    private fun createImageView(context: Context): ImageView {
        return ImageView(context).apply {
            id = imageViewId
            layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                endToEnd = PARENT_ID
            }
            setColorFilter(iconColor)
            setImageDrawable(icon)
        }
    }

    private fun createConstraintLayout(context: Context): ConstraintLayout {
        return ConstraintLayout(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            setPadding(padding, padding, padding, padding)
        }
    }

    private fun createHintTextView(context: Context, textViewText: String): TextView {
        return TextView(context).apply {
            id = hintTextViewId
            textSize = 12f
            layoutParams = ConstraintLayout.LayoutParams(0, WRAP_CONTENT).apply {
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                endToStart = imageViewId
                setMargins(0, 0, innerMargin, 0)
            }
            TextViewCompat.setTextAppearance(this, R.style.SecondarySmallRegularTextAppearance)
            text = textViewText
        }
    }

    private fun createTextView(context: Context, textViewText: String): TextView {
        return TextView(context).apply {
            textSize = 14f
            layoutParams = ConstraintLayout.LayoutParams(0, WRAP_CONTENT).apply {
                topToBottom = hintTextViewId
                startToStart = PARENT_ID
                endToStart = imageViewId
                setMargins(0, innerMargin, innerMargin, 0)
            }
            TextViewCompat.setTextAppearance(this, R.style.MediumRegularTextAppearance)
            text = textViewText
        }
    }

    companion object {
        private const val DEFAULT_COLOR = 0
        private const val DEFAULT_PADDING = 0f
        private const val DEFAULT_INNER_MARGIN = 0f
    }


}