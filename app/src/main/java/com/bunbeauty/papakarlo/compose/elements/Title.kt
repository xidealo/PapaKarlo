package com.bunbeauty.papakarlo.compose.elements

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme

@Composable
fun Title(@StringRes textStringId: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = FoodDeliveryTheme.dimensions.mediumSpace,
                end = FoodDeliveryTheme.dimensions.mediumSpace,
                top = FoodDeliveryTheme.dimensions.mediumSpace,
            ),
        text = stringResource(textStringId),
        style = FoodDeliveryTheme.typography.h2,
        textAlign = TextAlign.Center
    )
}