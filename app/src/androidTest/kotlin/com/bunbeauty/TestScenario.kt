package com.bunbeauty

import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasTestTag
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.lazylist.KLazyListItemNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListNode

class SelectCityScreenViews(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<SelectCityScreenViews>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("SelectCityScreen") }
    ) {

    val list = KLazyListNode(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("SelectCitySuccessScreenLazyColumn") },
        itemTypeBuilder = {
            itemType(::LazyListItemNode)
        },
        positionMatcher = { position -> hasTestTag("position=$position")
        }
    )
}
class LazyListItemNode(
    semanticsNode: SemanticsNode,
    semanticsProvider: SemanticsNodeInteractionsProvider,
) : KLazyListItemNode<LazyListItemNode>(semanticsNode, semanticsProvider)
