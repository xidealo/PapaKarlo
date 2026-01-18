package com.bunbeauty.menu.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.core.Constants.FAB_SNACKBAR_BOTTOM_PADDING
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.ProductDetailsOpenedFrom
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAction
import com.bunbeauty.designsystem.ui.element.FoodDeliveryScaffold
import com.bunbeauty.designsystem.ui.element.TopCartUi
import com.bunbeauty.designsystem.ui.element.button.FoodDeliveryExtendedFab
import com.bunbeauty.designsystem.ui.element.card.BannerCard
import com.bunbeauty.designsystem.ui.screen.ErrorScreen
import com.bunbeauty.menu.presentation.MenuViewModel
import com.bunbeauty.menu.presentation.model.MenuDataState
import com.bunbeauty.menu.ui.mapper.toMenuViewState
import com.bunbeauty.menu.ui.state.MenuItemUi
import com.bunbeauty.menu.ui.state.MenuViewState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.description_ic_discount
import papakarlo.designsystem.generated.resources.error_consumer_cart_add_product
import papakarlo.designsystem.generated.resources.error_menu_loading
import papakarlo.designsystem.generated.resources.ic_cart_24
import papakarlo.designsystem.generated.resources.ic_discount
import papakarlo.designsystem.generated.resources.ic_profile
import papakarlo.designsystem.generated.resources.msg_menu_discount
import papakarlo.designsystem.generated.resources.msg_menu_product_added
import papakarlo.designsystem.generated.resources.title_menu
import papakarlo.designsystem.generated.resources.title_menu_discount

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MenuRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: MenuViewModel = koinViewModel(),
    goToProductDetailsFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) -> Unit,
    goToProfile: () -> Unit,
    goToConsumerCart: () -> Unit,
    showErrorMessage: (String) -> Unit,
    showInfoMessage: (String, Int) -> Unit,
) {
    val viewState by viewModel.menuState.collectAsStateWithLifecycle()

    val consumeEffects =
        remember {
            {
                viewModel.consumeEventList(viewState.eventList)
            }
        }

    MenuEffect(
        effects = viewState.eventList,
        goToProductDetailsFragment = goToProductDetailsFragment,
        consumeEffects = consumeEffects,
        showErrorMessage = showErrorMessage,
        showInfoMessage = showInfoMessage,
    )

    MenuScreen(
        viewState = viewState.toMenuViewState(),
        onMenuPositionChanged = viewModel::onMenuPositionChanged,
        errorAction = viewModel::getMenu,
        goToProfile = goToProfile,
        goToConsumerCart = goToConsumerCart,
        onCategoryClicked = viewModel::onCategoryClicked,
        onStartAutoScroll = viewModel::onStartAutoScroll,
        getMenuListPosition = viewModel::getMenuListPosition,
        onStopAutoScroll = viewModel::onStopAutoScroll,
        onAddProductClicked = viewModel::onAddProductClicked,
        onMenuItemClicked = viewModel::onMenuItemClicked,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
    )
}

@Composable
private fun MenuEffect(
    effects: List<MenuDataState.Event>,
    goToProductDetailsFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) -> Unit,
    consumeEffects: () -> Unit,
    showErrorMessage: (String) -> Unit,
    showInfoMessage: (String, Int) -> Unit,
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                is MenuDataState.Event.GoToSelectedItem -> {
                    goToProductDetailsFragment(
                        effect.uuid,
                        effect.name,
                        ProductDetailsOpenedFrom.MENU_PRODUCT,
                    )
                }

                MenuDataState.Event.ShowAddProductError -> {
                    showErrorMessage(getString(Res.string.error_consumer_cart_add_product))
                }

                is MenuDataState.Event.ShowAddedProduct -> {
                    showInfoMessage(
                        getString(
                            Res.string.msg_menu_product_added,
                        ),
                        FAB_SNACKBAR_BOTTOM_PADDING,
                    )
                }
            }
        }
        consumeEffects()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MenuScreen(
    viewState: MenuViewState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    onMenuPositionChanged: (Int) -> Unit,
    goToProfile: () -> Unit,
    goToConsumerCart: () -> Unit,
    errorAction: () -> Unit,
    onCategoryClicked: (categoryItem: CategoryItem) -> Unit,
    onStartAutoScroll: () -> Unit,
    getMenuListPosition: (categoryItem: CategoryItem) -> Int,
    onStopAutoScroll: () -> Unit,
    onAddProductClicked: (menuProductUuid: String) -> Unit,
    onMenuItemClicked: (menuProductUuid: String) -> Unit,
) {
    val menuLazyGridState = rememberLazyGridState()

    FoodDeliveryScaffold(
        title = stringResource(Res.string.title_menu),
        topActions =
            persistentListOf(
                FoodDeliveryAction(iconId = Res.drawable.ic_profile, onClick = goToProfile),
            ),
        scrollableState = menuLazyGridState,
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
        appBarContent = {
            if (viewState.state is MenuDataState.State.Success) {
                CategoryRow(
                    modifier = Modifier.fillMaxWidth(),
                    categoryItemList = viewState.categoryItemList,
                    menuLazyGridState = menuLazyGridState,
                    onCategoryClicked = onCategoryClicked,
                    onStartAutoScroll = onStartAutoScroll,
                    getMenuListPosition = getMenuListPosition,
                    onStopAutoScroll = onStopAutoScroll,
                )
            }
        },
        actionButton = {
            Crossfade(targetState = viewState.state) { state ->
                if (state is MenuDataState.State.Success) {
                    FoodDeliveryExtendedFab(
                        text = viewState.topCartUi.cost,
                        onClick = goToConsumerCart,
                        icon = Res.drawable.ic_cart_24,
                        iconBadge = viewState.topCartUi.count,
                    )
                }
            }
        },
        floatingActionButtonPosition = Alignment.BottomEnd,
    ) {
        when (viewState.state) {
            is MenuDataState.State.Success -> {
                MenuSuccessScreen(
                    menu = viewState,
                    menuLazyGridState = menuLazyGridState,
                    onMenuPositionChanged = onMenuPositionChanged,
                    onAddProductClicked = onAddProductClicked,
                    onMenuItemClicked = onMenuItemClicked,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                )
            }

            is MenuDataState.State.Error -> {
                ErrorScreen(
                    mainTextId = Res.string.error_menu_loading,
                    onClick = errorAction,
                )
            }

            else -> {
                MenuLoadingScreen()
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MenuSuccessScreen(
    menu: MenuViewState,
    menuLazyGridState: LazyGridState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    onMenuPositionChanged: (Int) -> Unit,
    onAddProductClicked: (menuProductUuid: String) -> Unit,
    onMenuItemClicked: (menuProductUuid: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val menuPosition by remember {
            derivedStateOf {
                menuLazyGridState.firstVisibleItemIndex
            }
        }
        LaunchedEffect(Unit) {
            snapshotFlow { menuPosition }.collect(onMenuPositionChanged)
        }
        MenuColumn(
            menu = menu,
            menuLazyListState = menuLazyGridState,
            onAddProductClicked = onAddProductClicked,
            onMenuItemClicked = onMenuItemClicked,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
        )
    }
}

@Composable
private fun CategoryRow(
    categoryItemList: List<CategoryItem>,
    menuLazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
    onCategoryClicked: (categoryItem: CategoryItem) -> Unit,
    onStartAutoScroll: () -> Unit,
    getMenuListPosition: (categoryItem: CategoryItem) -> Int,
    onStopAutoScroll: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val categoryLazyListState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        contentPadding =
            PaddingValues(
                horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                vertical = FoodDeliveryTheme.dimensions.smallSpace,
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
                    onCategoryClicked(categoryItemModel)
                    coroutineScope.launch {
                        categoryLazyListState.animateScrollToItem(i)
                    }
                    coroutineScope.launch {
                        onStartAutoScroll()
                        menuLazyGridState.animateScrollToItem(
                            getMenuListPosition(categoryItemModel),
                        )
                        onStopAutoScroll()
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MenuColumn(
    menu: MenuViewState,
    menuLazyListState: LazyGridState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    onAddProductClicked: (menuProductUuid: String) -> Unit,
    onMenuItemClicked: (menuProductUuid: String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = spacedBy(8.dp),
        state = menuLazyListState,
        userScrollEnabled = menu.userScrollEnabled,
    ) {
        itemsIndexed(
            items = menu.menuItemList,
            key = { _, menuItemModel -> menuItemModel.key },
            span = { _, menuItemModel ->
                when (menuItemModel) {
                    is MenuItemUi.Discount,
                    is MenuItemUi.CategoryHeader,
                    -> GridItemSpan(maxLineSpan)

                    else -> GridItemSpan(1)
                }
            },
        ) { index, menuItem ->
            when (menuItem) {
                is MenuItemUi.Discount -> {
                    BannerCard(
                        title =
                            stringResource(
                                resource = Res.string.title_menu_discount,
                                menuItem.discount,
                            ),
                        text =
                            stringResource(
                                resource = Res.string.msg_menu_discount,
                                menuItem.discount,
                            ),
                        icon = Res.drawable.ic_discount,
                        iconDescription =
                            stringResource(
                                Res.string.description_ic_discount,
                            ),
                    )
                }

                is MenuItemUi.CategoryHeader -> {
                    Text(
                        modifier =
                            Modifier.padding(
                                top =
                                    if (index == 0) {
                                        0.dp
                                    } else {
                                        16.dp
                                    },
                            ),
                        text = menuItem.name,
                        style = FoodDeliveryTheme.typography.titleMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    )
                }

                is MenuItemUi.Product -> {
                    MenuProductItem(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        modifier = Modifier.padding(top = 8.dp),
                        menuProductItem = menuItem,
                        onAddProductClick = onAddProductClicked,
                        onProductClick = onMenuItemClicked,
                    )
                }
            }
        }
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
            name = "Бургеры",
        )

    fun getMenuProductItem(key: String) =
        MenuItemUi.Product(
            uuid = "",
            key = key,
            photoLink = "",
            name = "Бэргер",
            newPrice = "99",
            oldPrice = "100",
        )
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            FoodDeliveryTheme {
                MenuScreen(
                    viewState =
                        MenuViewState(
                            categoryItemList =
                                persistentListOf(
                                    getCategoryItem("1"),
                                    getCategoryItem("2"),
                                    getCategoryItem("3"),
                                ),
                            menuItemList =
                                persistentListOf(
                                    getMenuCategoryHeaderItem("4"),
                                    getMenuProductItem("5"),
                                    getMenuProductItem("6"),
                                    getMenuCategoryHeaderItem("7"),
                                    getMenuProductItem("8"),
                                ),
                            state = MenuDataState.State.Success,
                            userScrollEnabled = true,
                            topCartUi =
                                TopCartUi(
                                    cost = "100",
                                    count = "2",
                                ),
                            eventList = persistentListOf(),
                        ),
                    onMenuPositionChanged = {},
                    errorAction = {},
                    goToProfile = {},
                    goToConsumerCart = {},
                    onCategoryClicked = {},
                    onStartAutoScroll = {},
                    getMenuListPosition = { 0 },
                    onStopAutoScroll = {},
                    onAddProductClicked = {},
                    onMenuItemClicked = {},
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun MenuScreenLoadingPreview() {
    FoodDeliveryTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                MenuScreen(
                    viewState =
                        MenuViewState(
                            categoryItemList = persistentListOf(),
                            topCartUi =
                                TopCartUi(
                                    cost = "100",
                                    count = "2",
                                ),
                            menuItemList = persistentListOf(),
                            state = MenuDataState.State.Loading,
                            userScrollEnabled = true,
                            eventList = persistentListOf(),
                        ),
                    onMenuPositionChanged = {},
                    errorAction = {},
                    goToProfile = {},
                    goToConsumerCart = {},
                    onCategoryClicked = {},
                    onStartAutoScroll = {},
                    getMenuListPosition = { 0 },
                    onStopAutoScroll = {},
                    onAddProductClicked = {},
                    onMenuItemClicked = {},
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun MenuScreenErrorPreview() {
    FoodDeliveryTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                MenuScreen(
                    viewState =
                        MenuViewState(
                            categoryItemList = persistentListOf(),
                            topCartUi =
                                TopCartUi(
                                    cost = "100",
                                    count = "2",
                                ),
                            menuItemList = persistentListOf(),
                            state = MenuDataState.State.Error(Throwable()),
                            userScrollEnabled = true,
                            eventList = persistentListOf(),
                        ),
                    onMenuPositionChanged = {},
                    errorAction = {},
                    goToProfile = {},
                    goToConsumerCart = {},
                    onCategoryClicked = {},
                    onStartAutoScroll = {},
                    getMenuListPosition = { 0 },
                    onStopAutoScroll = {},
                    onAddProductClicked = {},
                    onMenuItemClicked = {},
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
            }
        }
    }
}
