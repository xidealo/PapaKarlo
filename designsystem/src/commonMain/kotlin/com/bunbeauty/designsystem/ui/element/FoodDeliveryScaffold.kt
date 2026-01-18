package com.bunbeauty.designsystem.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.topbar.FoodDeliveryTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryScaffold(
    modifier: Modifier = Modifier,
    title: String? = null,
    backActionClick: (() -> Unit)? = null,
    topActions: ImmutableList<FoodDeliveryToolbarActions> = persistentListOf(),
    scrollableState: ScrollableState? = null,
    backgroundColor: Color = FoodDeliveryTheme.colors.mainColors.background,
    actionButton: @Composable () -> Unit = {},
    appBarContent: @Composable () -> Unit = {},
    floatingActionButtonPosition: Alignment = Alignment.BottomCenter,
    content: (@Composable () -> Unit),
) {
    val appBarState = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)
    val scrollBehavior = remember { behavior }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .background(backgroundColor)
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            FoodDeliveryTopAppBar(
                title = title,
                backActionClick = backActionClick,
                isScrolled = scrollableState?.canScrollBackward ?: scrollBehavior.isScrolled,
                actions = topActions,
                content = appBarContent,
            )
            content()
        }

        Box(
            modifier = Modifier
                .align(floatingActionButtonPosition)
                .padding(
                    bottom = 20.dp,
                    end = if (floatingActionButtonPosition == Alignment.BottomEnd) {
                        16.dp
                    } else {
                        0.dp
                    }
                )
        ) {
            actionButton()
        }
    }
}

private const val StartScrollContentOffset = -8f

@OptIn(ExperimentalMaterial3Api::class)
private val TopAppBarScrollBehavior.isScrolled: Boolean
    @Composable
    get() = state.contentOffset < StartScrollContentOffset
