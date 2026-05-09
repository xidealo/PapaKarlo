package com.bunbeauty.designsystem.ui.element.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    elevated: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = FoodDeliveryButtonDefaults.secondaryButtonColors,
        shape = FoodDeliveryButtonDefaults.buttonShape,
        elevation = FoodDeliveryButtonDefaults.getButtonElevation(elevated),
        enabled = enabled,
        border = BorderStroke(
            width = 2.dp,
            color = FoodDeliveryTheme.colors.mainColors.onSecondary,
        ),
    ) {
        Text(
            text = text,
            style = FoodDeliveryTheme.typography.labelLarge.medium,
        )
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    textStringId: StringResource,
    elevated: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    SecondaryButton(
        modifier = modifier,
        text = stringResource(textStringId),
        elevated = elevated,
        enabled = enabled,
        onClick = onClick,
    )
}

@Preview
@Composable
private fun SecondaryButtonPreview() {
    FoodDeliveryTheme {
        SecondaryButton(
            text = "Preview button",
            onClick = {},
        )
    }
}
