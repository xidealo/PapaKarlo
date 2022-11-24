package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@Composable
fun SmallButton(
    modifier: Modifier = Modifier,
    @StringRes textStringId: Int,
    hasShadow: Boolean = true,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        Card(
            modifier = Modifier
                .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.smallButtonSize)
                .fillMaxHeight()
                .fillMaxWidth()
                .shadow(
                    elevation = FoodDeliveryTheme.dimensions.getEvaluation(hasShadow),
                    shape = mediumRoundedCornerShape
                )
                .clip(mediumRoundedCornerShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onClick
                ),
            backgroundColor = FoodDeliveryTheme.colors.primary
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
                    style = FoodDeliveryTheme.typography.smallButton,
                    color = FoodDeliveryTheme.colors.onPrimary
                )
            }
        }
    }
}
