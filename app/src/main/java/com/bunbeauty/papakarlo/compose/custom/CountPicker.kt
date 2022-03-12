package com.bunbeauty.papakarlo.compose.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.smallIcon
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape

@Composable
fun CountPicker(
    modifier: Modifier = Modifier,
    count: Int,
    onCountIncreased: () -> Unit,
    onCountDecreased: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(mediumRoundedCornerShape)
            .background(FoodDeliveryTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.smallButtonSize)
                .fillMaxHeight()
                .width(FoodDeliveryTheme.dimensions.smallButtonSize)
                .clip(mediumRoundedCornerShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onCountDecreased
                ),
            backgroundColor = FoodDeliveryTheme.colors.primary
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .smallIcon(),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_minus),
                    contentDescription = stringResource(R.string.description_consumer_cart_decrease),
                    tint = FoodDeliveryTheme.colors.onPrimary
                )
            }
        }
        Text(
            text = count.toString(),
            style = FoodDeliveryTheme.typography.h3,
            color = FoodDeliveryTheme.colors.onPrimary,
        )
        Card(
            modifier = Modifier
                .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.smallButtonSize)
                .fillMaxHeight()
                .width(FoodDeliveryTheme.dimensions.smallButtonSize)
                .clip(mediumRoundedCornerShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onCountIncreased
                ),
            backgroundColor = FoodDeliveryTheme.colors.primary
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .smallIcon(),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.description_consumer_cart_increase),
                    tint = FoodDeliveryTheme.colors.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun CountPickerOneDigitPreview() {
    CountPicker(
        count = 5,
        onCountIncreased = {},
        onCountDecreased = {}
    )
}

@Preview
@Composable
private fun CountPickerTwoDigitsPreview() {
    CountPicker(
        count = 99,
        onCountIncreased = {},
        onCountDecreased = {}
    )
}