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
import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.annotation.ExperimentalCoilApi
import coil.load
import coil.size.ViewSizeResolver
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
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

fun View.showOrGone(isVisible: Boolean): View {
    if (isVisible) {
        this.visible()
    } else {
        this.gone()
    }
    return this
}

fun View.showSnackbar(
    message: String,
    @ColorInt textColor: Int,
    @ColorInt backgroundColor: Int,
    isTop: Boolean
) {
    val snack = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
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

inline fun ComposeView.compose(crossinline content: @Composable () -> Unit) {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    setContent {
        FoodDeliveryTheme {
            content()
        }
    }
}