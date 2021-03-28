package com.bunbeauty.papakarlo.ui.view

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import com.bunbeauty.papakarlo.R

interface Customizable {

    fun getString(
        context: Context,
        attributeSet: AttributeSet?,
        attributeId: Int,
        defaultString: String
    ): String {
        return if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.ProgressButton)
            val text = typedArray.getString(attributeId) ?: defaultString
            typedArray.recycle()

            text
        } else {
            defaultString
        }
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

    fun getColor(
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

    fun getBoolean(
        context: Context,
        attributeSet: AttributeSet?,
        attributeId: Int,
        default: Boolean
    ): Boolean {
        return if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.ProgressButton)
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