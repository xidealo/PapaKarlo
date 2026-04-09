package com.bunbeauty.designsystem.ui.topbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.LocalStatusBarColor
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAction
import com.bunbeauty.designsystem.ui.element.FoodDeliveryCartAction
import com.bunbeauty.designsystem.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.designsystem.ui.element.FoodDeliveryToolbarActions
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.designsystem.ui.icon16
import com.bunbeauty.designsystem.ui.icon20
import com.bunbeauty.designsystem.ui.icon24
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.painterResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.ic_arrow_back
import papakarlo.designsystem.generated.resources.ic_cart_24

@Composable
fun FoodDeliveryTopAppBar(
    title: String?,
    backActionClick: (() -> Unit)? = null,
    actions: ImmutableList<FoodDeliveryToolbarActions> = persistentListOf(),
    isScrolled: Boolean = false,
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

    val statusBarColorState = LocalStatusBarColor.current

    LaunchedEffect(barColor) {
        statusBarColorState.value = barColor
    }

    if (backActionClick != null || title != null) {
        Column(modifier = Modifier.background(barColor)) {
            FoodDeliveryTopAppBarContent(
                title = title,
                backActionClick = backActionClick,
                actions = actions,
            )

            if (isScrolled) {
                FoodDeliveryHorizontalDivider(
                    color = FoodDeliveryTheme.colors.mainColors.strokeVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoodDeliveryTopAppBarContent(
    title: String?,
    modifier: Modifier = Modifier,
    backActionClick: (() -> Unit)? = null,
    actions: ImmutableList<FoodDeliveryToolbarActions> = persistentListOf(),
) {
    TopAppBar(
        modifier = modifier,
        colors = FoodDeliveryTopAppBarDefaults.topAppBarColors(),
        title = {
            Text(
                text = title.orEmpty(),
                maxLines = 1,
                style = FoodDeliveryTheme.typography.titleMedium.medium,
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
                        Action(
                            action = action
                        )
                    }

                    is FoodDeliveryCartAction -> {
                        CardAction(
                            action = action
                        )
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
            modifier = Modifier.icon20(),
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
                            .icon20(),
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
