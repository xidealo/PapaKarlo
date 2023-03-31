package com.bunbeauty.papakarlo.feature.consumer_cart.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.button.FoodDeliveryButtonDefaults
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold

@Composable
fun CountPicker(
    modifier: Modifier = Modifier,
    count: Int,
    onCountIncreased: () -> Unit,
    onCountDecreased: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(FoodDeliveryButtonDefaults.buttonShape)
            .border(
                border = BorderStroke(2.dp, FoodDeliveryTheme.colors.mainColors.primary),
                shape = FoodDeliveryButtonDefaults.buttonShape
            )
            .background(FoodDeliveryTheme.colors.mainColors.surface)
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountPickerButton(
            iconId = R.drawable.ic_minus_16,
            descriptionStringId = R.string.description_consumer_cart_decrease,
            onClick = onCountDecreased
        )
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = count.toString(),
            style = FoodDeliveryTheme.typography.bodySmall.bold,
            color = FoodDeliveryTheme.colors.mainColors.primary,
        )
        CountPickerButton(
            iconId = R.drawable.ic_plus_16,
            descriptionStringId = R.string.description_consumer_cart_increase,
            onClick = onCountIncreased
        )
    }
}

@Composable
fun CountPickerButton(
    @DrawableRes iconId: Int,
    @StringRes descriptionStringId: Int,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(36.dp),
        onClick = onClick,
        colors = FoodDeliveryButtonDefaults.iconButtonColors,
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(iconId),
            tint = FoodDeliveryTheme.colors.mainColors.primary,
            contentDescription = stringResource(descriptionStringId),
        )
    }
}

@Preview
@Composable
private fun CountPickerOneDigitPreview() {
    FoodDeliveryTheme {
        CountPicker(
            count = 5,
            onCountIncreased = {},
            onCountDecreased = {}
        )
    }
}

@Preview
@Composable
private fun CountPickerTwoDigitsPreview() {
    FoodDeliveryTheme {
        CountPicker(
            count = 99,
            onCountIncreased = {},
            onCountDecreased = {}
        )
    }
}
