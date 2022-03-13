package com.bunbeauty.papakarlo.compose.custom

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.compose.theme.smallRoundedCornerShape

@Composable
fun Switcher(
    modifier: Modifier = Modifier,
    @StringRes variantStringIdList: List<Int>? = null,
    variantList: List<String> = emptyList(),
    position: Int = 0,
    onPositionChanged: (Int) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = FoodDeliveryTheme.dimensions.elevation,
        shape = mediumRoundedCornerShape,
        backgroundColor = FoodDeliveryTheme.colors.surface
    ) {
        Row(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.smallSpace)) {
            val buttonTextList = variantStringIdList?.map { variantStringId ->
                stringResource(variantStringId)
            } ?: variantList
            buttonTextList.onEachIndexed { i, text ->
                val startSpace = if (i == 0) {
                    0.dp
                } else {
                    FoodDeliveryTheme.dimensions.smallSpace
                }
                SwitcherButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = startSpace),
                    text = text,
                    enabled = position != i,
                    onClick = {
                        onPositionChanged(i)
                    }
                )
            }
        }
    }
}

@Composable
private fun SwitcherButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        Card(
            modifier = Modifier
                .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.smallButtonSize)
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(smallRoundedCornerShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onClick
                ),
            backgroundColor = FoodDeliveryTheme.colors.switcherButtonColor(enabled)
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
                    text = text.uppercase(),
                    style = FoodDeliveryTheme.typography.button,
                    color = FoodDeliveryTheme.colors.switcherButtonTextColor(enabled)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SwitcherPreview() {
    Switcher(
        variantStringIdList = listOf(
            R.string.action_create_order_delivery,
            R.string.action_create_order_pickup
        ),
        position = 1
    ) { }
}