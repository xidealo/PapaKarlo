package com.bunbeauty.shared.ui.common.ui.element.topbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.shared.ui.common.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.shared.ui.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.shared.ui.common.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.shared.ui.common.ui.icon16
import com.bunbeauty.shared.ui.common.ui.icon24
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.theme.bold
import com.bunbeauty.shared.ui.theme.medium
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.DrawableResource
import papakarlo.shared.generated.resources.description_company_logo
import papakarlo.shared.generated.resources.ic_arrow_back
import papakarlo.shared.generated.resources.ic_cart_24

@Composable
fun FoodDeliveryTopAppBar(
    title: String?,
    backActionClick: (() -> Unit)? = null,
    actions: ImmutableList<FoodDeliveryToolbarActions> = persistentListOf(),
    isScrolled: Boolean = false,
    drawableId: DrawableResource? = null,
    content: @Composable () -> Unit = {},
) {
    val barColor by animateColorAsState(
        targetValue =
            if (isScrolled) {
                FoodDeliveryTheme.colors.mainColors.surfaceVariant
            } else {
                FoodDeliveryTheme.colors.mainColors.surface
            },
        animationSpec = tween(500),
        label = "barColor",
    )
    // TODO SET BAR COLOR
    LaunchedEffect(barColor) {
        // (localActivity as? MainActivity)?.setStatusBarColor(barColor)
    }

    Column(modifier = Modifier.background(barColor)) {
        Box {
            FoodDeliveryTopAppBar(
                title = title,
                backActionClick = backActionClick,
                actions = actions,
            )
            LogoImage(
                modifier = Modifier.align(Alignment.Center),
                drawableId = drawableId,
            )
        }

        content()

        if (isScrolled) {
            FoodDeliveryHorizontalDivider(color = FoodDeliveryTheme.colors.mainColors.strokeVariant)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoodDeliveryTopAppBar(
    title: String?,
    backActionClick: (() -> Unit)? = null,
    actions: ImmutableList<FoodDeliveryToolbarActions> = persistentListOf(),
) {
    TopAppBar(
        colors = FoodDeliveryTopAppBarDefaults.topAppBarColors(),
        title = {
            Text(
                text = title.orEmpty(),
                maxLines = 1,
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            backActionClick?.let {
                IconButton(
                    onClick = backActionClick,
                ) {
                    Icon(
                        modifier = Modifier.icon16(),
                        painter = painterResource(
                            resource = Res.drawable.ic_arrow_back
                        ),
                        tint = FoodDeliveryTheme.colors.mainColors.onSurface,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = {
            actions.forEach { action ->
                when (action) {
                    is FoodDeliveryAction -> {
                        Action(action = action)
                    }

                    is FoodDeliveryCartAction -> {
                        CardAction(action = action)
                    }
                }
            }
        },
    )
}

@Composable
private fun Action(action: FoodDeliveryAction) {
    IconButton(
        onClick = action.onClick,
    ) {
        Icon(
            modifier = Modifier.icon24(),
            painter = painterResource(resource = action.iconId),
            tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
            contentDescription = null,
        )
    }
}

@Composable
private fun CardAction(action: FoodDeliveryCartAction) {
    FoodDeliveryCard(
        elevated = false,
        colors = FoodDeliveryCardDefaults.transparentCardColors,
        onClick = action.onClick,
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
                text = action.topCartUi.cost,
            )
            Box {
                Icon(
                    modifier =
                        Modifier
                            .padding(4.dp)
                            .icon24(),
                    painter = painterResource(
                        resource = Res.drawable.ic_cart_24
                    ),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = null,
                )
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd),
                    containerColor = FoodDeliveryTheme.colors.mainColors.primary,
                    contentColor = FoodDeliveryTheme.colors.mainColors.onPrimary,
                ) {
                    Text(
                        style = FoodDeliveryTheme.typography.labelSmall.medium,
                        text = action.topCartUi.count,
                    )
                }
            }
        }
    }
}

@Composable
private fun LogoImage(
    drawableId: DrawableResource?,
    modifier: Modifier = Modifier,
) {
    drawableId?.let {
        Image(
            modifier =
                modifier
                    .height(40.dp),
            painter = painterResource(drawableId),
            contentDescription = stringResource(Res.string.description_company_logo),
        )
    }
}
