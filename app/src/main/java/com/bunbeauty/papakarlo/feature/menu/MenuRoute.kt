package com.bunbeauty.papakarlo.feature.menu

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.Crossfade
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.FoodDeliveryExtendedFab
import com.bunbeauty.papakarlo.common.ui.element.card.BannerCard
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryAction
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.menu.mapper.toMenuViewState
import com.bunbeauty.papakarlo.feature.menu.state.MenuItemUi
import com.bunbeauty.papakarlo.feature.menu.state.MenuViewState
import com.bunbeauty.papakarlo.feature.menu.ui.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.menu.MenuViewModel
import com.bunbeauty.shared.presentation.menu.model.CategoryItem
import com.bunbeauty.shared.presentation.menu.model.MenuDataState
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuRoute(
    viewModel: MenuViewModel = koinViewModel(),
    goToProductDetailsFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom
    ) -> Unit,
    goToProfile: () -> Unit,
    goToConsumerCart: () -> Unit
) {
    // TODO
    // overrideBackPressedCallback()

    LaunchedEffect(Unit) {
        viewModel.getMenu()
    }

    val viewState by viewModel.menuState.collectAsStateWithLifecycle()

    val consumeEffects = remember {
        {
            viewModel.consumeEventList(viewState.eventList)
        }
    }

    MenuEffect(
        effects = viewState.eventList,
        goToProductDetailsFragment = goToProductDetailsFragment,
        consumeEffects = consumeEffects
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
        onMenuItemClicked = viewModel::onMenuItemClicked
    )
}

@Composable
private fun MenuEffect(
    effects: List<MenuDataState.Event>,
    goToProductDetailsFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom
    ) -> Unit,
    consumeEffects: () -> Unit
) {
    val activity = LocalActivity.current
    effects.forEach { effect ->
        when (effect) {
            is MenuDataState.Event.GoToSelectedItem -> {
                goToProductDetailsFragment(
                    effect.uuid,
                    effect.name,
                    ProductDetailsOpenedFrom.MENU_PRODUCT
                )
            }

            MenuDataState.Event.ShowAddProductError -> {
                (activity as? IMessageHost)?.showErrorMessage(
                    activity.resources.getString(R.string.error_consumer_cart_add_product)
                )
            }
        }
    }
    consumeEffects()
}

@Composable
private fun MenuScreen(
    viewState: MenuViewState,
    onMenuPositionChanged: (Int) -> Unit,
    goToProfile: () -> Unit,
    goToConsumerCart: () -> Unit,
    errorAction: () -> Unit,
    onCategoryClicked: (categoryItem: CategoryItem) -> Unit,
    onStartAutoScroll: () -> Unit,
    getMenuListPosition: (categoryItem: CategoryItem) -> Int,
    onStopAutoScroll: () -> Unit,
    onAddProductClicked: (menuProductUuid: String) -> Unit,
    onMenuItemClicked: (menuProductUuid: String) -> Unit
) {
    val menuLazyGridState = rememberLazyGridState()

    FoodDeliveryScaffold(
        title = stringResource(R.string.title_menu),
        topActions = persistentListOf(
            FoodDeliveryAction(iconId = R.drawable.ic_profile, onClick = goToProfile)
        ),
        scrollableState = menuLazyGridState,
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
        drawableId = R.drawable.logo_small,
        appBarContent = {
            if (viewState.state is MenuDataState.State.Success) {
                CategoryRow(
                    modifier = Modifier.fillMaxWidth(),
                    categoryItemList = viewState.categoryItemList,
                    menuLazyGridState = menuLazyGridState,
                    onCategoryClicked = onCategoryClicked,
                    onStartAutoScroll = onStartAutoScroll,
                    getMenuListPosition = getMenuListPosition,
                    onStopAutoScroll = onStopAutoScroll
                )
            }
        },
        actionButton = {
            Crossfade(targetState = viewState.state) { state ->
                if (state is MenuDataState.State.Success) {
                    FoodDeliveryExtendedFab(
                        text = viewState.topCartUi.cost,
                        onClick = goToConsumerCart,
                        icon = R.drawable.ic_cart_24,
                        iconBadge = viewState.topCartUi.count
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Crossfade(
            targetState = viewState.state,
            label = "MenuScreen"
        ) { state ->
            when (state) {
                is MenuDataState.State.Success -> {
                    MenuSuccessScreen(
                        menu = viewState,
                        menuLazyGridState = menuLazyGridState,
                        onMenuPositionChanged = onMenuPositionChanged,
                        onAddProductClicked = onAddProductClicked,
                        onMenuItemClicked = onMenuItemClicked
                    )
                }

                is MenuDataState.State.Error -> {
                    ErrorScreen(
                        mainTextId = R.string.error_menu_loading,
                        onClick = errorAction
                    )
                }

                else -> {
                    LoadingScreen()
                }
            }
        }
    }
}

@Composable
private fun MenuSuccessScreen(
    menu: MenuViewState,
    menuLazyGridState: LazyGridState,
    onMenuPositionChanged: (Int) -> Unit,
    onAddProductClicked: (menuProductUuid: String) -> Unit,
    onMenuItemClicked: (menuProductUuid: String) -> Unit
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
            onMenuItemClicked = onMenuItemClicked
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
    onStopAutoScroll: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val categoryLazyListState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
            vertical = FoodDeliveryTheme.dimensions.smallSpace
        ),
        state = categoryLazyListState,
        horizontalArrangement = spacedBy(8.dp)
    ) {
        itemsIndexed(
            categoryItemList,
            key = { _, categoryItemModel -> categoryItemModel.key }
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
                            getMenuListPosition(categoryItemModel)
                        )
                        onStopAutoScroll()
                    }
                }
            )
        }
    }
    SideEffect {
        coroutineScope.launch {
            categoryItemList.indexOfFirst { categoryItemModel ->
                categoryItemModel.isSelected
            }.let { index ->
                if (index >= 0) {
                    categoryLazyListState.animateScrollToItem(index)
                }
            }
        }
    }
}

@Composable
private fun MenuColumn(
    menu: MenuViewState,
    menuLazyListState: LazyGridState,
    onAddProductClicked: (menuProductUuid: String) -> Unit,
    onMenuItemClicked: (menuProductUuid: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = spacedBy(8.dp),
        state = menuLazyListState,
        userScrollEnabled = menu.userScrollEnabled
    ) {
        itemsIndexed(
            items = menu.menuItemList,
            key = { _, menuItemModel -> menuItemModel.key },
            span = { _, menuItemModel ->
                when (menuItemModel) {
                    is MenuItemUi.Discount,
                    is MenuItemUi.CategoryHeader
                    -> GridItemSpan(maxLineSpan)

                    else -> GridItemSpan(1)
                }
            }
        ) { index, menuItem ->
            when (menuItem) {
                is MenuItemUi.Discount -> {
                    BannerCard(
                        title = stringResource(
                            id = R.string.title_menu_discount,
                            menuItem.discount
                        ),
                        text = stringResource(
                            id = R.string.msg_menu_discount,
                            menuItem.discount
                        ),
                        icon = R.drawable.ic_discount,
                        iconDescription = stringResource(
                            R.string.description_ic_discount
                        )
                    )
                }

                is MenuItemUi.CategoryHeader -> {
                    Text(
                        modifier = Modifier.padding(
                            top = if (index == 0) {
                                0.dp
                            } else {
                                16.dp
                            }
                        ),
                        text = menuItem.name,
                        style = FoodDeliveryTheme.typography.titleMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                }

                is MenuItemUi.Product -> {
                    MenuProductItem(
                        modifier = Modifier.padding(top = 8.dp),
                        menuProductItem = menuItem,
                        onAddProductClick = onAddProductClicked,
                        onProductClick = onMenuItemClicked
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MenuScreenSuccessPreview() {
    fun getCategoryItem(key: String) = CategoryItem(
        key = key,
        uuid = "",
        name = "Бургеры",
        isSelected = false
    )

    fun getMenuCategoryHeaderItem(key: String) = MenuItemUi.CategoryHeader(
        key = key,
        name = "Бургеры"
    )

    fun getMenuProductItem(key: String) = MenuItemUi.Product(
        uuid = "",
        key = key,
        photoLink = "",
        name = "Бэргер",
        newPrice = "99",
        oldPrice = "100"
    )

    FoodDeliveryTheme {
        MenuScreen(
            viewState = MenuViewState(
                categoryItemList = persistentListOf(
                    getCategoryItem("1"),
                    getCategoryItem("2"),
                    getCategoryItem("3")
                ),
                menuItemList = persistentListOf(
                    getMenuCategoryHeaderItem("4"),
                    getMenuProductItem("5"),
                    getMenuProductItem("6"),
                    getMenuCategoryHeaderItem("7"),
                    getMenuProductItem("8")
                ),
                state = MenuDataState.State.Success,
                userScrollEnabled = true,
                topCartUi = TopCartUi(
                    cost = "100",
                    count = "2"
                ),
                eventList = persistentListOf()
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
            onMenuItemClicked = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MenuScreenLoadingPreview() {
    FoodDeliveryTheme {
        MenuScreen(
            viewState = MenuViewState(
                categoryItemList = persistentListOf(),
                topCartUi = TopCartUi(
                    cost = "100",
                    count = "2"
                ),
                menuItemList = persistentListOf(),
                state = MenuDataState.State.Loading,
                userScrollEnabled = true,
                eventList = persistentListOf()
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
            onMenuItemClicked = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MenuScreenErrorPreview() {
    FoodDeliveryTheme {
        MenuScreen(
            viewState = MenuViewState(
                categoryItemList = persistentListOf(),
                topCartUi = TopCartUi(
                    cost = "100",
                    count = "2"
                ),
                menuItemList = persistentListOf(),
                state = MenuDataState.State.Error(Throwable()),
                userScrollEnabled = true,
                eventList = persistentListOf()
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
            onMenuItemClicked = {}
        )
    }
}
