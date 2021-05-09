package com.bunbeauty.papakarlo.extensions

import android.content.Context
import android.graphics.Paint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bunbeauty.papakarlo.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

fun View.invisible(): View {
    visibility = View.INVISIBLE
    return this
}

fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}

fun View.gone(): View {
    visibility = View.GONE
    return this
}

fun View.toggleVisibility(isVisible: Boolean): View {
    if (isVisible) {
        this.visible()
    } else {
        this.gone()
    }
    return this
}

fun View.toggleVisibilityInvisibility(isVisible: Boolean): View {
    if (isVisible) {
        this.visible()
    } else {
        this.invisible()
    }
    return this
}

fun View.showSnackbar(errorMessage: String, textColorId: Int, backgroundColorId: Int) {
    val snack = Snackbar.make(this, errorMessage, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(ContextCompat.getColor(context, backgroundColorId))
        .setTextColor(ContextCompat.getColor(context, textColorId))
        .setActionTextColor(ContextCompat.getColor(context, textColorId))
    val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
        gravity = Gravity.TOP
        setMargins(getPixels(context, 16), getPixels(context, 72), getPixels(context, 16), 0)
    }
    snack.run {
        view.layoutParams = layoutParams
        view.findViewById<TextView>(R.id.snackbar_text).textAlignment = View.TEXT_ALIGNMENT_CENTER
        show()
    }
}

private fun getPixels(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun TextView.strikeOutText() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.underlineText() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextInputLayout.setErrorFocus(errorMessage: String) {
    this.error = errorMessage
    this.isErrorEnabled = true
    this.requestFocus()
}

fun TextInputLayout.clearErrorFocus() {
    this.error = null
    this.isErrorEnabled = false
}