package com.bunbeauty.papakarlo.feature.productdetails

import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryAsyncImage
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.FoodDeliveryExtendedFab
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCheckbox
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryItem
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryRadioButton
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.Constants.FAB_SNACKBAR_BOTTOM_PADDING
import com.bunbeauty.shared.presentation.product_details.AdditionItem
import com.bunbeauty.shared.presentation.product_details.MenuProductAdditionItem
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import com.bunbeauty.shared.presentation.product_details.ProductDetailsState
import com.bunbeauty.shared.presentation.product_details.ProductDetailsViewModel
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

private const val ANIMATION_LABEL = "ProductDetailsFragment"
private const val ANIMATION_DURATION_MILLIS = 200

@Composable
fun ProductDetailsRoute(
    viewModel: ProductDetailsViewModel = koinViewModel(),
    back: () -> Unit,
    menuProductUuid: String,
    menuProductName: String,
    productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    additionUuidList: List<String>,
    cartProductUuid: String?,
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(
            ProductDetailsState.Action.Init(
                menuProductUuid = menuProductUuid,
                selectedAdditionUuidList = additionUuidList,
            ),
        )
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction =
        remember {
            { action: ProductDetailsState.Action ->
                viewModel.onAction(action)
            }
        }

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects =
        remember {
            {
                viewModel.consumeEvents(effects)
            }
        }

    ProductDetailsEffect(
        effects = effects,
        back = back,
        consumeEffects = consumeEffects,
    )

    ProductDetailsScreen(
        menuProductName = menuProductName,
        menuProductUuid = menuProductUuid,
        additionUuidList = additionUuidList,
        productDetailsViewState = viewState.map(),
        onAction = onAction,
        productDetailsOpenedFrom = productDetailsOpenedFrom,
        cartProductUuid = cartProductUuid,
    )
}

@Composable
fun ProductDetailsEffect(
    effects: List<ProductDetailsState.Event>,
    back: () -> Unit,
    consumeEffects: () -> Unit,
) {
    val activity = LocalActivity.current
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                ProductDetailsState.Event.NavigateBack -> back()

                ProductDetailsState.Event.EditedProduct -> {
                    (activity as? IMessageHost)?.showInfoMessage(
                        activity.resources.getString(
                            R.string.msg_menu_product_edited,
                        ),
                    )
                    back()
                }

                ProductDetailsState.Event.ShowAddProductError -> {
                    (activity as? IMessageHost)?.showErrorMessage(
                        activity.resources.getString(R.string.error_consumer_cart_add_product),
                    )
                }

                ProductDetailsState.Event.AddedProduct -> {
                    (activity as? IMessageHost)?.showInfoMessage(
                        text =
                            activity.resources.getString(
                                R.string.msg_menu_product_added,
                            ),
                        paddingBottom = FAB_SNACKBAR_BOTTOM_PADDING,
                    )
                    back()
                }
            }
        }
        consumeEffects()
    }
}

@SuppressLint("RestrictedApi")
@Composable
private fun ProductDetailsScreen(
    menuProductName: String,
    menuProductUuid: String,
    additionUuidList: List<String>,
    productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    cartProductUuid: String?,
    productDetailsViewState: ProductDetailsViewState,
    onAction: (ProductDetailsState.Action) -> Unit,
) {
    FoodDeliveryScaffold(
        title = menuProductName,
        backActionClick = {
            onAction(ProductDetailsState.Action.BackClick)
        },
        actionButton = {
            if (productDetailsViewState is ProductDetailsViewState.Success) {
                FoodDeliveryExtendedFab(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                    text =
                        stringResource(
                            id = R.string.action_product_details_want,
                            productDetailsViewState.menuProductUi.priceWithAdditions,
                        ),
                    onClick = {
                        onAction(
                            ProductDetailsState.Action.AddProductToCartClick(
                                productDetailsOpenedFrom = productDetailsOpenedFrom,
                                cartProductUuid = cartProductUuid,
                            ),
                        )
                    },
                    icon = R.drawable.ic_plus_16,
                )
            }
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
    ) {
        AnimatedContent(
            targetState = productDetailsViewState,
            label = ANIMATION_LABEL,
            contentKey = { state ->
                state::class.java
            },
            transitionSpec = {
                ContentTransform(
                    targetContentEnter =
                        fadeIn(
                            animationSpec = tween(delayMillis = ANIMATION_DURATION_MILLIS),
                        ),
                    initialContentExit =
                        fadeOut(
                            animationSpec = tween(delayMillis = ANIMATION_DURATION_MILLIS),
                        ),
                )
            },
        ) { productDetailsViewState ->
            when (productDetailsViewState) {
                is ProductDetailsViewState.Success -> {
                    ProductDetailsSuccessScreen(
                        productDetailsViewState.menuProductUi,
                        onAction = onAction,
                    )
                }

                is ProductDetailsViewState.Loading -> {
                    LoadingScreen()
                }

                is ProductDetailsViewState.Error -> {
                    ErrorScreen(mainTextId = R.string.common_error) {
                        onAction(
                            ProductDetailsState.Action.Init(
                                menuProductUuid = menuProductUuid,
                                selectedAdditionUuidList = additionUuidList,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductDetailsSuccessScreen(
    menuProductUi: ProductDetailsViewState.Success.MenuProductUi,
    onAction: (ProductDetailsState.Action) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        item {
            ProductCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                menuProductUi = menuProductUi,
            )
        }
        items(
            menuProductUi.additionList,
            key = { menuProductAdditionItem ->
                menuProductAdditionItem.key
            },
        ) { menuProductAdditionItem ->
            when (menuProductAdditionItem) {
                is AdditionItem.AdditionHeaderItem -> {
                    Text(
                        modifier =
                            Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 24.dp),
                        text = menuProductAdditionItem.name,
                        style = FoodDeliveryTheme.typography.titleMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    )
                }

                is AdditionItem.AdditionListItem -> {
                    FoodDeliveryItem(needDivider = !menuProductAdditionItem.product.isLast) {
                        AdditionItem(
                            menuProductAdditionItem = menuProductAdditionItem.product,
                            isMultiple = menuProductAdditionItem.isMultiple,
                            onAction = onAction,
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

@Composable
private fun AdditionItem(
    menuProductAdditionItem: MenuProductAdditionItem,
    isMultiple: Boolean,
    onAction: (ProductDetailsState.Action) -> Unit,
) {
    FoodDeliveryCard(
        onClick = {
            onAction(
                ProductDetailsState.Action.AdditionClick(
                    uuid = menuProductAdditionItem.uuid,
                    groupUuid = menuProductAdditionItem.groupId,
                ),
            )
        },
        elevated = false,
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FoodDeliveryAsyncImage(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(FoodDeliveryCardDefaults.cardShape),
                photoLink = menuProductAdditionItem.photoLink,
                contentDescription = stringResource(R.string.description_product_addition),
                contentScale = ContentScale.FillWidth,
                error = null,
                placeholder = null,
            )

            Text(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                text = menuProductAdditionItem.name,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )

            menuProductAdditionItem.price?.let { price ->
                Text(
                    modifier =
                        Modifier
                            .padding(end = 8.dp),
                    text = price,
                    style = FoodDeliveryTheme.typography.bodyLarge,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )
            }

            if (isMultiple) {
                FoodDeliveryCheckbox(
                    checked = menuProductAdditionItem.isSelected,
                    onCheckedChange = {
                        onAction(
                            ProductDetailsState.Action.AdditionClick(
                                uuid = menuProductAdditionItem.uuid,
                                groupUuid = menuProductAdditionItem.groupId,
                            ),
                        )
                    },
                )
            } else {
                FoodDeliveryRadioButton(
                    selected = menuProductAdditionItem.isSelected,
                    onClick = {
                        onAction(
                            ProductDetailsState.Action.AdditionClick(
                                uuid = menuProductAdditionItem.uuid,
                                groupUuid = menuProductAdditionItem.groupId,
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun ProductCard(
    modifier: Modifier = Modifier,
    menuProductUi: ProductDetailsViewState.Success.MenuProductUi,
) {
    Column(
        modifier = modifier,
    ) {
        FoodDeliveryAsyncImage(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(FoodDeliveryCardDefaults.cardShape),
            photoLink = menuProductUi.photoLink,
            placeholder = painterResource(R.drawable.placeholder_large),
            contentDescription = stringResource(R.string.description_product),
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier =
                Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
        ) {
            Row {
                Text(
                    modifier =
                        Modifier
                            .weight(1f)
                            .alignByBaseline()
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                    text = menuProductUi.name,
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = menuProductUi.size,
                    style = FoodDeliveryTheme.typography.bodySmall,
                    color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                )
            }
            Row(
                modifier =
                    Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
            ) {
                menuProductUi.oldPrice?.let {
                    Text(
                        modifier =
                            Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                        text = menuProductUi.oldPrice,
                        style = FoodDeliveryTheme.typography.bodyLarge,
                        color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough,
                    )
                }
                Text(
                    text = menuProductUi.newPrice,
                    style = FoodDeliveryTheme.typography.bodyLarge.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )
            }
            Text(
                modifier =
                    Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                text = menuProductUi.description,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )
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
            productDetailsViewState =
                ProductDetailsViewState.Success(
                    topCartUi =
                        TopCartUi(
                            cost = "100",
                            count = "2",
                        ),
                    menuProductUi =
                        ProductDetailsViewState.Success.MenuProductUi(
                            photoLink = "",
                            name = "Бэргер куриный Макс с экстра сырным соусом",
                            size = "300 г",
                            oldPrice = "320 ₽",
                            newPrice = "280 ₽",
                            description =
                                "Сочная котлетка, сыр Чедр, маринованный огурчик, помидор, " +
                                    "красный лук, салат, фирменный соус, булочка с кунжутом",
                            additionList =
                                persistentListOf(
                                    AdditionItem.AdditionHeaderItem(
                                        key = "key1",
                                        uuid = "uuid1",
                                        name = "Булочка",
                                    ),
                                    AdditionItem.AdditionListItem(
                                        key = "key2",
                                        product =
                                            MenuProductAdditionItem(
                                                uuid = "uuid2",
                                                isSelected = true,
                                                name = "БулОЧКА Валентина",
                                                price = "+200",
                                                photoLink = "",
                                                isLast = false,
                                                groupId = "",
                                            ),
                                        isMultiple = false,
                                    ),
                                    AdditionItem.AdditionListItem(
                                        key = "key3",
                                        product =
                                            MenuProductAdditionItem(
                                                uuid = "uuid3",
                                                isSelected = false,
                                                name = "БулОЧКА Марка",
                                                price = "300",
                                                photoLink = "",
                                                isLast = true,
                                                groupId = "",
                                            ),
                                        isMultiple = false,
                                    ),
                                    AdditionItem.AdditionHeaderItem(
                                        key = "key4",
                                        uuid = "uuid4",
                                        name = "Добавить по вкусу",
                                    ),
                                    AdditionItem.AdditionListItem(
                                        key = "key5",
                                        product =
                                            MenuProductAdditionItem(
                                                uuid = "uuid5",
                                                isSelected = true,
                                                name = "Монкейэс",
                                                price = "13",
                                                photoLink = "",
                                                isLast = false,
                                                groupId = "",
                                            ),
                                        isMultiple = true,
                                    ),
                                    AdditionItem.AdditionListItem(
                                        key = "key6",
                                        product =
                                            MenuProductAdditionItem(
                                                uuid = "uuid6",
                                                isSelected = false,
                                                name = "Лида в лаваше",
                                                price = "2",
                                                photoLink = "",
                                                isLast = true,
                                                groupId = "",
                                            ),
                                        isMultiple = true,
                                    ),
                                ),
                            priceWithAdditions = "300 ₽",
                        ),
                ),
            additionUuidList = persistentListOf(),
            onAction = {},
            productDetailsOpenedFrom = ProductDetailsOpenedFrom.CART_PRODUCT,
            cartProductUuid = "",
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
            productDetailsOpenedFrom = ProductDetailsOpenedFrom.CART_PRODUCT,
            cartProductUuid = "",
            productDetailsViewState = ProductDetailsViewState.Loading,
            additionUuidList = persistentListOf(),
            onAction = {},
        )
    }
}
