package com.bunbeauty.papakarlo.common.ui.element.button

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.buttonRoundedCornerShape
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
    hasShadow: Boolean = true,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = FoodDeliveryButtonDefaults.mainButtonColors,
        shape = buttonRoundedCornerShape,
        elevation = FoodDeliveryTheme.dimensions.getButtonEvaluation(hasShadow),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(FoodDeliveryTheme.dimensions.smallProgressBarSize),
                color = FoodDeliveryTheme.colors.mainColors.onDisabled
            )
        } else {
            Text(
                text = stringResource(textStringId),
                style = FoodDeliveryTheme.typography.labelLarge.medium,
                color = FoodDeliveryTheme.colors.mainColors.onPrimary
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingButtonPreview() {
    FoodDeliveryTheme {
        Box(modifier = Modifier.background(FoodDeliveryTheme.colors.mainColors.background)) {
            LoadingButton(
                textStringId = R.string.action_create_order_create_order,
                isLoading = false
            ) {}
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingButtonLoadingPreview() {
    FoodDeliveryTheme {
        Box(modifier = Modifier.background(FoodDeliveryTheme.colors.mainColors.background)) {
            LoadingButton(
                textStringId = R.string.action_create_order_create_order,
                isLoading = true
            ) {}
        }
    }
}