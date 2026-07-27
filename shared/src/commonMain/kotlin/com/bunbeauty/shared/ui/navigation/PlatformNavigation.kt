package com.bunbeauty.shared.ui.navigation

import androidx.navigation.NavController

// In the browser the back button works with window.history, which knows nothing
// about the NavController stack, so the two have to be bound manually. On Android
// and iOS the system back is already handled by NavHost, so there it does nothing.
expect suspend fun NavController.bindSystemBackNavigation()

// Action for the back arrow in the top bar. On the web the browser history is the
// source of truth for back, on the other platforms it is the navigation stack.
expect fun NavController.backAction(): () -> Unit
