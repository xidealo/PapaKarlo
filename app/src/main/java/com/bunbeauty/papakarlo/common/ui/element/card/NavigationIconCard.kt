package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationIconCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes iconDescription: Int,
    @StringRes labelStringId: Int? = null,
    label: String = "",
    hasShadow: Boolean = true,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.cardHeight),
        shape = mediumRoundedCornerShape,
        colors = FoodDeliveryTheme.colors.cardColors(),
        elevation = FoodDeliveryTheme.dimensions.cardEvaluation(hasShadow),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.icon24(),
                imageVector = ImageVector.vectorResource(iconId),
                contentDescription = stringResource(iconDescription),
                tint = FoodDeliveryTheme.colors.onSurfaceVariant
            )
            val labelText = labelStringId?.let { id ->
                stringResource(id)
            } ?: label
            OverflowingText(
                modifier = Modifier
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                    .weight(1f),
                text = labelText,
                style = FoodDeliveryTheme.typography.body1,
                color = FoodDeliveryTheme.colors.onSurface
            )
            Icon(
                modifier = Modifier.icon16(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow),
                contentDescription = stringResource(R.string.description_ic_next),
                tint = FoodDeliveryTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun NavigationIconCardPreview() {
    NavigationIconCard(
        iconId = R.drawable.ic_info,
        iconDescription = R.string.description_ic_about,
        label = "Текст"
    ) {}
}
