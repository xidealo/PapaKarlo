package com.bunbeauty.papakarlo.ui.fragment.create_order

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.DEFERRED_TIME_REQUEST_KEY
import com.bunbeauty.common.Constants.SELECTED_DEFERRED_TIME_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetDeferredTimeBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.create_order.DeferredTimeViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

class DeferredTimeBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_deferred_time) {

    override val viewModel: DeferredTimeViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(BottomSheetDeferredTimeBinding::bind)

    private val selectedHour by argument<Int>()
    private val selectedMinute by argument<Int>()
    private val title by argument<String>()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            bottomSheetDeferredTimeTvTitle.text = title
            bottomSheetDeferredTimeMcvAsap.setOnClickListener {
                sendResult(null)
            }
            bottomSheetDeferredTimeNcSelectTime.setOnClickListener {
                showTimePicker()
            }
        }
    }

    private val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute, _ ->
        val selectedTimeMillis = viewModel.getSelectedMillis(hourOfDay, minute)
        sendResult(selectedTimeMillis)
    }

    private fun showTimePicker() {
        val hour = if (selectedHour == -1) {
            viewModel.minTimeHour
        } else {
            selectedHour
        }
        val minute = if (selectedMinute == -1) {
            viewModel.minTimeMinute
        } else {
            selectedMinute
        }
        val timePicker = TimePickerDialog.newInstance(timeListener, hour, minute, true).apply {
            title = this@DeferredTimeBottomSheet.title
            accentColor = resourcesProvider.getColorById(R.color.orange)
            setMinTime(viewModel.minTimeHour, viewModel.minTimeMinute, 0)
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