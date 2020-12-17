package com.bunbeauty.papakarlo.extensions

import android.view.View

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

fun View.setViewVisibility(isVisible: Boolean): View {
    if (isVisible) {
        this.visible()
    } else {
        this.gone()
    }
    return this
}