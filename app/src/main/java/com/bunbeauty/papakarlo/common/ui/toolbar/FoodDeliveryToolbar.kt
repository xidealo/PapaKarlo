package com.bunbeauty.papakarlo.common.ui.toolbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.common.ui.theme.medium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryToolbar(
    title: String?,
    backActionClick: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior,
    @DrawableRes drawableId: Int? = null,
    actions: List<FoodDeliveryToolbarActions> = emptyList(),
) {
    Box {
        TopAppBar(
            colors = FoodDeliveryTopAppBarDefaults.topAppBarColors(),
            title = {
                Text(
                    text = title ?: "",
                    maxLines = 1,
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                backActionClick?.let {
                    IconButton(
                        onClick = { backActionClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                }
            },
            actions = {
                actions.forEach { action ->
                    when (action) {
                        is FoodDeliveryAction -> {
                            Action(action)
                        }
                        is FoodDeliveryCartAction -> {
                            CardAction(action)
                        }
                    }
                }
            },
            scrollBehavior = scrollBehavior
        )
        drawableId?.let {
            Image(
                modifier = Modifier
                    .height(40.dp)
                    .align(Alignment.Center),
                painter = painterResource(drawableId),
                contentDescription = stringResource(R.string.description_company_logo)
            )
        }
    }
}

@Composable
private fun Action(action: FoodDeliveryAction) {
    IconButton(
        onClick = action.onClick
    ) {
        Icon(
            painter = painterResource(id = action.iconId),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardAction(action: FoodDeliveryCartAction) {
    FoodDeliveryCard(
        elevated = false,
        onClick = action.onClick,
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.onSurface,
                text = action.topCartUi.cost
            )
            Box {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    painter = painterResource(id = R.drawable.ic_cart_24),
                    tint = FoodDeliveryTheme.colors.onSurfaceVariant,
                    contentDescription = null
                )
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd),
                    containerColor = FoodDeliveryTheme.colors.primary,
                    contentColor = FoodDeliveryTheme.colors.onPrimary,
                ) {
                    Text(
                        style = FoodDeliveryTheme.typography.labelSmall.medium,
                        text = action.topCartUi.count
                    )
                }
            }
        }
    }
}
