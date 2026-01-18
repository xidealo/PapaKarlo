package com.bunbeauty.createorder.ui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.createorder.presentation.createorder.CreateOrder
import com.bunbeauty.designsystem.ui.element.card.NavigationCard
import com.bunbeauty.designsystem.ui.element.card.SimpleCard
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_deferred_time_asap
import papakarlo.designsystem.generated.resources.action_deferred_time_select_time

@Composable
fun DeferredTimeBottomSheet(
    isShown: Boolean,
    title: String,
    onAction: (CreateOrder.Action) -> Unit,
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
private fun ColumnScope.DeferredTimeBottomSheetContent(onAction: (CreateOrder.Action) -> Unit) {
    SimpleCard(
        text = stringResource(Res.string.action_deferred_time_asap),
        elevated = false,
        onClick = {
            onAction(CreateOrder.Action.AsapClick)
        },
    )
    NavigationCard(
        modifier = Modifier.padding(top = 8.dp),
        elevated = false,
        label = stringResource(Res.string.action_deferred_time_select_time),
        onClick = {
            onAction(CreateOrder.Action.PickTimeClick)
        },
    )
}

@Preview
@Composable
private fun DeferredTimeBottomSheetPreview() {
    Column {
        DeferredTimeBottomSheetContent(onAction = {})
    }
}
