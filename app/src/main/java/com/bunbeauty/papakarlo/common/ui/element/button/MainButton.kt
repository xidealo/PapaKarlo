package com.bunbeauty.papakarlo.common.ui.element.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int? = null,
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
        MainButton(textStringId = R.string.action_login_continue) {}
    }
}

@Preview
@Composable
private fun MainButtonDisabledPreview() {
    FoodDeliveryTheme {
        MainButton(
            textStringId = R.string.action_login_continue,
            enabled = false,
        ) {}
    }
}
