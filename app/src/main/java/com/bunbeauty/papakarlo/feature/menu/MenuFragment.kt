package com.bunbeauty.papakarlo.feature.menu

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.papakarlo.common.ui.element.top_bar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.menu.model.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuProductItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuState
import com.bunbeauty.papakarlo.feature.menu.model.MenuUi
import com.bunbeauty.papakarlo.feature.menu.model.MenuUiStateMapper
import com.bunbeauty.papakarlo.feature.menu.ui.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsFragmentDirections.globalConsumerCartFragment
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
class MenuFragment : BaseFragment(R.layout.layout_compose) {

    override val viewModel: MenuViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)
    private val menuUiStateMapper: MenuUiStateMapper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMenu()
        viewBinding.root.setContentWithTheme {
            val menuState by viewModel.menuState.collectAsStateWithLifecycle()
            MenuScreen(menuUi = menuUiStateMapper.map(menuState))
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
    private fun MenuScreen(menuUi: MenuUi) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_menu),
            drawableId = R.drawable.logo_small,
            topActions = listOf(
                FoodDeliveryCartAction(topCartUi = menuUi.topCartUi) {
                    findNavController().navigateSafe(globalConsumerCartFragment())
                }
            ),
        ) {
            when (menuUi.state) {
                is MenuState.State.Success -> {
                    MenuSuccessScreen(menuUi)
                }
                is MenuState.State.Error -> {
                    ErrorScreen(
                        mainTextId = R.string.error_menu_loading,
                        onClick = viewModel::getMenu
                    )
                }
                else -> {
                    LoadingScreen()
                }
            }
        }
    }

    @Composable
    private fun MenuSuccessScreen(menu: MenuUi) {
        Column(modifier = Modifier.fillMaxSize()) {
            val menuLazyListState = rememberLazyListState()
            val menuPosition by remember {
                derivedStateOf {
                    menuLazyListState.firstVisibleItemIndex
                }
            }
            LaunchedEffect(Unit) {
                snapshotFlow { menuPosition }.collect(viewModel::onMenuPositionChanged)
            }
            FoodDeliverySurface(modifier = Modifier.fillMaxWidth()) {
                CategoryRow(menu.categoryItemList, menuLazyListState)
            }
            MenuColumn(menu.menuItemList, menuLazyListState)
        }
    }

    @Composable
    private fun CategoryRow(
        categoryItemList: List<CategoryItem>,
        menuLazyListState: LazyListState,
    ) {
        val coroutineScope = rememberCoroutineScope()
        val categoryLazyListState = rememberLazyListState()
        LazyRow(
            contentPadding = PaddingValues(
                horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                vertical = FoodDeliveryTheme.dimensions.smallSpace,
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
        menuLazyListState: LazyListState,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace),
            state = menuLazyListState
        ) {
            itemsIndexed(
                items = menuItemList,
                key = { _, menuItemModel -> menuItemModel.key }
            ) { i, menuItemModel ->
                when (menuItemModel) {
                    is MenuItem.MenuCategoryHeaderItem -> {
                        if (i > 0) {
                            Spacer(
                                modifier = Modifier.height(FoodDeliveryTheme.dimensions.mediumSpace)
                            )
                        }
                        Text(
                            text = menuItemModel.name,
                            style = FoodDeliveryTheme.typography.titleMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                    is MenuItem.MenuProductPairItem -> {
                        Row(Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                            MenuProductItem(
                                modifier = Modifier.weight(1f),
                                menuProductItem = menuItemModel.firstProduct,
                                onAddProductClick = viewModel::onAddProductClicked,
                                onProductClick = viewModel::onMenuItemClicked,
                            )
                            Spacer(modifier = Modifier.width(FoodDeliveryTheme.dimensions.smallSpace))
                            if (menuItemModel.secondProduct == null) {
                                Spacer(Modifier.weight(1f))
                            } else {
                                MenuProductItem(
                                    modifier = Modifier
                                        .padding(start = FoodDeliveryTheme.dimensions.verySmallSpace)
                                        .weight(1f),
                                    menuProductItem = menuItemModel.secondProduct,
                                    onAddProductClick = viewModel::onAddProductClicked,
                                    onProductClick = viewModel::onMenuItemClicked,
                                )
                            }
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
            newPrice = "99 ₽",
            oldPrice = "100 ₽",
        )

        fun getMenuProductPairItemModel(key: String) = MenuItem.MenuProductPairItem(
            key = key,
            firstProduct = menuProductItemModel,
            secondProduct = menuProductItemModel,
        )

        FoodDeliveryTheme {
            MenuScreen(
                menuUi = MenuUi(
                    categoryItemList = listOf(
                        getCategoryItemModel("1"),
                        getCategoryItemModel("2"),
                        getCategoryItemModel("3"),
                    ),
                    menuItemList = listOf(
                        getMenuCategoryHeaderItemModel("4"),
                        getMenuProductPairItemModel("5"),
                        getMenuProductPairItemModel("6"),
                        getMenuCategoryHeaderItemModel("7"),
                        getMenuProductPairItemModel("8"),
                    ),
                    state = MenuState.State.Success,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2",
                    ),
                )
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
                        count = "2",
                    ),
                ),
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
                        count = "2",
                    ),
                )
            )
        }
    }
}
