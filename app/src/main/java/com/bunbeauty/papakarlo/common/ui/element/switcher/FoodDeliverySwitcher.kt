package com.bunbeauty.papakarlo.common.ui.element.switcher

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.switcher.FoodDeliverySwitcherDefaults.switcherShape
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun FoodDeliverySwitcher(
    @StringRes optionResIdList: List<Int>,
    modifier: Modifier = Modifier,
    position: Int = 0,
    onPositionChanged: (Int) -> Unit
) {
    FoodDeliverySwitcher(
        modifier = modifier,
        optionList = optionResIdList.map { optionIdRes ->
            stringResource(optionIdRes)
        },
        position = position,
        onPositionChanged = onPositionChanged
    )
}

@Composable
fun FoodDeliverySwitcher(
    modifier: Modifier = Modifier,
    optionList: List<String> = emptyList(),
    position: Int = 0,
    onPositionChanged: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .background(
                color = FoodDeliveryTheme.colors.mainColors.stroke,
                shape = switcherShape
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        optionList.onEachIndexed { i, text ->
            SwitcherButton(
                modifier = Modifier
                    .weight(1f),
                text = text,
                enabled = position != i,
                onClick = {
                    onPositionChanged(i)
                }
            )
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
    Card(
        modifier = modifier.height(40.dp),
        onClick = onClick,
        enabled = enabled,
        shape = FoodDeliverySwitcherDefaults.switcherButtonShape,
        colors = FoodDeliverySwitcherDefaults.switcherButtonColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = FoodDeliveryTheme.typography.labelLarge.medium
            )
        }
    }
}

@Preview
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
