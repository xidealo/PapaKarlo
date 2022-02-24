package com.bunbeauty.papakarlo.compose.element

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(FoodDeliveryTheme.dimensions.buttonHeight),
        colors = FoodDeliveryTheme.colors.buttonColors(),
        shape = mediumRoundedCornerShape,
        onClick = onClick
    ) {
        Text(
            text = stringResource(textStringId).uppercase(),
            style = FoodDeliveryTheme.typography.button,
            color = FoodDeliveryTheme.colors.onPrimary
        )
    }
}