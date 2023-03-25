package com.bunbeauty.papakarlo.feature.product_details

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.toolbar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.element.toolbar.FoodDeliveryToolbarScreen
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.FragmentComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsFragmentDirections.globalConsumerCartFragment
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.shared.presentation.product_details.ProductDetailsState
import com.bunbeauty.shared.presentation.product_details.ProductDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsFragment : BaseFragmentWithSharedViewModel(R.layout.fragment_compose) {

    private val viewModel: ProductDetailsViewModel by viewModel()

    private val args: ProductDetailsFragmentArgs by navArgs()

    override val viewBinding by viewBinding(FragmentComposeBinding::bind)

    private val productDetailsUiStateMapper: ProductDetailsUiStateMapper by inject()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMenuProduct(args.menuProductUuid)

        viewBinding.root.setContentWithTheme {
            val menuProductUiState by viewModel.menuProductDetailsState.collectAsStateWithLifecycle()
            ProductDetailsScreen(
                menuProductName = args.menuProductName,
                menuProductUuid = args.menuProductUuid,
                productDetailsUi = productDetailsUiStateMapper.map(menuProductUiState),
                state = menuProductUiState.state
            )
        }
    }

    @Composable
    private fun ProductDetailsScreen(
        menuProductName: String,
        menuProductUuid: String,
        productDetailsUi: ProductDetailsUi,
        state: ProductDetailsState.State,
    ) {
        FoodDeliveryToolbarScreen(
            title = menuProductName,
            backActionClick = {
                findNavController().popBackStack()
            },
            topActions = listOf(
                FoodDeliveryCartAction(topCartUi = productDetailsUi.topCartUi) {
                    val backQueue = findNavController().backQueue
                    if ((backQueue.size > 1) &&
                        (backQueue[backQueue.lastIndex - 1].destination.id == R.id.consumerCartFragment)
                    ) {
                        findNavController().popBackStack()
                    } else {
                        findNavController().navigate(globalConsumerCartFragment())
                    }
                }
            ),
            actionButton = {
                if (state == ProductDetailsState.State.SUCCESS) {
                    MainButton(
                        modifier = Modifier.padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        textStringId = R.string.action_product_details_want
                    ) {
                        viewModel.onWantClicked()
                    }
                }
            }
        ) {
            when (state) {
                ProductDetailsState.State.SUCCESS -> {
                    ProductDetailsSuccessScreen(productDetailsUi.menuProductUi)
                }
                ProductDetailsState.State.LOADING -> {
                    LoadingScreen()
                }
                ProductDetailsState.State.ERROR -> {
                    ErrorScreen(mainTextId = R.string.common_error) {
                        viewModel.getMenuProduct(menuProductUuid)
                    }
                }
            }
        }
    }

    @Composable
    private fun ProductDetailsSuccessScreen(menuProductUi: ProductDetailsUi.MenuProductUi?) {
        menuProductUi?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                ProductCard(menuProductUi)
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }

    @Composable
    private fun ProductCard(menuProductUi: ProductDetailsUi.MenuProductUi) {
        FoodDeliveryCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(menuProductUi.photoLink)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder_large),
                    contentDescription = stringResource(R.string.description_product),
                    contentScale = ContentScale.FillWidth
                )
                Column(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace)) {
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
                productDetailsUi = ProductDetailsUi(
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2",
                    ),
                    menuProductUi = ProductDetailsUi.MenuProductUi(
                        photoLink = "",
                        name = "Бэргер куриный Макс с экстра сырным соусом",
                        size = "300 г",
                        oldPrice = "320 ₽",
                        newPrice = "280 ₽",
                        description = "Сочная котлетка, сыр Чедр, маринованный огурчик, помидор, " +
                            "красный лук, салат, фирменный соус, булочка с кунжутом",
                    )
                ),
                state = ProductDetailsState.State.SUCCESS
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
                productDetailsUi = ProductDetailsUi(
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2",
                    ),
                    menuProductUi = null,
                ),
                state = ProductDetailsState.State.LOADING
            )
        }
    }
}
