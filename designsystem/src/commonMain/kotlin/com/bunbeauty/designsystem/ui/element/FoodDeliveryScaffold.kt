package com.bunbeauty.designsystem.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
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
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.topbar.FoodDeliveryTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.DrawableResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryScaffold(
    modifier: Modifier = Modifier,
    title: String? = null,
    backActionClick: (() -> Unit)? = null,
    topActions: ImmutableList<FoodDeliveryToolbarActions> = persistentListOf(),
    scrollableState: ScrollableState? = null,
    backgroundColor: Color = FoodDeliveryTheme.colors.mainColors.background,
    drawableId: DrawableResource? = null,
    actionButton: @Composable () -> Unit = {},
    appBarContent: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.Center,
    content: (@Composable () -> Unit),
) {
    val appBarState = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)
    val scrollBehavior = remember { behavior }

    Scaffold(
        modifier =
            modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FoodDeliveryTopAppBar(
                title = title,
                backActionClick = backActionClick,
                isScrolled = scrollableState?.canScrollBackward ?: scrollBehavior.isScrolled,
                actions = topActions,
                drawableId = drawableId,
                content = appBarContent,
            )
        },
        containerColor = backgroundColor,
        floatingActionButton = actionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(backgroundColor),
        ) {
            content()
        }
    }
}

private val StartScrollContentOffset = -8f

@OptIn(ExperimentalMaterial3Api::class)
private val TopAppBarScrollBehavior.isScrolled: Boolean
    @Composable
    get() = state.contentOffset < _root_ide_package_.com.bunbeauty.designsystem.ui.element.StartScrollContentOffset
