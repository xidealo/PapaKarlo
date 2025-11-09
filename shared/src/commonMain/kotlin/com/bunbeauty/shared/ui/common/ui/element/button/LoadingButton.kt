package com.bunbeauty.shared.ui.common.ui.element.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunbeauty.shared.ui.common.ui.element.button.FoodDeliveryButtonDefaults.getButtonElevation
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.theme.medium
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.action_create_order_create_order

@Composable
fun LoadingButton(
    textStringId: StringResource,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    hasShadow: Boolean = true,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors =
            FoodDeliveryButtonDefaults.mainButtonColors(
                enabled = isEnabled && !isLoading,
            ),
        shape = FoodDeliveryButtonDefaults.buttonShape,
        elevation = getButtonElevation(hasShadow),
        enabled = isEnabled && !isLoading,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = FoodDeliveryTheme.colors.mainColors.onDisabled,
            )
        } else {
            Text(
                text = stringResource(textStringId),
                style = FoodDeliveryTheme.typography.labelLarge.medium,
                color =
                    if (isEnabled) {
                        FoodDeliveryTheme.colors.mainColors.onPrimary
                    } else {
                        FoodDeliveryTheme.colors.mainColors.onDisabled
                    },
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingButtonPreview() {
    FoodDeliveryTheme {
        LoadingButton(
            textStringId = Res.string.action_create_order_create_order,
            isLoading = false,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingButtonLoadingPreview() {
    FoodDeliveryTheme {
        LoadingButton(
            textStringId = Res.string.action_create_order_create_order,
            isLoading = true,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingButtonDisabledPreview() {
    FoodDeliveryTheme {
        LoadingButton(
            textStringId = Res.string.action_create_order_create_order,
            isLoading = false,
            isEnabled = false,
            onClick = {},
        )
    }
}
