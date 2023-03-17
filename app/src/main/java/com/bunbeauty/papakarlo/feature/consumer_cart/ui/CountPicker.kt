package com.bunbeauty.papakarlo.feature.consumer_cart.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
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
            .heightIn(
                min = FoodDeliveryTheme.dimensions.smallButtonSize
            )
            .clip(buttonRoundedCornerShape)
            .border(
                BorderStroke(2.dp, FoodDeliveryTheme.colors.primary),
                shape = buttonRoundedCornerShape
            )
            .background(FoodDeliveryTheme.colors.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onCountDecreased,
            colors = FoodDeliveryTheme.colors.iconButtonColors(),
        ) {
            Icon(
                modifier = Modifier
                    .size(12.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_minus_16),
                contentDescription = stringResource(R.string.description_consumer_cart_decrease),
                tint = FoodDeliveryTheme.colors.primary
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = FoodDeliveryTheme.dimensions.verySmallSpace)
                .padding(vertical = FoodDeliveryTheme.dimensions.smallSpace),
            text = count.toString(),
            style = FoodDeliveryTheme.typography.bodySmall.bold,
            color = FoodDeliveryTheme.colors.primary,
        )

        IconButton(
            onClick = onCountIncreased,
            colors = FoodDeliveryTheme.colors.iconButtonColors(),
        ) {
            Icon(
                modifier = Modifier
                    .size(12.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_plus_16),
                contentDescription = stringResource(R.string.description_consumer_cart_increase),
                tint = FoodDeliveryTheme.colors.primary
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
