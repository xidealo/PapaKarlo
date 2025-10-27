package com.bunbeauty.papakarlo.common.ui.element.button

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun SmallButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
    elevated: Boolean = true,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = FoodDeliveryButtonDefaults.mainOutlineButtonColors,
        border =
            BorderStroke(
                width = 2.dp,
                color = FoodDeliveryTheme.colors.mainColors.primary,
            ),
        shape = FoodDeliveryButtonDefaults.buttonShape,
        elevation = FoodDeliveryButtonDefaults.getButtonElevation(elevated),
        enabled = isEnabled,
    ) {
        Text(
            text = stringResource(textStringId),
            style = FoodDeliveryTheme.typography.labelLarge.medium,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SmallButtonPreview() {
    FoodDeliveryTheme {
        SmallButton(
            modifier = Modifier.wrapContentSize(),
            textStringId = R.string.action_create_order_create_order,
            onClick = { },
        )
    }
}
