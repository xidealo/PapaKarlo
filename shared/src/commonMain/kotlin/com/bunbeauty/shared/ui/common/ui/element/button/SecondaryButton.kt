package com.bunbeauty.shared.ui.common.ui.element.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.theme.medium
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.action_logout

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    textStringId: StringResource,
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
    ) {
        Text(
            text = stringResource(resource = textStringId),
            style = FoodDeliveryTheme.typography.labelLarge.medium,
        )
    }
}

@Preview
@Composable
private fun SecondaryButtonPreview() {
    FoodDeliveryTheme {
        SecondaryButton(
            textStringId = Res.string.action_logout,
            onClick = {},
        )
    }
}
