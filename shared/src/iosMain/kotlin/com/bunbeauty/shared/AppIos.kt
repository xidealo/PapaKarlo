package com.bunbeauty.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.bunbeauty.shared.ui.screen.main.MainScreen
import platform.UIKit.UIViewController

@Suppress("FunctionName")
fun MainViewController(): UIViewController = ComposeUIViewController {
    MainScreen()
}
