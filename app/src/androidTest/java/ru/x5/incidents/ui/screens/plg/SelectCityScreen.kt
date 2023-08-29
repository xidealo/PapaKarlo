package ru.x5.incidents.ui.screens.plg

import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasTestTag
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListItemNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListNode

class SelectCityScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<SelectCityScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("SelectCityScreen") }
    ) {

    val list = KLazyListNode(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("LazyList") },
        itemTypeBuilder = {
            itemType(::LazyListItemNode)
        },
        positionMatcher = { position -> hasTestTag("position=$position")
        }
    )


    //override val viewClass: Class<*> = MainActivity::class.java

    // val bottomNavMenu: MainBottomNavigationMenu = MainBottomNavigationMenu
}
class LazyListItemNode(
    semanticsNode: SemanticsNode,
    semanticsProvider: SemanticsNodeInteractionsProvider,
) : KLazyListItemNode<LazyListItemNode>(semanticsNode, semanticsProvider)
