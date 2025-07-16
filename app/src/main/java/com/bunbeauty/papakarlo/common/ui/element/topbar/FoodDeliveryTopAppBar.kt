package com.bunbeauty.papakarlo.common.ui.element.topbar

import android.app.Activity
import android.view.Window
import android.view.WindowManager
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.common.ui.theme.medium
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FoodDeliveryTopAppBar(
    title: String?,
    backActionClick: (() -> Unit)? = null,
    actions: ImmutableList<FoodDeliveryToolbarActions> = persistentListOf(),
    isScrolled: Boolean = false,
    @DrawableRes drawableId: Int? = null,
    content: @Composable () -> Unit = {}
) {
    val barColor by animateColorAsState(
        targetValue = if (isScrolled) {
            FoodDeliveryTheme.colors.mainColors.surfaceVariant
        } else {
            FoodDeliveryTheme.colors.mainColors.surface
        },
        animationSpec = tween(500),
        label = "barColor"
    )

    val window = (LocalView.current.context as? Activity)?.window
    LaunchedEffect(barColor) {
        val isLight = barColor.luminance() > 0.5f
        window?.setBarColor(barColor.toArgb(), isLight)
    }

    Column(modifier = Modifier.background(barColor)) {
        Box {
            FoodDeliveryTopAppBar(
                title = title,
                backActionClick = backActionClick,
                actions = actions
            )
            LogoImage(
                modifier = Modifier.align(Alignment.Center),
                drawableId = drawableId
            )
        }

        content()

        if (isScrolled) {
            FoodDeliveryHorizontalDivider(color = FoodDeliveryTheme.colors.mainColors.strokeVariant)
        }
    }
}


fun Window.setBarColor(color: Int, isLightIcons: Boolean) {
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    navigationBarColor = color

    val insetsController = WindowInsetsControllerCompat(this, decorView)
    insetsController.isAppearanceLightStatusBars = isLightIcons
    insetsController.isAppearanceLightNavigationBars = isLightIcons
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoodDeliveryTopAppBar(
    title: String?,
    backActionClick: (() -> Unit)? = null,
    actions: ImmutableList<FoodDeliveryToolbarActions> = persistentListOf()
) {
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
                    onClick = backActionClick
                ) {
                    Icon(
                        modifier = Modifier.icon16(),
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        tint = FoodDeliveryTheme.colors.mainColors.onSurface,
                        contentDescription = null
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
        }
    )
}

@Composable
private fun Action(action: FoodDeliveryAction) {
    IconButton(
        onClick = action.onClick
    ) {
        Icon(
            modifier = Modifier.icon24(),
            painter = painterResource(id = action.iconId),
            tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
            contentDescription = null
        )
    }
}

@Composable
private fun CardAction(action: FoodDeliveryCartAction) {
    FoodDeliveryCard(
        elevated = false,
        colors = FoodDeliveryCardDefaults.transparentCardColors,
        onClick = action.onClick
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
                text = action.topCartUi.cost
            )
            Box {
                Icon(
                    modifier = Modifier
                        .padding(4.dp)
                        .icon24(),
                    painter = painterResource(id = R.drawable.ic_cart_24),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = null
                )
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd),
                    containerColor = FoodDeliveryTheme.colors.mainColors.primary,
                    contentColor = FoodDeliveryTheme.colors.mainColors.onPrimary
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

@Composable
private fun LogoImage(
    @DrawableRes drawableId: Int?,
    modifier: Modifier = Modifier
) {
    drawableId?.let {
        Image(
            modifier = modifier
                .height(40.dp),
            painter = painterResource(drawableId),
            contentDescription = stringResource(R.string.description_company_logo)
        )
    }
}
