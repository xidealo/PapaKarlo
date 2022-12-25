package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun RowCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    @DrawableRes startIconId: Int? = null,
    startIconTint: Color = FoodDeliveryTheme.colors.onSurfaceVariant,
    @StringRes labelStringId: Int,
    @DrawableRes endIconId: Int? = null,
    endIconTint: Color = FoodDeliveryTheme.colors.onSurfaceVariant,
    onClick: (() -> Unit) = {}
) {
    RowCard(
        modifier = modifier,
        enabled = enabled,
        elevated = elevated,
        startIconId = startIconId,
        startIconTint = startIconTint,
        label = stringResource(labelStringId),
        endIconId = endIconId,
        endIconTint = endIconTint,
        onClick = onClick,
    )
}

@Composable
fun RowCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    @DrawableRes startIconId: Int? = null,
    startIconTint: Color = FoodDeliveryTheme.colors.onSurfaceVariant,
    label: String,
    @DrawableRes endIconId: Int? = null,
    endIconTint: Color = FoodDeliveryTheme.colors.onSurfaceVariant,
    onClick: (() -> Unit) = {}
) {
    FoodDeliveryCard(
        modifier = modifier,
        enabled = enabled,
        elevated = elevated,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(FoodDeliveryTheme.dimensions.mediumSpace))
            if (startIconId != null) {
                Icon(
                    modifier = Modifier.icon24(),
                    painter = painterResource(startIconId),
                    tint = startIconTint,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(FoodDeliveryTheme.dimensions.mediumSpace))
            }
            OverflowingText(
                modifier = Modifier.weight(1f),
                text = label,
                style = FoodDeliveryTheme.typography.body1,
                color = FoodDeliveryTheme.colors.onSurface
            )
            if (endIconId != null) {
                Spacer(modifier = Modifier.width(FoodDeliveryTheme.dimensions.mediumSpace))
                Icon(
                    modifier = Modifier.icon24(),
                    painter = painterResource(endIconId),
                    tint = endIconTint,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(FoodDeliveryTheme.dimensions.mediumSpace))
        }
    }
}