package com.bunbeauty.shared

import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.bunbeauty.core.Logger
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.screen.main.MainScreen
import platform.UIKit.UIViewController

@Suppress("FunctionName")
fun MainViewController(flavor: String): UIViewController =
    ComposeUIViewController {
        Logger.logD("MainViewController", "Flavor: $flavor")
        FoodDeliveryTheme(
            flavor = flavor,
        ) {
            MainScreen(
                modifier =
                    Modifier
                        .imePadding(),
            )
        }
    }
