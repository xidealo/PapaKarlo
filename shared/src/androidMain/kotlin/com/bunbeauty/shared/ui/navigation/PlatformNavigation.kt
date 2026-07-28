package com.bunbeauty.shared.ui.navigation

import androidx.navigation.NavController

// The system back button is wired to the stack by NavHost itself.
actual suspend fun NavController.bindSystemBackNavigation() = Unit

actual fun NavController.backAction(): () -> Unit = { navigateUp() }
