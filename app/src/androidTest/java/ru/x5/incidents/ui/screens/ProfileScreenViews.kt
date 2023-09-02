package ru.x5.incidents.ui.screens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class ProfileScreenViews(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<ProfileScreenViews>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("ProfileScreen") }
    ) {

    val enterProfileButton: KNode = child {
        hasTestTag("EnterProfileButton")
    }
}