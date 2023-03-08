package com.bunbeauty.papakarlo.feature.create_order.ui

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
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.buttonRoundedCornerShape
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun Switcher(
    modifier: Modifier = Modifier,
    @StringRes variantStringIdList: List<Int>? = null,
    variantList: List<String> = emptyList(),
    position: Int = 0,
    onPositionChanged: (Int) -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = FoodDeliveryTheme.dimensions.cardEvaluation(),
        shape = buttonRoundedCornerShape,
        colors = FoodDeliveryTheme.colors.cardColors()
    ) {
        Row(
            modifier = Modifier.padding(
                FoodDeliveryTheme.dimensions.verySmallSpace
            )
        ) {
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
        shape = buttonRoundedCornerShape,
        colors = FoodDeliveryTheme.colors.switcherButtonColor(enabled),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = FoodDeliveryTheme.typography.labelLarge.medium,
                color = FoodDeliveryTheme.colors.switcherButtonTextColor(enabled),
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SwitcherPreview() {
    FoodDeliveryTheme {
        Switcher(
            variantStringIdList = listOf(
                R.string.action_create_order_delivery,
                R.string.action_create_order_pickup
            ),
            position = 1
        ) { }
    }
}
