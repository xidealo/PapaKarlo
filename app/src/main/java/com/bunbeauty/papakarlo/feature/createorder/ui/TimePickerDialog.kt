package com.bunbeauty.papakarlo.feature.createorder.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.feature.createorder.TimePickerUI
import com.bunbeauty.papakarlo.feature.createorder.TimeUI
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime

@Composable
fun TimePickerDialog(
    timePicker: TimePickerUI,
    onAction: (CreateOrder.Action) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    LaunchedEffect(timePicker.isShown) {
        if (timePicker.isShown) {
            dialogState.show()
        } else {
            dialogState.hide()
        }
    }

    var time by remember {
        mutableStateOf(
            LocalTime.of(
                timePicker.initialTime.hours,
                timePicker.initialTime.minutes
            )
        )
    }

    MaterialDialog(
        dialogState = dialogState,
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
        onCloseRequest = {
            onAction(CreateOrder.Action.HideTimePicker)
        }
    ) {
        val minTime = LocalTime.of(
            timePicker.minTime.hours,
            timePicker.minTime.minutes
        )
        timepicker(
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = FoodDeliveryTheme.colors.mainColors.primary.copy(0.2f),
                inactiveBackgroundColor = FoodDeliveryTheme.colors.mainColors.disabled,
                activeTextColor = FoodDeliveryTheme.colors.mainColors.primary,
                inactiveTextColor = FoodDeliveryTheme.colors.mainColors.onSurface,
                selectorColor = FoodDeliveryTheme.colors.mainColors.primary,
                selectorTextColor = FoodDeliveryTheme.colors.mainColors.onPrimary,
                headerTextColor = FoodDeliveryTheme.colors.mainColors.surface,
                borderColor = FoodDeliveryTheme.colors.mainColors.onSurface
            ),
            is24HourClock = true,
            initialTime = LocalTime.of(
                timePicker.initialTime.hours,
                timePicker.initialTime.minutes
            ),
            timeRange = minTime..LocalTime.MAX,
            waitForPositiveButton = false,
            onTimeChange = { newTime ->
                time = newTime
            }
        )
        Row(modifier = Modifier.padding(bottom = 24.dp, end = 24.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {
                    onAction(CreateOrder.Action.HideTimePicker)
                }
            ) {
                Text(
                    text = stringResource(R.string.common_cancel),
                    style = FoodDeliveryTheme.typography.labelLarge.medium,
                    color = FoodDeliveryTheme.colors.mainColors.onSecondary
                )
            }
            TextButton(
                modifier = Modifier.padding(start = 8.dp),
                onClick = {
                    onAction(
                        CreateOrder.Action.ChangeDeferredTime(
                            Time(
                                hours = time.hour,
                                minutes = time.minute
                            )
                        )
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.common_ok),
                    style = FoodDeliveryTheme.typography.labelLarge.medium,
                    color = FoodDeliveryTheme.colors.mainColors.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun TimePickerDialogPreview() {
    TimePickerDialog(
        timePicker = TimePickerUI(
            isShown = true,
            minTime = TimeUI(1, 30),
            initialTime = TimeUI(2, 30)
        ),
        onAction = {}
    )
}
