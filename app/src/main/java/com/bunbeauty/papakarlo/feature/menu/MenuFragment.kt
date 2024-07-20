package com.bunbeauty.papakarlo.feature.menu

import android.os.Bundle
import android.view.View
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
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.menu.mapper.toMenuViewState
import com.bunbeauty.papakarlo.feature.menu.state.MenuItemUi
import com.bunbeauty.papakarlo.feature.menu.state.MenuViewState
import com.bunbeauty.papakarlo.feature.menu.ui.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.ui.FirstOrderDiscountItem
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsFragmentDirections.globalConsumerCartFragment
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.menu.MenuViewModel
import com.bunbeauty.shared.presentation.menu.model.CategoryItem
import com.bunbeauty.shared.presentation.menu.model.MenuDataState
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    val viewModel: MenuViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMenu()
        viewBinding.root.setContentWithTheme {
            val menuState by viewModel.menuState.collectAsStateWithLifecycle()
            MenuScreen(
                viewState = menuState.toMenuViewState(),
                onMenuPositionChanged = viewModel::onMenuPositionChanged,
                errorAction = viewModel::getMenu
            )
            LaunchedEffect(menuState.eventList) {
                handleEventList(menuState.eventList)
            }
        }
    }

    private fun handleEventList(eventList: List<MenuDataState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is MenuDataState.Event.GoToSelectedItem -> {
                    findNavController().navigateSafe(
                        MenuFragmentDirections.toProductFragment(
                            event.uuid,
                            event.name,
                            ProductDetailsOpenedFrom.MENU_PRODUCT,
                            emptyArray(),
                            null
                        )
                    )
                }

                MenuDataState.Event.ShowAddProductError -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        resources.getString(R.string.error_consumer_cart_add_product)
                    )
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Composable
    private fun MenuScreen(
        viewState: MenuViewState,
        onMenuPositionChanged: (Int) -> Unit,
        errorAction: () -> Unit,
    ) {
        val menuLazyGridState = rememberLazyGridState()

        FoodDeliveryScaffold(
            title = stringResource(R.string.title_menu),
            topActions = listOf(
                FoodDeliveryCartAction(topCartUi = viewState.topCartUi) {
                    findNavController().navigateSafe(globalConsumerCartFragment())
                }
            ),
            scrollableState = menuLazyGridState,
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
            drawableId = R.drawable.logo_small,
            appBarContent = {
                if (viewState.state is MenuDataState.State.Success) {
                    CategoryRow(
                        modifier = Modifier.fillMaxWidth(),
                        categoryItemList = viewState.categoryItemList,
                        menuLazyGridState = menuLazyGridState
                    )
                }
            }
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
                            onMenuPositionChanged = onMenuPositionChanged
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
                menuLazyListState = menuLazyGridState
            )
        }
    }

    @Composable
    private fun CategoryRow(
        categoryItemList: List<CategoryItem>,
        menuLazyGridState: LazyGridState,
        modifier: Modifier = Modifier,
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
                        viewModel.onCategoryClicked(categoryItemModel)
                        coroutineScope.launch {
                            categoryLazyListState.animateScrollToItem(i)
                        }
                        coroutineScope.launch {
                            viewModel.onStartAutoScroll()
                            menuLazyGridState.animateScrollToItem(
                                viewModel.getMenuListPosition(categoryItemModel)
                            )
                            viewModel.onStopAutoScroll()
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
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace),
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
                        is MenuItemUi.CategoryHeader,
                        -> GridItemSpan(maxLineSpan)

                        else -> GridItemSpan(1)
                    }
                }
            ) { index, menuItem ->
                when (menuItem) {
                    is MenuItemUi.Discount -> {
                        FirstOrderDiscountItem(
                            discount = menuItem.discount
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
                            onAddProductClick = viewModel::onAddProductClicked,
                            onProductClick = viewModel::onMenuItemClicked
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
                errorAction = {}
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
                errorAction = {}
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
                errorAction = {}
            )
        }
    }
}
