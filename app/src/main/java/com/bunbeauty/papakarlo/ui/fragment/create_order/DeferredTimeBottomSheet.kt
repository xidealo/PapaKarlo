package com.bunbeauty.papakarlo.ui.fragment.create_order

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bunbeauty.common.Constants.ASAP
import com.bunbeauty.common.Constants.DEFERRED_TIME_REQUEST_KEY
import com.bunbeauty.common.Constants.SELECTED_DEFERRED_TIME_KEY
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetDeferedTimeBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.presentation.create_order.DeferredTimeViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DeferredTimeBottomSheet : BaseBottomSheetDialog<BottomSheetDeferedTimeBinding>() {

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override val viewModel: DeferredTimeViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private val args: DeferredTimeBottomSheetArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            bottomSheetDeferredTimeTvTitle.text = args.title
            bottomSheetDeferredTimeMcvAsap.setOnClickListener {
                sendResult(resourcesProvider.getString(R.string.msg_deferred_time_asap))
            }
            bottomSheetDeferredTimeNcSelectTime.setOnClickListener {
                showTimePicker()
            }
        }
        viewModel.selectedTime.onEach { selectedTime ->
            sendResult(selectedTime)
        }.startedLaunch(viewLifecycleOwner)
    }

    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .setHour(viewModel.currentTimeHour)
            .setMinute(viewModel.currentTimeMinute)
            .setTitleText(resourcesProvider.getString(R.string.title_deferred_time_select_time))
            .build()
        picker.addOnPositiveButtonClickListener {
            viewModel.onTimeSelected(picker.hour, picker.minute)
        }
        picker.show(parentFragmentManager, null)
    }

    private fun sendResult(result: String) {
        setFragmentResult(
            DEFERRED_TIME_REQUEST_KEY,
            bundleOf(SELECTED_DEFERRED_TIME_KEY to result)
        )
        viewModel.goBack()
    }
}