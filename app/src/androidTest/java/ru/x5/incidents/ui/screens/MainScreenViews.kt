package ru.x5.incidents.ui.screens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class MainScreenViews(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<MainScreenViews>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("MainScreen") }
    ) {

    val profileBottomItem:KNode = child {
        hasTestTag("ProfileBottomItem")
    }
}
