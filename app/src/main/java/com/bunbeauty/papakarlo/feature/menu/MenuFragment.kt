package com.bunbeauty.papakarlo.feature.menu

import android.os.Bundle
import android.view.View
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
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
import com.bunbeauty.papakarlo.common.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.menu.model.MenuUi
import com.bunbeauty.papakarlo.feature.menu.model.MenuUiStateMapper
import com.bunbeauty.papakarlo.feature.menu.ui.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.ui.FirstOrderDiscountItem
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsFragmentDirections.globalConsumerCartFragment
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.menu.CategoryItem
import com.bunbeauty.shared.presentation.menu.MenuItem
import com.bunbeauty.shared.presentation.menu.MenuProductItem
import com.bunbeauty.shared.presentation.menu.MenuState
import com.bunbeauty.shared.presentation.menu.MenuViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    val viewModel: MenuViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)
    private val menuUiStateMapper: MenuUiStateMapper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMenu()
        viewBinding.root.setContentWithTheme {
            val menuState by viewModel.menuState.collectAsStateWithLifecycle()
            MenuScreen(
                menuUi = menuUiStateMapper.map(menuState),
                onMenuPositionChanged = viewModel::onMenuPositionChanged,
                errorAction = viewModel::getMenu
            )
            LaunchedEffect(menuState.eventList) {
                handleEventList(menuState.eventList)
            }
        }
    }

    private fun handleEventList(eventList: List<MenuState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is MenuState.Event.GoToSelectedItem -> {
                    findNavController().navigateSafe(
                        MenuFragmentDirections.toProductFragment(
                            event.uuid,
                            event.name
                        )
                    )
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Composable
    private fun MenuScreen(
        menuUi: MenuUi,
        onMenuPositionChanged: (Int) -> Unit,
        errorAction: () -> Unit
    ) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_menu),
            drawableId = R.drawable.logo_small,
            topActions = listOf(
                FoodDeliveryCartAction(topCartUi = menuUi.topCartUi) {
                    findNavController().navigateSafe(globalConsumerCartFragment())
                }
            )
        ) {
            Crossfade(targetState = menuUi.state, label = "MenuScreen") { state ->
                when (state) {
                    is MenuState.State.Success -> {
                        MenuSuccessScreen(
                            menu = menuUi,
                            onMenuPositionChanged = onMenuPositionChanged
                        )
                    }

                    is MenuState.State.Error -> {
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
    private fun MenuSuccessScreen(menu: MenuUi, onMenuPositionChanged: (Int) -> Unit) {
        Column(modifier = Modifier.fillMaxSize()) {
            val menuLazyGridState = rememberLazyGridState()
            val menuPosition by remember {
                derivedStateOf {
                    menuLazyGridState.firstVisibleItemIndex
                }
            }
            LaunchedEffect(Unit) {
                snapshotFlow { menuPosition }.collect(onMenuPositionChanged)
            }
            FoodDeliverySurface(modifier = Modifier.fillMaxWidth()) {
                CategoryRow(menu.categoryItemList, menuLazyGridState)
            }
            MenuColumn(
                menuItemList = menu.menuItemList,
                discount = menu.discount,
                menuLazyListState = menuLazyGridState
            )
        }
    }

    @Composable
    private fun CategoryRow(
        categoryItemList: List<CategoryItem>,
        menuLazyListState: LazyGridState
    ) {
        val coroutineScope = rememberCoroutineScope()
        val categoryLazyListState = rememberLazyListState()
        LazyRow(
            contentPadding = PaddingValues(
                horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                vertical = FoodDeliveryTheme.dimensions.smallSpace
            ),
            state = categoryLazyListState
        ) {
            itemsIndexed(
                categoryItemList,
                key = { _, categoryItemModel -> categoryItemModel.key }
            ) { i, categoryItemModel ->
                CategoryItem(
                    modifier = Modifier.padding(
                        start = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                    ),
                    categoryItem = categoryItemModel,
                    onClick = {
                        viewModel.onCategoryClicked(categoryItemModel)
                        coroutineScope.launch {
                            categoryLazyListState.animateScrollToItem(i)
                        }
                        coroutineScope.launch {
                            viewModel.autoScrolling = true
                            menuLazyListState.animateScrollToItem(
                                viewModel.getMenuListPosition(categoryItemModel)
                            )
                            viewModel.autoScrolling = false
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
        menuItemList: List<MenuItem>,
        discount: String?,
        menuLazyListState: LazyGridState
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.Absolute.spacedBy(8.dp),
            state = menuLazyListState
        ) {
            discount?.let {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    FirstOrderDiscountItem(discount = discount)
                }
            }
            menuItemList.forEachIndexed { inedex, menuItemModel ->
                when (menuItemModel) {
                    is MenuItem.MenuCategoryHeaderItem -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                modifier = Modifier.padding(
                                    top = if (inedex > 0 || discount != null) {
                                        16.dp
                                    } else {
                                        0.dp
                                    }
                                ),
                                text = menuItemModel.name,
                                style = FoodDeliveryTheme.typography.titleMedium.bold,
                                color = FoodDeliveryTheme.colors.mainColors.onSurface
                            )
                        }
                    }

                    is MenuItem.MenuProductItem -> {
                        item {
                            MenuProductItem(
                                modifier = Modifier.padding(top = 8.dp),
                                menuProductItem = menuItemModel.product,
                                onAddProductClick = viewModel::onAddProductClicked,
                                onProductClick = viewModel::onMenuItemClicked
                            )
                        }
                    }
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun MenuScreenSuccessPreview() {
        fun getCategoryItemModel(key: String) = CategoryItem(
            key = key,
            uuid = "",
            name = "Бургеры",
            isSelected = false
        )

        fun getMenuCategoryHeaderItemModel(key: String) = MenuItem.MenuCategoryHeaderItem(
            key = key,
            uuid = "",
            name = "Бургеры"
        )

        val menuProductItemModel = MenuProductItem(
            uuid = "",
            photoLink = "",
            name = "Бэргер",
            newPrice = 99,
            oldPrice = 100
        )

        fun getMenuProductPairItemModel(key: String) = MenuItem.MenuProductItem(
            key = key,
            product = menuProductItemModel
        )

        FoodDeliveryTheme {
            MenuScreen(
                menuUi = MenuUi(
                    categoryItemList = listOf(
                        getCategoryItemModel("1"),
                        getCategoryItemModel("2"),
                        getCategoryItemModel("3")
                    ),
                    menuItemList = listOf(
                        getMenuCategoryHeaderItemModel("4"),
                        getMenuProductPairItemModel("5"),
                        getMenuProductPairItemModel("6"),
                        getMenuCategoryHeaderItemModel("7"),
                        getMenuProductPairItemModel("8")
                    ),
                    state = MenuState.State.Success,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    ),
                    discount = null
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
                menuUi = MenuUi(
                    state = MenuState.State.Loading,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    ),
                    discount = null
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
                menuUi = MenuUi(
                    state = MenuState.State.Error(Throwable()),
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    ),
                    discount = null
                ),
                onMenuPositionChanged = {},
                errorAction = {}
            )
        }
    }
}
