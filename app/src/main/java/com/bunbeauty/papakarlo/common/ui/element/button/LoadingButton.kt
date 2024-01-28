package com.bunbeauty.papakarlo.common.ui.element.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.button.FoodDeliveryButtonDefaults.getButtonElevation
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun LoadingButton(
    @StringRes textStringId: Int,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    hasShadow: Boolean = true,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = FoodDeliveryButtonDefaults.mainButtonColors,
        shape = FoodDeliveryButtonDefaults.buttonShape,
        elevation = getButtonElevation(hasShadow),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = FoodDeliveryTheme.colors.mainColors.onDisabled
            )
        } else {
            Text(
                text = stringResource(textStringId),
                style = FoodDeliveryTheme.typography.labelLarge.medium,
                color = FoodDeliveryTheme.colors.mainColors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingButtonPreview() {
    FoodDeliveryTheme {
        LoadingButton(
            textStringId = R.string.action_create_order_create_order,
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
            textStringId = R.string.action_create_order_create_order,
            isLoading = true,
            onClick = {},
        )
    }
}
