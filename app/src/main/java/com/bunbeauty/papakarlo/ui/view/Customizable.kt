package com.bunbeauty.papakarlo.ui.view

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import com.bunbeauty.papakarlo.R

interface Customizable {

    fun getString(
        context: Context,
        attributeSet: AttributeSet?,
        attributes: IntArray,
        attributeId: Int,
        defaultString: String
    ): String {
        val a = if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, attributes)
            val text = typedArray.getString(attributeId) ?: defaultString
            typedArray.recycle()

            text
        } else {
            defaultString
        }

        return a
    }

    fun getDimensionPixel(
        context: Context,
        attributeSet: AttributeSet?,
        attributes: IntArray,
        attributeId: Int,
        defaultDip: Float
    ): Int {
        return if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, attributes)
            val dimensionPixel = typedArray.getLayoutDimension(
                attributeId,
                getPixels(defaultDip, context.resources)
            )
            typedArray.recycle()

            dimensionPixel
        } else {
            getPixels(defaultDip, context.resources)
        }
    }

    fun getInteger(
        context: Context,
        attributeSet: AttributeSet?,
        attributes: IntArray,
        attributeId: Int,
        defaultInt: Int
    ): Int {
        return if (attributeSet != null) {
            val typedArray = context.obtainStyledAttributes(attributeSet, attributes)
            val integerValue = typedArray.getInteger(attributeId, defaultInt)
            typedArray.recycle()

            integerValue
        } else {
            defaultInt
        }
    }

    fun getColor(
        context: Context,
        attributeSet: AttributeSet?,
        attributes: IntArray,
        attributeId: Int,
        defaultColor: Int
    ): Int {
        return if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, attributes)
            val color = typedArray.getColor(attributeId, defaultColor)
            typedArray.recycle()

            color
        } else {
            defaultColor
        }
    }

    fun getDrawable(
        context: Context,
        attributeSet: AttributeSet?,
        attributes: IntArray,
        attributeId: Int,
    ): Drawable? {
        return if (attributeSet != null) {
            val typedArray = context.obtainStyledAttributes(attributeSet, attributes)
            val drawable = typedArray.getDrawable(attributeId)
            typedArray.recycle()

            drawable
        } else {
            null
        }
    }

    fun getBoolean(
        context: Context,
        attributeSet: AttributeSet?,
        attributes: IntArray,
        attributeId: Int,
        default: Boolean
    ): Boolean {
        return if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, attributes)
            val boolean = typedArray.getBoolean(attributeId, default)
            typedArray.recycle()

            boolean
        } else {
            default
        }
    }

    private fun getPixels(dip: Float, resources: Resources): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resources.displayMetrics)
            .toInt()
    }
}