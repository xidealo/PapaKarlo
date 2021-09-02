package com.bunbeauty.papakarlo.extensions

import android.graphics.Paint
import android.view.View
import android.widget.TextView
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

fun TextView.strikeOutText() {
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
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