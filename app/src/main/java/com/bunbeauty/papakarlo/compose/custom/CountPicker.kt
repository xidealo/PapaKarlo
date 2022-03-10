package com.bunbeauty.papakarlo.compose.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
            .clip(mediumRoundedCornerShape)
            .background(FoodDeliveryTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.size(FoodDeliveryTheme.dimensions.smallButtonSize),
            colors = FoodDeliveryTheme.colors.mainButtonColors(),
            elevation = FoodDeliveryTheme.dimensions.getButtonEvaluation(hasShadow = false),
            shape = mediumRoundedCornerShape,
            contentPadding = PaddingValues(0.dp),
            onClick = onCountDecreased
        ) {
            Icon(
                modifier = Modifier.smallIcon(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_minus),
                contentDescription = stringResource(R.string.description_consumer_cart_decrease),
                tint = FoodDeliveryTheme.colors.onPrimary
            )
        }
        Text(
            text = count.toString(),
            style = FoodDeliveryTheme.typography.h3,
            color = FoodDeliveryTheme.colors.onPrimary,
        )
        Button(
            modifier = Modifier.size(FoodDeliveryTheme.dimensions.smallButtonSize),
            colors = FoodDeliveryTheme.colors.mainButtonColors(),
            elevation = FoodDeliveryTheme.dimensions.getButtonEvaluation(hasShadow = false),
            shape = mediumRoundedCornerShape,
            contentPadding = PaddingValues(0.dp),
            onClick = onCountIncreased
        ) {
            Icon(
                modifier = Modifier.smallIcon(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
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