package com.bunbeauty.papakarlo.feature.createorder.screen.createorder.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.SimpleCard
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.shared.presentation.createorder.CreateOrder

@Composable
fun DeferredTimeBottomSheet(
    isShown: Boolean,
    title: String,
    onAction: (CreateOrder.Action) -> Unit
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(CreateOrder.Action.HideDeferredTime)
        },
        isShown = isShown,
        title = title,
    ) {
        DeferredTimeBottomSheetContent(onAction = onAction)
    }
}

@Composable
private fun ColumnScope.DeferredTimeBottomSheetContent(
    onAction: (CreateOrder.Action) -> Unit
) {
    SimpleCard(
        text = stringResource(R.string.action_deferred_time_asap),
        elevated = false,
        onClick = {
            onAction(CreateOrder.Action.AsapClick)
        }
    )
    NavigationCard(
        modifier = Modifier.padding(top = 8.dp),
        elevated = false,
        label = stringResource(R.string.action_deferred_time_select_time),
        onClick = {
            onAction(CreateOrder.Action.PickTimeClick)
        }
    )
}

@Preview
@Composable
private fun DeferredTimeBottomSheetPreview() {
    Column {
        DeferredTimeBottomSheetContent(onAction = {})
    }
}