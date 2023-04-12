package com.bunbeauty.papakarlo.extensions

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.google.android.material.snackbar.Snackbar

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

inline fun ComposeView.setContentWithTheme(crossinline content: @Composable () -> Unit) {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    setContent {
        FoodDeliveryTheme {
            content()
        }
    }
}
