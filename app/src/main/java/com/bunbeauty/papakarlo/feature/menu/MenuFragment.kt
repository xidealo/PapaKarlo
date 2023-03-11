package com.bunbeauty.papakarlo.feature.menu

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.FragmentMenuBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.menu.model.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuAction
import com.bunbeauty.papakarlo.feature.menu.model.MenuItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuProductItem
import com.bunbeauty.papakarlo.feature.menu.model.MenuUI
import com.bunbeauty.papakarlo.feature.menu.ui.CategoryItem
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    override val viewModel: MenuViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMenu()
        viewBinding.fragmentMenuCvMain.setContentWithTheme {
            val menuState by viewModel.menuState.collectAsState()
            MenuScreen(menuState = menuState)
        }
        handleActions()
    }

    private fun handleActions() {
        viewModel.actionFlow.startedLaunch { action ->
            when (action) {
                is MenuAction.GoToSelectedItem -> {
                    findNavController().navigate(
                        MenuFragmentDirections.toProductFragment(
                            action.uuid,
                            action.name
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun MenuScreen(menuState: State<MenuUI>) {
        when (menuState) {
            is State.Success -> {
                MenuSuccessScreen(menuState.data)
            }
            is State.Error -> {
                ErrorScreen(R.string.error_menu_loading) {
                    viewModel.getMenu()
                }
            }
            else -> {
                LoadingScreen()
            }
        }
    }

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
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f),
                shadowElevation = FoodDeliveryTheme.dimensions.elevation,
                color = FoodDeliveryTheme.colors.surface
            ) {
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
                start = FoodDeliveryTheme.dimensions.mediumSpace,
                end = FoodDeliveryTheme.dimensions.mediumSpace,
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
                    categoryItem = categoryItemModel
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
                categoryItemList.indexOfFirst { categoryItemModel ->
                    categoryItemModel.isSelected
                }.let { index ->
                    categoryLazyListState.animateScrollToItem(index)
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
                        val topSpace = if (i == 0) {
                            0.dp
                        } else {
                            FoodDeliveryTheme.dimensions.mediumSpace
                        }
                        Text(
                            modifier = Modifier.padding(top = topSpace),
                            text = menuItemModel.name,
                            style = FoodDeliveryTheme.typography.titleMedium.bold,
                            color = FoodDeliveryTheme.colors.onSurface
                        )
                    }
                    is MenuItem.MenuProductPairItem -> {
                        Row(Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                            MenuProductItem(
                                modifier = Modifier
                                    .padding(end = FoodDeliveryTheme.dimensions.verySmallSpace)
                                    .weight(1f),
                                menuProductItem = menuItemModel.firstProduct,
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
                                    menuProductItem = menuItemModel.secondProduct,
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

        MenuScreen(
            menuState = State.Success(
                MenuUI(
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
                    )
                )
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun MenuScreenLoadingPreview() {
        MenuScreen(menuState = State.Loading())
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun MenuScreenErrorPreview() {
        MenuScreen(menuState = State.Error("Не удалось загрузить меню"))
    }
}
