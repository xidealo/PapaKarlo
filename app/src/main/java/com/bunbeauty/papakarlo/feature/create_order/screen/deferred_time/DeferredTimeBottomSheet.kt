package com.bunbeauty.papakarlo.feature.create_order.screen.deferred_time

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.SimpleCard
import com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.shared.presentation.create_order.model.TimeUI
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DeferredTimeBottomSheet : ComposeBottomSheet<TimeUI>() {

    private var deferredTime by argument<TimeUI>()
    private var minTime by argument<TimeUI.Time>()
    private var title by argument<String>()

    private val hours by lazy {
        deferredTime.let { timeUi ->
            if (timeUi is TimeUI.Time) {
                timeUi.hours
            } else {
                minTime.hours
            }
        }
    }
    private val minutes by lazy {
        deferredTime.let { timeUi ->
            if (timeUi is TimeUI.Time) {
                timeUi.minutes
            } else {
                minTime.minutes
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            val timeDialogState = rememberMaterialDialogState()
            DeferredTimeScreen(
                title = title,
                onAsapClicked = {
                    callback?.onResult(TimeUI.ASAP)
                    dismiss()
                },
                onSelectTimeClicked = {
                    timeDialogState.show()
                }
            )
            TimePickerDialog(timeDialogState)
        }
    }

    @Composable
    private fun TimePickerDialog(dialogState: MaterialDialogState) {
        var pickedTime by remember {
            mutableStateOf(LocalTime.of(hours, minutes))
        }
        MaterialDialog(
            dialogState = dialogState,
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface
        ) {
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
                initialTime = LocalTime.of(hours, minutes),
                timeRange = LocalTime.of(minTime.hours, minTime.minutes)..LocalTime.MAX,
                waitForPositiveButton = false
            ) { time ->
                pickedTime = time
            }
            Row(modifier = Modifier.padding(bottom = 24.dp, end = 24.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    dialogState.hide()
                }) {
                    Text(
                        text = stringResource(R.string.common_cancel),
                        style = FoodDeliveryTheme.typography.labelLarge.medium,
                        color = FoodDeliveryTheme.colors.mainColors.disabled
                    )
                }
                TextButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {
                        callback?.onResult(
                            TimeUI.Time(
                                hours = pickedTime.hour,
                                minutes = pickedTime.minute
                            )
                        )
                        dismiss()
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

    companion object {
        private const val TAG = "DeferredTimeBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            deferredTime: TimeUI,
            minTime: TimeUI.Time,
            title: String
        ) = suspendCoroutine { continuation ->
            DeferredTimeBottomSheet().apply {
                this.deferredTime = deferredTime
                this.minTime = minTime
                this.title = title
                callback = object : Callback<TimeUI> {
                    override fun onResult(result: TimeUI?) {
                        continuation.resume(result)
                    }
                }
                show(fragmentManager, TAG)
            }
        }
    }
}

@Composable
private fun DeferredTimeScreen(
    title: String,
    onAsapClicked: () -> Unit,
    onSelectTimeClicked: () -> Unit
) {
    FoodDeliveryBottomSheet(title = title) {
        SimpleCard(
            text = stringResource(R.string.action_deferred_time_asap),
            elevated = false,
            onClick = onAsapClicked
        )
        NavigationCard(
            modifier = Modifier.padding(top = 8.dp),
            elevated = false,
            label = stringResource(R.string.action_deferred_time_select_time),
            onClick = onSelectTimeClicked
        )
    }
}

@Preview
@Composable
private fun DeferredTimeScreenPreview() {
    FoodDeliveryTheme {
        DeferredTimeScreen(
            title = "Время доставки",
            onAsapClicked = {},
            onSelectTimeClicked = {}
        )
    }
}
