package com.bunbeauty.shared.ui.screen.createorder.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import com.bunbeauty.shared.ui.screen.createorder.TimePickerUI
import com.bunbeauty.shared.ui.screen.createorder.TimeUI
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.common_cancel
import papakarlo.shared.generated.resources.common_ok

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    timePicker: TimePickerUI,
    onAction: (CreateOrder.Action) -> Unit,
) {
    if (!timePicker.isShown) return

    Dialog(
        onDismissRequest = {
            onAction(CreateOrder.Action.HideTimePicker)
        },
    ) {
        val timePickerState =
            rememberTimePickerState(
                initialHour = timePicker.initialTime.hours,
                initialMinute = timePicker.initialTime.minutes,
                is24Hour = true,
            )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier.background(
                    color = FoodDeliveryTheme.colors.mainColors.surface,
                    shape = RoundedCornerShape(size = 16.dp),
                ),
        ) {
            TimePicker(
                modifier = Modifier.padding(top = 16.dp),
                state = timePickerState,
            )

            Row(
                modifier =
                    Modifier
                        .padding(bottom = 24.dp)
                        .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = {
                        onAction(CreateOrder.Action.HideTimePicker)
                    },
                ) {
                    Text(
                        text = stringResource(Res.string.common_cancel),
                        style = FoodDeliveryTheme.typography.labelLarge.medium,
                        color = FoodDeliveryTheme.colors.mainColors.onSecondary,
                    )
                }
                TextButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {
                        onAction(
                            CreateOrder.Action.ChangeDeferredTime(
                                Time(
                                    hours = timePickerState.hour,
                                    minutes = timePickerState.minute,
                                ),
                            ),
                        )
                    },
                ) {
                    Text(
                        text = stringResource(Res.string.common_ok),
                        style = FoodDeliveryTheme.typography.labelLarge.medium,
                        color = FoodDeliveryTheme.colors.mainColors.primary,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TimePickerDialogPreview() {
    TimePickerDialog(
        timePicker =
            TimePickerUI(
                isShown = true,
                minTime = TimeUI(1, 30),
                initialTime = TimeUI(2, 30),
            ),
        onAction = {},
    )
}
