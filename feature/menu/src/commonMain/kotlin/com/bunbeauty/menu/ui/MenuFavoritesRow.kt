package com.bunbeauty.menu.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.core.model.ProductUi
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.FoodDeliveryProductItem
import com.bunbeauty.menu.presentation.MenuState
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.title_favorites

private val FavoriteProductCardWidth = 180.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MenuFavoritesRow(
    products: ImmutableList<ProductUi>,
    animatedContentScope: AnimatedVisibilityScope,
    onAction: (MenuState.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            modifier =
                Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            text = stringResource(resource = Res.string.title_favorites),
            style = FoodDeliveryTheme.typography.titleMedium.bold,
            color = FoodDeliveryTheme.colors.mainColors.onSurface,
        )
        LazyRow(
            modifier =
                Modifier
                    .padding(top = 12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = products,
                key = { product -> product.key },
            ) { product ->
                FoodDeliveryProductItem(
                    modifier = Modifier.width(FavoriteProductCardWidth),
                    animatedContentScope = animatedContentScope,
                    onAddProductClick = { menuProductUuid ->
                        onAction(MenuState.Action.OnAddProductClicked(menuProductUuid))
                    },
                    onProductClick = { menuProductUuid ->
                        onAction(MenuState.Action.OnMenuItemClicked(menuProductUuid))
                    },
                    uuid = product.uuid,
                    photoLink = product.photoLink,
                    name = product.name,
                    oldPrice = product.oldPrice,
                    newPrice = product.newPrice,
                )
            }
        }
    }
}
