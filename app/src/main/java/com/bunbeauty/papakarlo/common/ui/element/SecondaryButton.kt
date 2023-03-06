package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
    hasShadow: Boolean = true,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        FoodDeliveryCard(
            modifier = Modifier
                .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.buttonSize)
                .fillMaxHeight()
                .fillMaxWidth(),
            onClick = onClick,
            colors = FoodDeliveryTheme.colors.secondaryCardColors(),
            elevated = hasShadow
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                        vertical = FoodDeliveryTheme.dimensions.smallSpace
                    )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(textStringId),
                    style = FoodDeliveryTheme.typography.button,
                    color = FoodDeliveryTheme.colors.onSecondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun SecondaryButtonPreview() {
    SecondaryButton(textStringId = R.string.action_logout) {}
}
