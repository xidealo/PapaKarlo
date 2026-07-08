package com.bunbeauty.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.designsystem.ui.LocalStatusBarColor
import com.bunbeauty.menu.presentation.MenuState
import com.bunbeauty.menu.ui.mapper.getMenuListPosition
import com.bunbeauty.menu.ui.state.MenuItemUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

private const val MENU_CATEGORY_ROW_INDEX = 1

@Composable
internal fun MenuCategoryRow(
    categoryItemList: ImmutableList<CategoryItem>,
    menuItemList: ImmutableList<MenuItemUi>,
    hasFavoritesSection: Boolean,
    menuLazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
    onAction: (MenuState.Action) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val categoryLazyListState = rememberLazyListState()
    LazyRow(
        modifier =
            modifier
                .background(
                    color =
                        LocalStatusBarColor.current.value.copy(
                            alpha = 0.95f,
                        ),
                ),
        contentPadding =
            PaddingValues(
                top =
                    12.dp +
                        with(LocalDensity.current) {
                            WindowInsets.statusBars.getTop(this).toDp()
                        },
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        state = categoryLazyListState,
        horizontalArrangement = spacedBy(8.dp),
    ) {
        itemsIndexed(
            categoryItemList,
            key = { _, categoryItemModel -> categoryItemModel.key },
        ) { i, categoryItemModel ->
            CategoryItem(
                categoryItem = categoryItemModel,
                onClick = {
                    onAction(MenuState.Action.OnCategoryClicked(categoryItemModel))
                    coroutineScope.launch {
                        categoryLazyListState.animateScrollToItem(i)
                    }
                    coroutineScope.launch {
                        onAction(MenuState.Action.OnStartAutoScroll)
                        try {
                            val index =
                                getMenuListPosition(
                                    categoryItem = categoryItemModel,
                                    menuItemList = menuItemList,
                                    hasFavoritesSection = hasFavoritesSection,
                                )
                            menuLazyGridState.alignCategoryHeaderUnderCategoryRow(
                                headerGridIndex = index,
                            )
                        } finally {
                            onAction(MenuState.Action.OnStopAutoScroll)
                        }
                    }
                },
            )
        }
    }
    SideEffect {
        coroutineScope.launch {
            categoryItemList
                .indexOfFirst { categoryItemModel ->
                    categoryItemModel.isSelected
                }.let { index ->
                    if (index >= 0) {
                        categoryLazyListState.animateScrollToItem(index)
                    }
                }
        }
    }
}

internal suspend fun LazyGridState.alignCategoryHeaderUnderCategoryRow(headerGridIndex: Int) {
    if (headerGridIndex < 0) return

    scrollToItem(
        index = headerGridIndex,
        scrollOffset = 0,
    )
    val categoryRowLayoutInfo =
        layoutInfo.visibleItemsInfo.firstOrNull { item ->
            item.index == MENU_CATEGORY_ROW_INDEX
        }
    val targetScrollOffset =
        categoryRowLayoutInfo?.let { item ->
            -(item.offset.y + item.size.height)
        } ?: 0
    animateScrollToItem(
        index = headerGridIndex,
        scrollOffset = targetScrollOffset,
    )
}
