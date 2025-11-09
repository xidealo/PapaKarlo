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
import papakarlo.shared.generated.resources.action_login_continue

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    textStringId: StringResource? = null,
    text: String? = null,
    elevated: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = FoodDeliveryButtonDefaults.mainButtonColors(enabled = enabled),
        shape = FoodDeliveryButtonDefaults.buttonShape,
        elevation = FoodDeliveryButtonDefaults.getButtonElevation(elevated),
        enabled = enabled,
    ) {
        val buttonText =
            text ?: textStringId?.let {
                stringResource(it)
            } ?: ""
        Text(
            text = buttonText,
            style = FoodDeliveryTheme.typography.labelLarge.medium,
        )
    }
}

@Preview
@Composable
private fun MainButtonPreview() {
    FoodDeliveryTheme {
        MainButton(textStringId = Res.string.action_login_continue) {}
    }
}

@Preview
@Composable
private fun MainButtonDisabledPreview() {
    FoodDeliveryTheme {
        MainButton(
            textStringId = Res.string.action_login_continue,
            enabled = false,
        ) {}
    }
}
