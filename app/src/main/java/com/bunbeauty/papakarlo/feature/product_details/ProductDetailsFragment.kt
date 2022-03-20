package com.bunbeauty.papakarlo.feature.product_details

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar
import com.bunbeauty.papakarlo.compose.element.MainButton
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.databinding.FragmentProductDetailsBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ProductDetailsFragment : BaseFragment(R.layout.fragment_product_details) {

    override val viewModel: ProductDetailsViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })
    override val viewBinding by viewBinding(FragmentProductDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentProductDetailsCvMain.compose {
            val menuProduct by viewModel.menuProduct.collectAsState()
            ProductDetailsScreen(menuProduct)
        }
    }

    @Composable
    private fun ProductDetailsScreen(menuProductUI: MenuProductUI?) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
        ) {
            if (menuProductUI == null) {
                CircularProgressBar(modifier = Modifier.align(Alignment.Center))
            } else {
                ProductDetailsSuccessScreen(menuProductUI)
            }
        }
    }

    @Composable
    private fun ProductDetailsSuccessScreen(menuProductUI: MenuProductUI) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                ProductCard(menuProductUI)
            }
            MainButton(
                textStringId = R.string.action_product_details_want
            ) {
                viewModel.onWantClicked(menuProductUI)
            }
        }
    }

    @Composable
    private fun ProductCard(menuProductUI: MenuProductUI) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace),
            shape = mediumRoundedCornerShape,
            backgroundColor = FoodDeliveryTheme.colors.surface
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(menuProductUI.photoLink)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = stringResource(R.string.description_product),
                    contentScale = ContentScale.FillWidth
                )
                Column(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace)) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = menuProductUI.name,
                            style = FoodDeliveryTheme.typography.h2,
                            color = FoodDeliveryTheme.colors.onSurface
                        )
                        Text(
                            text = menuProductUI.size,
                            style = FoodDeliveryTheme.typography.body2,
                            color = FoodDeliveryTheme.colors.onSurfaceVariant
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
                    ) {
                        menuProductUI.oldPrice?.let {
                            Text(
                                modifier = Modifier
                                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                                text = menuProductUI.oldPrice,
                                style = FoodDeliveryTheme.typography.body1,
                                color = FoodDeliveryTheme.colors.onSurfaceVariant,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        Text(
                            text = menuProductUI.newPrice,
                            style = FoodDeliveryTheme.typography.body1,
                            color = FoodDeliveryTheme.colors.onSurface
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        text = menuProductUI.description,
                        style = FoodDeliveryTheme.typography.body1,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    private fun ProductDetailsSuccessScreenPreview() {
        ProductDetailsScreen(
            MenuProductUI(
                uuid = "",
                photoLink = "",
                name = "Бэргер куриный Макс с экстра сырным соусом",
                size = "300 г",
                oldPrice = "320 ₽",
                newPrice = "280 ₽",
                description = "Сочная котлетка, сыр Чедр, маринованный огурчик, помидор, " +
                        "красный лук, салат, фирменный соус, булочка с кунжутом",
            )
        )
    }

    @Preview
    @Composable
    private fun ProductDetailsLoadingScreenPreview() {
        ProductDetailsScreen(null)
    }
}