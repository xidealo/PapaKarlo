package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@Composable
fun SmallButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
    hasShadow: Boolean = true,
    onClick: () -> Unit,
    isEnabled: Boolean = true
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = FoodDeliveryTheme.colors.mainButtonColors(),
        shape = mediumRoundedCornerShape,
        elevation = FoodDeliveryTheme.dimensions.getButtonEvaluation(hasShadow),
        enabled = isEnabled
    ) {
        Text(
            text = stringResource(textStringId),
            style = FoodDeliveryTheme.typography.smallButton,
        )
    }
}
