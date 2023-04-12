package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun NavigationCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    @StringRes labelStringId: Int,
    onClick: () -> Unit
) {
    RowCard(
        modifier = modifier,
        enabled = enabled,
        elevated = elevated,
        labelStringId = labelStringId,
        endIconId = R.drawable.ic_right_arrow,
        onClick = onClick
    )
}

@Composable
fun NavigationCard(
    modifier: Modifier = Modifier,
    elevated: Boolean = true,
    label: String,
    onClick: () -> Unit
) {
    RowCard(
        modifier = modifier,
        elevated = elevated,
        label = label,
        endIconId = R.drawable.ic_right_arrow,
        onClick = onClick
    )
}

@Preview(showSystemUi = true)
@Composable
private fun NavigationCardPreview() {
    FoodDeliveryTheme {
        NavigationCard(
            labelStringId = R.string.title_about_app,
            onClick = {}
        )
    }
}
