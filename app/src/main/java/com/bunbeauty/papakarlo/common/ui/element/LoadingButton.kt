package com.bunbeauty.papakarlo.common.ui.element

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
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

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
        colors = FoodDeliveryTheme.colors.mainButtonColors(),
        shape = mediumRoundedCornerShape,
        elevation = FoodDeliveryTheme.dimensions.getButtonEvaluation(hasShadow),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(FoodDeliveryTheme.dimensions.smallProgressBarSize),
                color = FoodDeliveryTheme.colors.onPrimaryDisabled
            )
        } else {
            Text(
                text = stringResource(textStringId),
                style = FoodDeliveryTheme.typography.button,
                color = FoodDeliveryTheme.colors.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun LoadingButtonPreview() {
    Box(Modifier.background(FoodDeliveryTheme.colors.background)) {
        LoadingButton(
            textStringId = R.string.action_create_order_create_order,
            isLoading = false
        ) {}
    }
}

@Preview
@Composable
private fun LoadingButtonLoadingPreview() {
    Box(Modifier.background(FoodDeliveryTheme.colors.background)) {
        LoadingButton(
            textStringId = R.string.action_create_order_create_order,
            isLoading = true
        ) {}
    }
}
