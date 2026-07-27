package com.bunbeauty.shared.ui.navigation

import androidx.navigation.NavController

// The back swipe gesture is wired to the stack by NavHost itself.
actual suspend fun NavController.bindSystemBackNavigation() = Unit

actual fun NavController.backAction(): () -> Unit = { navigateUp() }
