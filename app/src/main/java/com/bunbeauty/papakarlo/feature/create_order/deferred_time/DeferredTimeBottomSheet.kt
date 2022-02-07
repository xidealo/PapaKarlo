package com.bunbeauty.papakarlo.feature.create_order.deferred_time

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.DEFERRED_TIME_REQUEST_KEY
import com.bunbeauty.common.Constants.SELECTED_DEFERRED_TIME_KEY
import com.bunbeauty.domain.model.datee_time.Time
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.databinding.BottomSheetDeferredTimeBinding
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeferredTimeBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_deferred_time) {

    override val viewModel: DeferredTimeViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetDeferredTimeBinding::bind)

    private val selectedHour: Int by argument()
    private val selectedMinute: Int by argument()
    private val title: String by argument()

    private val timeListener = TimePickerDialog.OnTimeSetListener { _, hour, minute, _ ->
        viewModel.onTimeSelected(hour, minute)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setSelectedTime(selectedHour, selectedMinute)
        viewBinding.run {
            bottomSheetDeferredTimeTvTitle.text = title
            bottomSheetDeferredTimeMcvAsap.setOnClickListener {
                viewModel.onTimeSelectedAsap()
            }
            bottomSheetDeferredTimeNcSelectTime.setOnClickListener {
                viewModel.onSelectTimeClicked()
            }
        }
        viewModel.deferredTimeState.startedLaunch { deferredTimeState ->
            when (deferredTimeState) {
                is DeferredTimeState.Default -> Unit
                is DeferredTimeState.SelectTime -> {
                    showTimePicker(deferredTimeState.minTime, deferredTimeState.selectedTime)
                }
                is DeferredTimeState.TimeSelected -> {
                    sendResult(deferredTimeState.selectedTimeMillis)
                }
            }
        }
    }

    private fun showTimePicker(minTime: Time, selectedTime: Time) {
        val colorPrimary = resourcesProvider.getColorByAttr(R.attr.colorPrimary)
        val colorOnSurfaceVariant = resourcesProvider.getColorByAttr(R.attr.colorOnSurfaceVariant)
        val colorOnSurface = resourcesProvider.getColorByAttr(R.attr.colorOnSurface)
        val timePicker = TimePickerDialog.newInstance(
            timeListener,
            selectedTime.hourOfDay,
            selectedTime.minuteOfHour,
            true
        ).apply {
            title = this@DeferredTimeBottomSheet.title
            accentColor = colorPrimary
            setCancelColor(colorOnSurfaceVariant)
            setOkColor(colorOnSurface)
            setMinTime(minTime.hourOfDay, minTime.minuteOfHour, 0)
            setOnCancelListener {
                viewModel.onSelectTimeCanceled()
            }
        }
        timePicker.show(childFragmentManager, null)
    }

    private fun sendResult(selectedTimeMillis: Long?) {
        setFragmentResult(
            DEFERRED_TIME_REQUEST_KEY,
            bundleOf(SELECTED_DEFERRED_TIME_KEY to selectedTimeMillis)
        )
        viewModel.goBack()
    }
}