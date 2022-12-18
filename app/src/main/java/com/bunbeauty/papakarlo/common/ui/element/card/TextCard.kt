package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    @StringRes hintStringId: Int,
    label: String
) {
    Card(
        modifier = modifier.defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.cardHeight),
        shape = mediumRoundedCornerShape,
        colors = FoodDeliveryTheme.colors.cardColors(),
        elevation = FoodDeliveryTheme.dimensions.cardEvaluation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                    vertical = FoodDeliveryTheme.dimensions.smallSpace
                )
        ) {
            Text(
                text = stringResource(hintStringId),
                style = FoodDeliveryTheme.typography.hint,
                color = FoodDeliveryTheme.colors.onSurfaceVariant
            )
            Text(
                text = label,
                style = FoodDeliveryTheme.typography.body1,
                color = FoodDeliveryTheme.colors.onSurface
            )
        }
    }
}

@Preview
@Composable
fun TextCardPreview() {
    TextCard(
        hintStringId = R.string.hint_settings_phone,
        label = "+7 999 000-00-00"
    )
}
