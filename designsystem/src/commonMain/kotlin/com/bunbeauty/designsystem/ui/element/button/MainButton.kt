package com.bunbeauty.designsystem.ui.element.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.LocalBottomBarPadding
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.preview_string

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
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = LocalBottomBarPadding.current),
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
        MainButton(
            textStringId = Res.string.preview_string
        ) {}
    }
}

@Preview
@Composable
private fun MainButtonDisabledPreview() {
    FoodDeliveryTheme {
        MainButton(
            textStringId = Res.string.preview_string,
            enabled = false,
        ) {}
    }
}
