package com.bunbeauty.papakarlo.common.ui.element.switcher

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.buttonRoundedCornerShape
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun FoodDeliverySwitcher(
    @StringRes optionResIdList: List<Int>,
    modifier: Modifier = Modifier,
    position: Int = 0,
    onPositionChanged: (Int) -> Unit,
) {
    FoodDeliverySwitcher(
        modifier = modifier,
        optionList = optionResIdList.map { stringResource(it) },
        position = position,
        onPositionChanged = onPositionChanged,
    )
}

@Composable
fun FoodDeliverySwitcher(
    modifier: Modifier = Modifier,
    optionList: List<String> = emptyList(),
    position: Int = 0,
    onPositionChanged: (Int) -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = FoodDeliveryTheme.dimensions.cardEvaluation(),
        shape = buttonRoundedCornerShape,
        colors = FoodDeliveryCardDefaults.cardColors,
    ) {
        Row(
            modifier = Modifier.padding(
                FoodDeliveryTheme.dimensions.verySmallSpace
            )
        ) {
            optionList.onEachIndexed { i, text ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwitcherButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .height(40.dp),
        onClick = onClick,
        enabled = enabled,
        shape = buttonRoundedCornerShape,
        colors = FoodDeliverySwitcherDefaults.switcherButtonColor,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = FoodDeliveryTheme.typography.labelLarge.medium,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SwitcherPreview() {
    FoodDeliveryTheme {
        FoodDeliverySwitcher(
            optionResIdList = listOf(
                R.string.action_create_order_delivery,
                R.string.action_create_order_pickup
            ),
            position = 1,
            onPositionChanged = {}
        )
    }
}
