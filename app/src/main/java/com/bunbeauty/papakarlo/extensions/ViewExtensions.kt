package com.bunbeauty.papakarlo.extensions

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Paint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.annotation.ExperimentalCoilApi
import coil.load
import coil.size.ViewSizeResolver
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

fun View.showSnackbar(errorMessage: String, textColor: Int, backgroundColor: Int, isTop: Boolean) {
    val snack = Snackbar.make(this, errorMessage, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(backgroundColor)
        .setTextColor(textColor)
        .setActionTextColor(textColor)

    snack.run {
        if (isTop) {
            view.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                gravity = Gravity.TOP
                setMargins(
                    getPixels(context, 16),
                    getPixels(context, 64),
                    getPixels(context, 16),
                    0
                )
            }
        } else {
            setAnchorView(R.id.activity_main_bnv_bottom_navigation)
        }
        view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
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

fun View.focusAndShowKeyboard() {
    requestFocus()

    // Show keyboard
    val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, 0)
}

fun RecyclerView.scrollToPositionWithOffset(position: Int, offset: Int) {
    (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, offset)
}

@ExperimentalCoilApi
fun ImageView.setPhoto(photoLink: String) {
    load(photoLink) {
        crossfade(true)
        placeholder(R.drawable.placeholder)
        error(R.drawable.placeholder)
        size(ViewSizeResolver(this@setPhoto))
    }
}