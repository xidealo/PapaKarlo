package com.bunbeauty.papakarlo.feature.consumer_cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.buttonRoundedCornerShape

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
            .clip(buttonRoundedCornerShape)
            .background(FoodDeliveryTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = FoodDeliveryTheme.dimensions.buttonSize,
                    minHeight = FoodDeliveryTheme.dimensions.buttonSize
                ),
            onClick = onCountDecreased,
            colors = FoodDeliveryTheme.colors.iconButtonColors(),
        ) {
            Icon(
                modifier = Modifier
                    .icon16(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_minus_16),
                contentDescription = stringResource(R.string.description_consumer_cart_decrease),
                tint = FoodDeliveryTheme.colors.onPrimary
            )
        }
        Text(
            modifier = Modifier.padding(FoodDeliveryTheme.dimensions.verySmallSpace),
            text = count.toString(),
            style = FoodDeliveryTheme.typography.button,
            color = FoodDeliveryTheme.colors.onPrimary,
        )

        IconButton(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = FoodDeliveryTheme.dimensions.buttonSize,
                    minHeight = FoodDeliveryTheme.dimensions.buttonSize
                ),
            onClick = onCountIncreased,
            colors = FoodDeliveryTheme.colors.iconButtonColors(),
        ) {
            Icon(
                modifier = Modifier
                    .icon16(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_plus_16),
                contentDescription = stringResource(R.string.description_consumer_cart_increase),
                tint = FoodDeliveryTheme.colors.onPrimary
            )
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
