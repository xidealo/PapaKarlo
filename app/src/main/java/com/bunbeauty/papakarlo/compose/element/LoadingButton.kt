package com.bunbeauty.papakarlo.compose.element

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
    hasShadow: Boolean = true,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        Card(
            modifier = Modifier
                .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.buttonSize)
                .fillMaxHeight()
                .fillMaxWidth()
                .shadow(
                    elevation = FoodDeliveryTheme.dimensions.getEvaluation(hasShadow),
                    shape = mediumRoundedCornerShape
                )
                .clip(mediumRoundedCornerShape)
                .clickable(
                    enabled = !isLoading,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onClick
                ),
            backgroundColor = FoodDeliveryTheme.colors.primary,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                        vertical = FoodDeliveryTheme.dimensions.smallSpace
                    )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(FoodDeliveryTheme.dimensions.smallProgressBarSize)
                            .align(Alignment.Center),
                        color = FoodDeliveryTheme.colors.onPrimary
                    )
                } else {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(textStringId).uppercase(),
                        style = FoodDeliveryTheme.typography.button,
                        color = FoodDeliveryTheme.colors.onPrimary
                    )
                }
            }
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