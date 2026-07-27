package com.bunbeauty.shared.ui.navigation

import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.bindToBrowserNavigation
import com.bunbeauty.shared.ui.navigation.splash.SplashScreenDestination
import kotlinx.browser.window
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalBrowserHistoryApi::class)
actual suspend fun NavController.bindSystemBackNavigation() {
    // The splash screen removes itself from the stack (popUpTo inclusive), so binding
    // while it is still there would leave a history entry leading to an empty stack.
    currentBackStackEntryFlow.first { entry ->
        !entry.destination.hasRoute<SplashScreenDestination>() && previousBackStackEntry == null
    }

    // The route is not written to the address bar: the URL stays as it is, including
    // ?flavor=, which would otherwise be dropped because bindToBrowserNavigation
    // builds the address from origin + pathname only.
    val query = window.location.search
    bindToBrowserNavigation { query }
}

actual fun NavController.backAction(): () -> Unit = {
    // With a single screen left there is nothing to go back to: history.back() would
    // leave the site.
    if (previousBackStackEntry != null) {
        window.history.back()
    }
}
