package com.bunbeauty.papakarlo.common.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.core.widget.TextViewCompat
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.google.android.material.card.MaterialCardView
import javax.inject.Inject

class NavigationCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attributeSet, defStyleAttr), Customizable {

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    var cardText = getString(
        context,
        attributeSet,
        R.styleable.NavigationCard,
        R.styleable.NavigationCard_android_text,
        ""
    )
        set(value) {
            field = value
            textView.text = value
        }

    var icon = getDrawable(
        context,
        attributeSet,
        R.styleable.NavigationCard,
        R.styleable.NavigationCard_android_icon
    )
        set(value) {
            field = value
            imageView.setImageDrawable(value)
        }

    private val iconColor = getColor(
        context,
        attributeSet,
        R.styleable.NavigationCard,
        R.styleable.NavigationCard_iconColor,
        DEFAULT_COLOR
    )

    private val padding = getDimensionPixel(
        context,
        attributeSet,
        R.styleable.NavigationCard,
        R.styleable.NavigationCard_android_padding,
        DEFAULT_PADDING
    )

    init {
        (context.applicationContext as PapaKarloApplication).appComponent.inject(this)
    }

    private val imageViewId = generateViewId()
    private var textView: TextView = createTextView(context)
    private var imageView: ImageView = createImageView(context)

    init {
        val constraintLayout = createConstraintLayout(context).apply {
            addView(imageView)
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

    private fun createImageView(context: Context): ImageView {
        return ImageView(context).apply {
            id = imageViewId
            layoutParams = ConstraintLayout.LayoutParams(
                WRAP_CONTENT,
                resourcesProvider.getDimensionPixelOffset(R.dimen.icon_height)
            ).apply {
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                endToEnd = PARENT_ID
            }
            setColorFilter(iconColor)
            setImageDrawable(icon)
        }
    }

    private fun createTextView(context: Context): TextView {
        return TextView(context).apply {
            textSize = 14f
            layoutParams = ConstraintLayout.LayoutParams(0, WRAP_CONTENT).apply {
                startToStart = PARENT_ID
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                endToStart = imageViewId
                setMargins(0, 0, padding, 0)
            }
            TextViewCompat.setTextAppearance(this, R.style.MediumRegularTextAppearance)
            text = cardText
        }
    }

    companion object {
        private const val DEFAULT_COLOR = 0
        private const val DEFAULT_PADDING = 0f
    }


}