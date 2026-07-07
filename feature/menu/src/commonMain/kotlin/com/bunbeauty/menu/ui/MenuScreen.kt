package com.bunbeauty.menu.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.ProductUi
import com.bunbeauty.core.model.date_time.Date
import com.bunbeauty.core.model.date_time.DateTime
import com.bunbeauty.core.model.date_time.Time
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.core.model.order.OrderStatus
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.SharedTransitionPreview
import com.bunbeauty.designsystem.ui.element.FoodDeliveryScaffold
import com.bunbeauty.designsystem.ui.element.TopCartUi
import com.bunbeauty.designsystem.ui.element.button.FoodDeliveryExtendedFab
import com.bunbeauty.designsystem.ui.screen.ErrorScreen
import com.bunbeauty.menu.presentation.MenuState
import com.bunbeauty.menu.ui.state.MenuItemUi
import com.bunbeauty.menu.ui.state.MenuViewState
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.error_menu_loading
import papakarlo.designsystem.generated.resources.ic_cart_24

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MenuScreen(
    viewState: MenuViewState,
    animatedContentScope: AnimatedVisibilityScope,
    onAction: (MenuState.Action) -> Unit,
) {
    val menuLazyGridState = rememberLazyGridState()

    FoodDeliveryScaffold(
        scrollableState = menuLazyGridState,
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
        actionButton = {
            Crossfade(targetState = viewState.state) { state ->
                if (state == MenuViewState.State.Success) {
                    FoodDeliveryExtendedFab(
                        text = viewState.topCartUi.cost,
                        onClick = { onAction(MenuState.Action.OnCartClicked) },
                        icon = Res.drawable.ic_cart_24,
                        iconBadge = viewState.topCartUi.count,
                    )
                }
            }
        },
        floatingActionButtonPosition = Alignment.BottomEnd,
    ) {
        when (viewState.state) {
            MenuViewState.State.Success -> {
                MenuSuccessScreen(
                    menu = viewState,
                    menuLazyGridState = menuLazyGridState,
                    onAction = onAction,
                    animatedContentScope = animatedContentScope,
                )
            }

            MenuViewState.State.Error -> {
                ErrorScreen(
                    mainTextId = Res.string.error_menu_loading,
                    onClick = { onAction(MenuState.Action.OnRefreshClicked) },
                )
            }

            MenuViewState.State.Loading -> {
                MenuLoadingScreen()
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MenuSuccessScreen(
    menu: MenuViewState,
    menuLazyGridState: androidx.compose.foundation.lazy.grid.LazyGridState,
    animatedContentScope: AnimatedVisibilityScope,
    onAction: (MenuState.Action) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val menuPosition by remember {
            derivedStateOf {
                menuLazyGridState.firstVisibleItemIndex
            }
        }
        LaunchedEffect(Unit) {
            snapshotFlow { menuPosition }.collect { position ->
                onAction(MenuState.Action.OnMenuPositionChanged(position))
            }
        }
        LaunchedEffect(menu.scrollToTopRequest) {
            if (menu.scrollToTopRequest > 0) {
                onAction(MenuState.Action.OnStartAutoScroll)
                try {
                    menuLazyGridState.scrollToItem(index = 0, scrollOffset = 0)
                } finally {
                    onAction(MenuState.Action.OnStopAutoScroll)
                }
            }
        }
        MenuColumn(
            menu = menu,
            menuLazyListState = menuLazyGridState,
            animatedContentScope = animatedContentScope,
            onAction = onAction,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun MenuScreenSuccessPreview() {
    fun getCategoryItem(key: String) =
        CategoryItem(
            key = key,
            uuid = "",
            name = "Бургеры",
            isSelected = false,
        )

    fun getMenuCategoryHeaderItem(key: String) =
        MenuItemUi.CategoryHeader(
            key = key,
            uuid = key,
            name = "Бургеры",
        )

    fun getMenuProductItem(key: String): MenuItemUi.Product {
        val product =
            ProductUi(
                uuid = key,
                key = key,
                photoLink = "",
                name = "Бэргер",
                newPrice = "99",
                oldPrice = "100",
            )
        return MenuItemUi.Product(
            key = key,
            product = product,
        )
    }
    SharedTransitionLayout {
        SharedTransitionPreview {
            MenuScreen(
                viewState =
                    MenuViewState(
                        categoryItemList =
                            persistentListOf(
                                getCategoryItem("1"),
                                getCategoryItem("2"),
                                getCategoryItem("3"),
                                getCategoryItem("4"),
                                getCategoryItem("5"),
                                getCategoryItem("6"),
                            ),
                        favoriteProductList = persistentListOf(),
                        hasFavoritesSection = false,
                        menuItemList =
                            persistentListOf(
                                getMenuCategoryHeaderItem("4"),
                                getMenuProductItem("5"),
                                getMenuProductItem("6"),
                                getMenuCategoryHeaderItem("7"),
                                getMenuProductItem("8"),
                            ),
                        state = MenuViewState.State.Success,
                        userScrollEnabled = true,
                        topCartUi =
                            TopCartUi(
                                cost = "100",
                                count = "2",
                            ),
                        lastOrder =
                            LightOrder(
                                uuid = "uuid",
                                status = OrderStatus.DONE,
                                code = "code",
                                dateTime =
                                    DateTime(
                                        date =
                                            Date(
                                                dayOfMonth = 5474,
                                                monthNumber = 7337,
                                                year = 1992,
                                            ),
                                        time = Time(hours = 3796, minutes = 8009),
                                    ),
                            ),
                    ),
                animatedContentScope = this,
                onAction = {},
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun MenuScreenLoadingPreview() {
    FoodDeliveryTheme {
        SharedTransitionPreview {
            MenuScreen(
                viewState =
                    MenuViewState(
                        categoryItemList = persistentListOf(),
                        favoriteProductList = persistentListOf(),
                        hasFavoritesSection = false,
                        topCartUi =
                            TopCartUi(
                                cost = "100",
                                count = "2",
                            ),
                        menuItemList = persistentListOf(),
                        state = MenuViewState.State.Loading,
                        userScrollEnabled = true,
                        lastOrder = null,
                    ),
                animatedContentScope = this,
                onAction = {},
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun MenuScreenErrorPreview() {
    FoodDeliveryTheme {
        SharedTransitionPreview {
            MenuScreen(
                viewState =
                    MenuViewState(
                        categoryItemList = persistentListOf(),
                        favoriteProductList = persistentListOf(),
                        hasFavoritesSection = false,
                        topCartUi =
                            TopCartUi(
                                cost = "100",
                                count = "2",
                            ),
                        menuItemList = persistentListOf(),
                        state = MenuViewState.State.Error,
                        userScrollEnabled = true,
                        lastOrder = null,
                    ),
                animatedContentScope = this,
                onAction = {},
            )
        }
    }
}
