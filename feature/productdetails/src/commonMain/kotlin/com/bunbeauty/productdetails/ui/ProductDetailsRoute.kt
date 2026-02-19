package com.bunbeauty.productdetails.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.core.Constants.FAB_SNACKBAR_BOTTOM_PADDING
import com.bunbeauty.core.model.ProductDetailsOpenedFrom
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.LocalBottomBarPadding
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAsyncImage
import com.bunbeauty.designsystem.ui.element.FoodDeliveryScaffold
import com.bunbeauty.designsystem.ui.element.TopCartUi
import com.bunbeauty.designsystem.ui.element.button.FoodDeliveryExtendedFab
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCheckbox
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryItem
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryRadioButton
import com.bunbeauty.designsystem.ui.screen.ErrorScreen
import com.bunbeauty.designsystem.ui.screen.LoadingScreen
import com.bunbeauty.productdetails.presentation.AdditionItem
import com.bunbeauty.productdetails.presentation.MenuProductAdditionItem
import com.bunbeauty.productdetails.presentation.ProductDetailsState
import com.bunbeauty.productdetails.presentation.ProductDetailsViewModel
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_product_details_want
import papakarlo.designsystem.generated.resources.common_error
import papakarlo.designsystem.generated.resources.description_product
import papakarlo.designsystem.generated.resources.description_product_addition
import papakarlo.designsystem.generated.resources.error_consumer_cart_add_product
import papakarlo.designsystem.generated.resources.ic_plus_16
import papakarlo.designsystem.generated.resources.msg_menu_product_added
import papakarlo.designsystem.generated.resources.msg_menu_product_edited

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProductDetailsRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: ProductDetailsViewModel = koinViewModel(),
    back: () -> Unit,
    menuProductUuid: String,
    menuProductName: String,
    productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    additionUuidList: List<String>,
    cartProductUuid: String?,
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
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
        showInfoMessage = showInfoMessage,
        showErrorMessage = showErrorMessage,
    )

    ProductDetailsScreen(
        menuProductName = menuProductName,
        menuProductUuid = menuProductUuid,
        additionUuidList = additionUuidList,
        productDetailsViewState = viewState.map(),
        onAction = onAction,
        productDetailsOpenedFrom = productDetailsOpenedFrom,
        cartProductUuid = cartProductUuid,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
    )
}

@Composable
fun ProductDetailsEffect(
    effects: List<ProductDetailsState.Event>,
    back: () -> Unit,
    consumeEffects: () -> Unit,
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                ProductDetailsState.Event.NavigateBack -> back()

                ProductDetailsState.Event.EditedProduct -> {
                    showInfoMessage(
                        getString(Res.string.msg_menu_product_edited),
                        FAB_SNACKBAR_BOTTOM_PADDING,
                    )
                    back()
                }

                ProductDetailsState.Event.ShowAddProductError -> {
                    showErrorMessage(
                        getString(resource = Res.string.error_consumer_cart_add_product),
                    )
                }

                ProductDetailsState.Event.AddedProduct -> {
                    showInfoMessage(
                        getString(
                            Res.string.msg_menu_product_added,
                        ),
                        FAB_SNACKBAR_BOTTOM_PADDING,
                    )
                    back()
                }
            }
        }
        consumeEffects()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ProductDetailsScreen(
    menuProductName: String,
    menuProductUuid: String,
    additionUuidList: List<String>,
    productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    cartProductUuid: String?,
    productDetailsViewState: ProductDetailsViewState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    onAction: (ProductDetailsState.Action) -> Unit,
) {
    FoodDeliveryScaffold(
        modifier = Modifier,
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
                            resource = Res.string.action_product_details_want,
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
                    icon = Res.drawable.ic_plus_16,
                )
            }
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
    ) {
        when (productDetailsViewState) {
            is ProductDetailsViewState.Success -> {
                ProductDetailsSuccessScreen(
                    menuProductUi = productDetailsViewState.menuProductUi,
                    onAction = onAction,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                )
            }

            is ProductDetailsViewState.Loading -> {
                LoadingScreen()
            }

            is ProductDetailsViewState.Error -> {
                ErrorScreen(mainTextId = Res.string.common_error) {
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ProductDetailsSuccessScreen(
    menuProductUi: ProductDetailsViewState.Success.MenuProductUi,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    onAction: (ProductDetailsState.Action) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxWidth(),
        contentPadding = PaddingValues(bottom = LocalBottomBarPadding.current),
    ) {
        item {
            ProductCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                menuProductUi = menuProductUi,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
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
                contentDescription = stringResource(resource = Res.string.description_product_addition),
                contentScale = ContentScale.FillWidth,
                error = null,
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

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationApi::class)
@Composable
private fun ProductCard(
    menuProductUi: ProductDetailsViewState.Success.MenuProductUi,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    with(sharedTransitionScope) {
        Column(
            modifier = modifier,
        ) {
            FoodDeliveryAsyncImage(
                modifier =
                    Modifier
                        .sharedElement(
                            sharedContentState =
                                sharedTransitionScope.rememberSharedContentState(
                                    key = "image-${menuProductUi.uuid}",
                                ),
                            animatedVisibilityScope = animatedContentScope,
                        ).fillMaxWidth()
                        .clip(shape = RoundedCornerShape(size = 16.dp))
                        .heightIn(min = 228.dp),
                photoLink = menuProductUi.photoLink,
                contentDescription = stringResource(Res.string.description_product),
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
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
                                .sharedElement(
                                    sharedContentState =
                                        sharedTransitionScope.rememberSharedContentState(
                                            key = "text-${menuProductUi.uuid}",
                                        ),
                                    animatedVisibilityScope = animatedContentScope,
                                ),
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
                                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
                                    .sharedElement(
                                        sharedContentState =
                                            sharedTransitionScope
                                                .rememberSharedContentState(
                                                    key = "oldPrice-${menuProductUi.uuid}",
                                                ),
                                        animatedVisibilityScope = animatedContentScope,
                                    ),
                            text = menuProductUi.oldPrice,
                            style = FoodDeliveryTheme.typography.bodyLarge,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough,
                        )
                    }
                    Text(
                        modifier =
                            Modifier.sharedElement(
                                sharedContentState =
                                    sharedTransitionScope.rememberSharedContentState(
                                        key = "price-${menuProductUi.uuid}",
                                    ),
                                animatedVisibilityScope = animatedContentScope,
                            ),
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
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun ProductDetailsSuccessScreenPreview() {
    FoodDeliveryTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                ProductDetailsScreen(
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
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
                                    uuid = "uid",
                                ),
                        ),
                    additionUuidList = persistentListOf(),
                    onAction = {},
                    productDetailsOpenedFrom = ProductDetailsOpenedFrom.CART_PRODUCT,
                    cartProductUuid = "",
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun ProductDetailsLoadingScreenPreview() {
    FoodDeliveryTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                ProductDetailsScreen(
                    menuProductName = "Бэргер куриный Макс с экстра сырным соусом",
                    menuProductUuid = "",
                    productDetailsOpenedFrom = ProductDetailsOpenedFrom.CART_PRODUCT,
                    cartProductUuid = "",
                    productDetailsViewState = ProductDetailsViewState.Loading,
                    additionUuidList = persistentListOf(),
                    onAction = {},
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
            }
        }
    }
}
