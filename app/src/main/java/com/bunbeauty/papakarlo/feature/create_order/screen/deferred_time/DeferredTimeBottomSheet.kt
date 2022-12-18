package com.bunbeauty.papakarlo.feature.create_order.screen.deferred_time

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.SimpleCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape
import com.bunbeauty.shared.presentation.create_order.model.TimeUI
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.color.MaterialColors
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DeferredTimeBottomSheet : ComposeBottomSheet<TimeUI>() {

    private var deferredTime by argument<TimeUI>()
    private var minTime by argument<TimeUI.Time>()
    private var title by argument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.root.setContent {
            DeferredTimeScreen(
                title = title,
                onAsapClicked = {
                    callback?.onResult(TimeUI.ASAP)
                    dismiss()
                },
                onSelectTimeClicked = {
                    showTimePicker()
                }
            )
        }
    }

    private fun showTimePicker() {
        val colorPrimary =
            MaterialColors.getColor(requireContext(), R.attr.colorPrimary, Color.BLACK)
        val colorOnSurfaceVariant =
            MaterialColors.getColor(requireContext(), R.attr.colorOnSurfaceVariant, Color.BLACK)
        val colorOnSurface =
            MaterialColors.getColor(requireContext(), R.attr.colorOnSurface, Color.BLACK)
        val hours = deferredTime.let { timeUi ->
            if (timeUi is TimeUI.Time) {
                timeUi.hours
            } else {
                minTime.hours
            }
        }
        val minutes = deferredTime.let { timeUi ->
            if (timeUi is TimeUI.Time) {
                timeUi.minutes
            } else {
                minTime.minutes
            }
        }
        val timePicker = TimePickerDialog.newInstance(
            { _, hour, minute, _ ->
                callback?.onResult(TimeUI.Time(hours = hour, minutes = minute))
                dismiss()
            },
            hours,
            minutes,
            true
        ).apply {
            title = title
            accentColor = colorPrimary
            setCancelColor(colorOnSurfaceVariant)
            setOkColor(colorOnSurface)
            setMinTime(
                minTime.hours,
                minTime.minutes,
                0
            )
            setOnCancelListener {
                dismiss()
            }
        }
        timePicker.show(childFragmentManager, null)
    }

    companion object {
        private const val TAG = "DeferredTimeBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            deferredTime: TimeUI,
            minTime: TimeUI.Time,
            title: String,
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
    onSelectTimeClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            text = title,
            style = FoodDeliveryTheme.typography.h2,
            color = FoodDeliveryTheme.colors.onSurface
        )
        Column(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace)) {
            SimpleCard(
                modifier = Modifier.clip(mediumRoundedCornerShape),
                text = stringResource(R.string.action_deferred_time_asap),
                hasShadow = false
            ) {
                onAsapClicked()
            }
            NavigationCard(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                labelStringId = R.string.action_deferred_time_select_time,
                hasShadow = false
            ) {
                onSelectTimeClicked()
            }
        }
    }
}
