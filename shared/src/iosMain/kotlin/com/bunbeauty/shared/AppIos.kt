package com.bunbeauty.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.bunbeauty.core.Logger
import com.bunbeauty.shared.ui.screen.main.MainScreen
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import platform.UIKit.UIViewController

@Suppress("FunctionName")
fun MainViewController(
    flavor: String
): UIViewController = ComposeUIViewController {
    Logger.logD("MainViewController", "Flavor: $flavor")
    FoodDeliveryTheme(
        flavor = flavor
    ) {
        MainScreen()
    }
}
