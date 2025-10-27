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
fun SecondaryButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
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
            text = stringResource(id = textStringId),
            style = FoodDeliveryTheme.typography.labelLarge.medium,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SecondaryButtonPreview() {
    FoodDeliveryTheme {
        SecondaryButton(
            textStringId = R.string.action_logout,
            onClick = {},
        )
    }
}
