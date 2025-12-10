package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.element.OverflowingText
import com.bunbeauty.designsystem.ui.icon16
import com.bunbeauty.designsystem.ui.icon24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.description_ic_about
import papakarlo.designsystem.generated.resources.description_ic_next
import papakarlo.designsystem.generated.resources.ic_info
import papakarlo.designsystem.generated.resources.ic_right_arrow

@Composable
fun NavigationIconCard(
    modifier: Modifier = Modifier,
    iconId: DrawableResource,
    iconDescriptionStringId: StringResource? = null,
    labelStringId: StringResource? = null,
    label: String = "",
    elevated: Boolean = true,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier,
        onClick = onClick,
        elevated = elevated,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.icon24(),
                painter = painterResource(iconId),
                tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                contentDescription =
                    iconDescriptionStringId?.let { stringId ->
                        stringResource(stringId)
                    },
            )
            val labelText =
                labelStringId?.let { id ->
                    stringResource(id)
                } ?: label
            OverflowingText(
                modifier =
                    Modifier
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                        .weight(1f),
                text = labelText,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )
            Icon(
                modifier = Modifier.icon16(),
                painter = painterResource(
                    Res.drawable.ic_right_arrow
                ),
                tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                contentDescription = stringResource(Res.string.description_ic_next),
            )
        }
    }
}

@Preview
@Composable
private fun NavigationIconCardPreview() {
    FoodDeliveryTheme {
        NavigationIconCard(
            iconId = Res.drawable.ic_info,
            iconDescriptionStringId = Res.string.description_ic_about,
            label = "Текст",
        ) {}
    }
}
