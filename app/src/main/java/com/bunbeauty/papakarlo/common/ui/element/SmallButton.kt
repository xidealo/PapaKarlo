package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.buttonRoundedCornerShape

@Composable
fun SmallButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
    hasShadow: Boolean = true,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = FoodDeliveryTheme.colors.mainOutlineButtonColors(),
        border = BorderStroke(
            width = 2.dp,
            color = FoodDeliveryTheme.colors.primary
        ),
        shape = buttonRoundedCornerShape,
        elevation = FoodDeliveryTheme.dimensions.getButtonEvaluation(hasShadow),
        enabled = isEnabled
    ) {
        Text(
            text = stringResource(textStringId),
            style = FoodDeliveryTheme.typography.smallButton,
        )
    }
}
