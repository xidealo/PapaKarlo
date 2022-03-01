package com.bunbeauty.papakarlo.compose.element

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
fun Title(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(FoodDeliveryTheme.dimensions.mediumSpace),
        text = stringResource(textStringId),
        style = FoodDeliveryTheme.typography.h2,
        textAlign = TextAlign.Center
    )
}