package com.bunbeauty.papakarlo.feature.productdetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseComposeFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryItem
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsFragmentDirections.globalConsumerCartFragment
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.product_details.AdditionItem
import com.bunbeauty.shared.presentation.product_details.MenuProductAdditionItem
import com.bunbeauty.shared.presentation.product_details.ProductDetailsState
import com.bunbeauty.shared.presentation.product_details.ProductDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsFragment :
    BaseComposeFragment<ProductDetailsState.ViewDataState, ProductDetailsUi, ProductDetailsState.Action, ProductDetailsState.Event>() {

    override val viewModel: ProductDetailsViewModel by viewModel()

    private val args: ProductDetailsFragmentArgs by navArgs()

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    private val productDetailsUiStateMapper: ProductDetailsUiStateMapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onAction(
            ProductDetailsState.Action.Init(
                menuProductUuid = args.menuProductUuid,
                selectedAdditionUuidList = args.additionUuidList.toList()
            )
        )
    }

    @Composable
    override fun Screen(
        viewState: ProductDetailsUi,
        onAction: (ProductDetailsState.Action) -> Unit,
    ) {
        ProductDetailsScreen(
            menuProductName = args.menuProductName,
            menuProductUuid = args.menuProductUuid,
            additionUuidList = args.additionUuidList.toList(),
            productDetailsUi = viewState,
            onAction = viewModel::onAction
        )
    }

    override fun mapState(dataState: ProductDetailsState.ViewDataState): ProductDetailsUi {
        return productDetailsUiStateMapper.map(dataState)
    }

    override fun handleEvent(event: ProductDetailsState.Event) {
        when (event) {
            ProductDetailsState.Event.NavigateBack -> findNavController().popBackStack()
            ProductDetailsState.Event.NavigateToConsumerCart -> findNavController()
                .navigateSafe(globalConsumerCartFragment())

            //set result?
            is ProductDetailsState.Event.AddedProduct -> findNavController().popBackStack()
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    private fun ProductDetailsScreen(
        menuProductName: String,
        menuProductUuid: String,
        additionUuidList: List<String>,
        productDetailsUi: ProductDetailsUi,
        onAction: (ProductDetailsState.Action) -> Unit,
    ) {
        FoodDeliveryScaffold(
            title = menuProductName,
            backActionClick = {
                onAction(ProductDetailsState.Action.BackClick)
            },
            topActions = if (productDetailsUi is ProductDetailsUi.Success) {
                listOf(
                    FoodDeliveryCartAction(topCartUi = productDetailsUi.topCartUi) {
                        val backQueue = findNavController().currentBackStack.value
                        if ((backQueue.size > 1) &&
                            (backQueue[backQueue.lastIndex - 1].destination.id == R.id.consumerCartFragment)
                        ) {
                            onAction(ProductDetailsState.Action.BackClick)
                        } else {
                            onAction(ProductDetailsState.Action.CartClick)
                        }
                    }
                )
            } else {
                emptyList()
            },
            actionButton = {
                if (productDetailsUi is ProductDetailsUi.Success) {
                    MainButton(
                        modifier = Modifier
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        text = stringResource(
                            id = R.string.action_product_details_want,
                            productDetailsUi.menuProductUi?.priceWithAdditions ?: "",

                            )
                    ) {
                        onAction(
                            ProductDetailsState.Action.AddProductToCartClick(
                                productDetailsOpenedFrom = args.productDetailsOpenedFrom
                            )
                        )
                    }
                }
            },
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface
        ) {
            when (productDetailsUi) {
                is ProductDetailsUi.Success -> {
                    ProductDetailsSuccessScreen(productDetailsUi.menuProductUi, onAction = onAction)
                }

                is ProductDetailsUi.Loading -> {
                    LoadingScreen()
                }

                is ProductDetailsUi.Error -> {
                    ErrorScreen(mainTextId = R.string.common_error) {
                        onAction(
                            ProductDetailsState.Action.Init(
                                menuProductUuid = menuProductUuid,
                                selectedAdditionUuidList = additionUuidList
                            )
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ProductDetailsSuccessScreen(
        menuProductUi: ProductDetailsUi.Success.MenuProductUi?,
        onAction: (ProductDetailsState.Action) -> Unit,
    ) {
        menuProductUi?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {
                    ProductCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                            .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        menuProductUi = menuProductUi
                    )
                }
                items(
                    menuProductUi.additionList,
                    key = { menuProductAdditionItem ->
                        menuProductAdditionItem.key
                    }
                ) { menuProductAdditionItem ->
                    when (menuProductAdditionItem) {
                        is AdditionItem.AdditionHeaderItem -> {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 24.dp),
                                text = menuProductAdditionItem.name,
                                style = FoodDeliveryTheme.typography.titleMedium.bold,
                                color = FoodDeliveryTheme.colors.mainColors.onSurface
                            )
                        }

                        is AdditionItem.AdditionSingleListItem -> {
                            FoodDeliveryItem(needDivider = !menuProductAdditionItem.product.isLast) {
                                AdditionItem(
                                    menuProductAdditionItem = menuProductAdditionItem.product,
                                    isMultiply = false,
                                    onAction = onAction
                                )
                            }
                        }

                        is AdditionItem.AdditionMultiplyListItem -> {
                            FoodDeliveryItem(needDivider = !menuProductAdditionItem.product.isLast) {
                                AdditionItem(
                                    menuProductAdditionItem = menuProductAdditionItem.product,
                                    isMultiply = true,
                                    onAction = onAction
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(FoodDeliveryTheme.dimensions.scrollScreenBottomSpace))
                }
            }
        }
    }

    @Composable
    private fun AdditionItem(
        menuProductAdditionItem: MenuProductAdditionItem,
        isMultiply: Boolean,
        onAction: (ProductDetailsState.Action) -> Unit,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(FoodDeliveryCardDefaults.cardShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menuProductAdditionItem.photoLink)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_small),
                contentDescription = stringResource(R.string.description_product_addition),
                contentScale = ContentScale.FillWidth,
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                text = menuProductAdditionItem.name,
                style = FoodDeliveryTheme.typography.bodyLarge,
            )

            menuProductAdditionItem.price?.let { price ->
                Text(
                    modifier = Modifier,
                    text = price,
                    style = FoodDeliveryTheme.typography.bodyLarge,
                )
            }

            if (isMultiply) {
                Checkbox(
                    checked = menuProductAdditionItem.isSelected,
                    onCheckedChange = {
                        onAction(
                            ProductDetailsState.Action.AdditionClick(
                                uuid = menuProductAdditionItem.uuid,
                                groupUuid = menuProductAdditionItem.groupId
                            )
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = FoodDeliveryTheme.colors.mainColors.primary,
                        uncheckedColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    ),
                )
            } else {
                RadioButton(
                    selected = menuProductAdditionItem.isSelected,
                    onClick = {
                        onAction(
                            ProductDetailsState.Action.AdditionClick(
                                uuid = menuProductAdditionItem.uuid,
                                groupUuid = menuProductAdditionItem.groupId
                            )
                        )
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = FoodDeliveryTheme.colors.mainColors.primary,
                        unselectedColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    ),
                )
            }
        }
    }

    @Composable
    private fun ProductCard(
        modifier: Modifier = Modifier,
        menuProductUi: ProductDetailsUi.Success.MenuProductUi,
    ) {
        FoodDeliveryCard(
            modifier = modifier,
            clickable = false,
            elevated = false
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(FoodDeliveryCardDefaults.cardShape),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(menuProductUi.photoLink)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder_large),
                    contentDescription = stringResource(R.string.description_product),
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                ) {
                    Row {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .alignByBaseline()
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = menuProductUi.name,
                            style = FoodDeliveryTheme.typography.titleMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                        Text(
                            modifier = Modifier.alignByBaseline(),
                            text = menuProductUi.size,
                            style = FoodDeliveryTheme.typography.bodySmall,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
                    ) {
                        menuProductUi.oldPrice?.let {
                            Text(
                                modifier = Modifier
                                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                                text = menuProductUi.oldPrice,
                                style = FoodDeliveryTheme.typography.bodyLarge,
                                color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        Text(
                            text = menuProductUi.newPrice,
                            style = FoodDeliveryTheme.typography.bodyLarge.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        text = menuProductUi.description,
                        style = FoodDeliveryTheme.typography.bodyLarge,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ProductDetailsSuccessScreenPreview() {
        FoodDeliveryTheme {
            ProductDetailsScreen(
                menuProductName = "Бэргер куриный Макс с экстра сырным соусом",
                menuProductUuid = "",
                productDetailsUi = ProductDetailsUi.Success(
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    ),
                    menuProductUi = ProductDetailsUi.Success.MenuProductUi(
                        photoLink = "",
                        name = "Бэргер куриный Макс с экстра сырным соусом",
                        size = "300 г",
                        oldPrice = "320 ₽",
                        newPrice = "280 ₽",
                        description = "Сочная котлетка, сыр Чедр, маринованный огурчик, помидор, " +
                                "красный лук, салат, фирменный соус, булочка с кунжутом",
                        additionList = listOf(
                            AdditionItem.AdditionHeaderItem(
                                key = "key1",
                                uuid = "uuid1",
                                name = "Булочка",
                            ),
                            AdditionItem.AdditionSingleListItem(
                                key = "key2",
                                product = MenuProductAdditionItem(
                                    uuid = "uuid2",
                                    isSelected = true,
                                    name = "БулОЧКА Валентина",
                                    price = "+200",
                                    photoLink = "",
                                    isLast = false,
                                    groupId = ""
                                )
                            ),
                            AdditionItem.AdditionSingleListItem(
                                key = "key3",
                                product = MenuProductAdditionItem(
                                    uuid = "uuid3",
                                    isSelected = false,
                                    name = "БулОЧКА Марка",
                                    price = "300",
                                    photoLink = "",
                                    isLast = true,
                                    groupId = ""
                                )
                            ),
                            AdditionItem.AdditionHeaderItem(
                                key = "key4",
                                uuid = "uuid4",
                                name = "Добавить по вкусу",
                            ),
                            AdditionItem.AdditionMultiplyListItem(
                                key = "key5",
                                product = MenuProductAdditionItem(
                                    uuid = "uuid5",
                                    isSelected = true,
                                    name = "Монкейэс",
                                    price = "13",
                                    photoLink = "",
                                    isLast = false,
                                    groupId = ""
                                )
                            ),
                            AdditionItem.AdditionMultiplyListItem(
                                key = "key6",
                                product = MenuProductAdditionItem(
                                    uuid = "uuid6",
                                    isSelected = false,
                                    name = "Лида в лаваше",
                                    price = "2",
                                    photoLink = "",
                                    isLast = true,
                                    groupId = ""
                                )
                            ),
                        ),
                        priceWithAdditions = "300 ₽"
                    )
                ),
                additionUuidList = emptyList(),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ProductDetailsLoadingScreenPreview() {
        FoodDeliveryTheme {
            ProductDetailsScreen(
                menuProductName = "Бэргер куриный Макс с экстра сырным соусом",
                menuProductUuid = "",
                productDetailsUi = ProductDetailsUi.Loading,
                additionUuidList = emptyList(),
                onAction = {}
            )
        }
    }
}
