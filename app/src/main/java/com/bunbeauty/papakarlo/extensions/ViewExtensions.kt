package com.bunbeauty.papakarlo.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme

inline fun ComposeView.setContentWithTheme(crossinline content: @Composable () -> Unit) {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    setContent {
        FoodDeliveryTheme {
            content()
        }
    }
}
