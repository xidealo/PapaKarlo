package com.bunbeauty.papakarlo.feature.menu

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.compose.item.CategoryItem
import com.bunbeauty.papakarlo.compose.item.MenuProductItem
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.menu.view_state.CategoryItemModel
import com.bunbeauty.papakarlo.feature.menu.view_state.MenuItemModel
import com.bunbeauty.papakarlo.feature.menu.view_state.MenuProductItemModel
import com.bunbeauty.papakarlo.feature.menu.view_state.MenuUI
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    override val viewModel: MenuViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentMenuCvMain.compose {
            val menuState by viewModel.menuState.collectAsState()
            MenuScreen(menuState = menuState)
        }
    }

    @Composable
    private fun MenuScreen(menuState: State<MenuUI>) {
        if (menuState is State.Success) {
            MenuSuccessScreen(menuState.data)
        } else {

        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun MenuSuccessScreen(menu: MenuUI) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
        ) {
            val menuLazyListState = rememberLazyListState()
            val menuPosition by remember {
                derivedStateOf {
                    menuLazyListState.firstVisibleItemIndex
                }
            }
            LaunchedEffect(Unit) {
                snapshotFlow { menuPosition }.collect(viewModel::onMenuPositionChanged)
            }

            CategoryRow(menu.categoryItemModelList, menuLazyListState)
            MenuColumn(menu.menuItemModelList, menuLazyListState)
        }
    }

    @Composable
    private fun CategoryRow(
        categoryItemModelList: List<CategoryItemModel>,
        menuLazyListState: LazyListState
    ) {
        val coroutineScope = rememberCoroutineScope()
        val categoryLazyListState = rememberLazyListState()

        LazyRow(
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace),
            state = categoryLazyListState
        ) {
            itemsIndexed(
                categoryItemModelList,
                key = { _, categoryItemModel -> categoryItemModel.key }
            ) { i, categoryItemModel ->
                CategoryItem(
                    modifier = Modifier.padding(
                        start = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                    ),
                    categoryItemModel = categoryItemModel
                ) {
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
            }
        }
        SideEffect {
            coroutineScope.launch {
                categoryItemModelList.indexOfFirst { categoryItemModel ->
                    categoryItemModel.isSelected
                }.let { index ->
                    categoryLazyListState.animateScrollToItem(index)
                }
            }
        }
    }

    @Composable
    private fun MenuColumn(
        menuItemModelList: List<MenuItemModel>,
        menuLazyListState: LazyListState
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = FoodDeliveryTheme.dimensions.mediumSpace,
                end = FoodDeliveryTheme.dimensions.mediumSpace,
                bottom = FoodDeliveryTheme.dimensions.mediumSpace,
            ),
            state = menuLazyListState
        ) {
            itemsIndexed(
                items = menuItemModelList,
                key = { _, menuItemModel -> menuItemModel.key }
            ) { i, menuItemModel ->
                when (menuItemModel) {
                    is MenuItemModel.MenuCategoryHeaderItemModel -> {
                        val topSpace = if (i == 0) {
                            0.dp
                        } else {
                            FoodDeliveryTheme.dimensions.mediumSpace
                        }
                        Text(
                            modifier = Modifier.padding(top = topSpace),
                            text = menuItemModel.name,
                            style = FoodDeliveryTheme.typography.h1,
                            color = FoodDeliveryTheme.colors.onBackground
                        )
                    }
                    is MenuItemModel.MenuProductPairItemModel -> {
                        Row(Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                            MenuProductItem(
                                modifier = Modifier
                                    .padding(end = FoodDeliveryTheme.dimensions.verySmallSpace)
                                    .weight(1f),
                                menuProductItemModel = menuItemModel.firstProduct,
                                onButtonClicked = {
                                    viewModel.onAddProductClicked(menuItemModel.firstProduct.uuid)
                                }
                            ) {
                                viewModel.onMenuItemClicked(menuItemModel.firstProduct)
                            }
                            if (menuItemModel.secondProduct == null) {
                                Box(Modifier.weight(1f)) {}
                            } else {
                                MenuProductItem(
                                    modifier = Modifier
                                        .padding(start = FoodDeliveryTheme.dimensions.verySmallSpace)
                                        .weight(1f),
                                    menuProductItemModel = menuItemModel.secondProduct,
                                    onButtonClicked = {
                                        viewModel.onAddProductClicked(menuItemModel.secondProduct.uuid)
                                    }
                                ) {
                                    viewModel.onMenuItemClicked(menuItemModel.secondProduct)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private val categoryItemModel = CategoryItemModel(
        key = "",
        uuid = "",
        name = "Бургеры",
        isSelected = false
    )

    private val menuCategoryHeaderItemModel = MenuItemModel.MenuCategoryHeaderItemModel(
        key = "",
        uuid = "",
        name = "Бургеры"
    )

    private val menuProductItemModel = MenuProductItemModel(
        uuid = "",
        photoLink = "",
        name = "Бэргер",
        newPrice = "99 ₽",
        oldPrice = "100 ₽",
    )

    private val menuProductPairItemModel = MenuItemModel.MenuProductPairItemModel(
        key = "",
        firstProduct = menuProductItemModel,
        secondProduct = menuProductItemModel,
    )

    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    private fun MenuScreenPreview() {
        MenuScreen(
            menuState = State.Success(
                MenuUI(
                    categoryItemModelList = listOf(
                        categoryItemModel,
                        categoryItemModel,
                        categoryItemModel,
                    ),
                    menuItemModelList = listOf(
                        menuCategoryHeaderItemModel,
                        menuProductPairItemModel,
                        menuProductPairItemModel,
                        menuCategoryHeaderItemModel,
                        menuProductPairItemModel,
                    )
                )
            )
        )
    }
}