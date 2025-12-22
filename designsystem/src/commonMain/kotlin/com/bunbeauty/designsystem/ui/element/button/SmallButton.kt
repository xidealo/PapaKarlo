package com.bunbeauty.designsystem.ui.element.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.preview_string

@Composable
fun SmallButton(
    modifier: Modifier = Modifier,
    textStringId: StringResource,
    elevated: Boolean = true,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = FoodDeliveryButtonDefaults.mainOutlineButtonColors,
        border =
            BorderStroke(
                width = 2.dp,
                color = FoodDeliveryTheme.colors.mainColors.primary,
            ),
        shape = FoodDeliveryButtonDefaults.buttonShape,
        elevation = FoodDeliveryButtonDefaults.getButtonElevation(elevated),
        enabled = isEnabled,
    ) {
        Text(
            text = stringResource(textStringId),
            style = FoodDeliveryTheme.typography.labelLarge.medium,
        )
    }
}

@Preview
@Composable
private fun SmallButtonPreview() {
    FoodDeliveryTheme {
        SmallButton(
            modifier = Modifier.wrapContentSize(),
            textStringId = Res.string.preview_string,
            onClick = { },
        )
    }
}
