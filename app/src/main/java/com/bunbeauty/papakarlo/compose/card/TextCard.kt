package com.bunbeauty.papakarlo.compose.card

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    @StringRes hint: Int,
    label: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = FoodDeliveryTheme.dimensions.cardHeight)
            .shadow(1.dp, mediumRoundedCornerShape)
            .clip(mediumRoundedCornerShape),
        backgroundColor = FoodDeliveryTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Text(
                text = stringResource(hint),
                style = FoodDeliveryTheme.typography.body2,
                color = FoodDeliveryTheme.colors.onSurfaceVariant
            )
            Text(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.verySmallSpace),
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
        hint = R.string.hint_settings_phone,
        label = "+7 999 000-00-00"
    )
}
