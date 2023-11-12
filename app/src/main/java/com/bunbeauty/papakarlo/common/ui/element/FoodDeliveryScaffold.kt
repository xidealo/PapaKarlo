package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryTopAppBar
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryToolbarActions
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryTopAppBar
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryScaffold(
    title: String? = null,
    backActionClick: (() -> Unit)? = null,
    topActions: List<FoodDeliveryToolbarActions> = emptyList(),
    scrollableState: ScrollableState? = null,
    backgroundColor: Color = FoodDeliveryTheme.colors.mainColors.background,
    @DrawableRes drawableId: Int? = null,
    actionButton: @Composable () -> Unit = {},
    appBarContent: @Composable () -> Unit = {},
    content: (@Composable () -> Unit),
) {
    val appBarState = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)
    val scrollBehavior = remember { behavior }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FoodDeliveryTopAppBar(
                title = title,
                backActionClick = backActionClick,
                isScrolled = scrollableState?.canScrollBackward ?: scrollBehavior.isScrolled,
                actions = topActions,
                drawableId = drawableId,
                content = appBarContent
            )
        },
        containerColor = backgroundColor,
        floatingActionButton = actionButton,
        floatingActionButtonPosition = FabPosition.Center,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            content()
        }
    }
}

private val StartScrollContentOffset = -8f

@OptIn(ExperimentalMaterial3Api::class)
private val TopAppBarScrollBehavior.isScrolled: Boolean
    @Composable
    get() = state.contentOffset < StartScrollContentOffset
