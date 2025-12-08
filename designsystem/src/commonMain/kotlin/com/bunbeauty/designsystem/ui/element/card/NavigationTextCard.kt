package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.icon16
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.description_ic_next
import papakarlo.designsystem.generated.resources.hint_settings_phone
import papakarlo.designsystem.generated.resources.ic_right_arrow

@Composable
fun NavigationTextCard(
    modifier: Modifier = Modifier,
    hintStringId: StringResource,
    label: String?,
    clickable: Boolean = true,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier,
        clickable = clickable,
        onClick = onClick,
    ) {
        Row(
            modifier =
                Modifier.padding(
                    horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                    vertical = FoodDeliveryTheme.dimensions.smallSpace,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
            ) {
                Text(
                    text = stringResource(hintStringId),
                    style = FoodDeliveryTheme.typography.labelSmall.medium,
                    color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                )
                Text(
                    text = label ?: "",
                    style = FoodDeliveryTheme.typography.bodyMedium,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )
            }
            Icon(
                modifier = Modifier.icon16(),
                painter = painterResource(Res.drawable.ic_right_arrow),
                tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                contentDescription = stringResource(Res.string.description_ic_next),
            )
        }
    }
}

@Preview
@Composable
private fun TextNavigationCardPreview() {
    FoodDeliveryTheme {
        NavigationTextCard(
            modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
            hintStringId = Res.string.hint_settings_phone,
            label = "+7 999 000-00-00",
            onClick = {},
        )
    }
}
