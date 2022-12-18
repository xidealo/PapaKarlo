package com.bunbeauty.papakarlo.feature.consumer_cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.bunbeauty.papakarlo.common.ui.smallIcon
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

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
                .defaultMinSize(
                    minWidth = FoodDeliveryTheme.dimensions.buttonSize,
                    minHeight = FoodDeliveryTheme.dimensions.buttonSize
                )
                .fillMaxHeight()
                .width(FoodDeliveryTheme.dimensions.smallButtonSize)
                .clip(mediumRoundedCornerShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onCountDecreased
                ),
            colors = FoodDeliveryTheme.colors.mainButtonCardColors()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .smallIcon(),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_minus_16),
                    contentDescription = stringResource(R.string.description_consumer_cart_decrease),
                    tint = FoodDeliveryTheme.colors.onPrimary
                )
            }
        }
        Text(
            modifier = Modifier.padding(FoodDeliveryTheme.dimensions.verySmallSpace),
            text = count.toString(),
            style = FoodDeliveryTheme.typography.button,
            color = FoodDeliveryTheme.colors.onPrimary,
        )
        Card(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = FoodDeliveryTheme.dimensions.buttonSize,
                    minHeight = FoodDeliveryTheme.dimensions.buttonSize
                )
                .fillMaxHeight()
                .width(FoodDeliveryTheme.dimensions.smallButtonSize)
                .clip(mediumRoundedCornerShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onCountIncreased
                ),
            colors = FoodDeliveryTheme.colors.mainButtonCardColors()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .smallIcon(),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_plus_16),
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
