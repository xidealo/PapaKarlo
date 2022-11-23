package com.bunbeauty.papakarlo.feature.create_order.screen.deferred_time

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationCard
import com.bunbeauty.papakarlo.common.ui.element.card.SimpleCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bottomSheetShape
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.databinding.BottomSheetDeferredTimeBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.create_order.model.DeferredTimeSettingsUI
import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.shared.Constants.DEFERRED_TIME_REQUEST_KEY
import com.bunbeauty.shared.Constants.SELECTED_DEFERRED_TIME_KEY
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class DeferredTimeBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_deferred_time) {

    override val viewModel: DeferredTimeViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })
    override val viewBinding by viewBinding(BottomSheetDeferredTimeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetDeferredTimeCvMain.compose {
            val deferredTimeSettings by viewModel.deferredTimeSettingsUI.collectAsState()
            DeferredTimeScreen(deferredTimeSettings)
        }
    }

    private fun showTimePicker(deferredTimeSettingsUI: DeferredTimeSettingsUI) {
//        val colorPrimary = resourcesProvider.getColorByAttr(R.attr.colorPrimary)
//        val colorOnSurfaceVariant = resourcesProvider.getColorByAttr(R.attr.colorOnSurfaceVariant)
//        val colorOnSurface = resourcesProvider.getColorByAttr(R.attr.colorOnSurface)
//        val timePicker = TimePickerDialog.newInstance(
//            { _, hour, minute, _ ->
//                sendResult(TimeUI(hours = hour, minutes = minute))
//            },
//            deferredTimeSettingsUI.selectedTime.hours,
//            deferredTimeSettingsUI.selectedTime.minutes,
//            true
//        ).apply {
//            title = deferredTimeSettingsUI.title
//            accentColor = colorPrimary
//            setCancelColor(colorOnSurfaceVariant)
//            setOkColor(colorOnSurface)
//            setMinTime(
//                deferredTimeSettingsUI.minTime.hours,
//                deferredTimeSettingsUI.minTime.minutes,
//                0
//            )
//            setOnCancelListener {
//                dismiss()
//            }
//        }
//        timePicker.show(childFragmentManager, null)
    }

    private fun sendResult(selectedTime: TimeUI?) {
        setFragmentResult(
            DEFERRED_TIME_REQUEST_KEY,
            bundleOf(SELECTED_DEFERRED_TIME_KEY to selectedTime)
        )
        viewModel.goBack()
    }

    @Composable
    private fun DeferredTimeScreen(deferredTimeSettingsUI: DeferredTimeSettingsUI?) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(bottomSheetShape)
                .background(FoodDeliveryTheme.colors.surface)
        ) {
            if (deferredTimeSettingsUI == null) {
                DeferredTimeLoadingScreen()
            } else {
                DeferredTimeSuccessScreen(deferredTimeSettingsUI)
            }
        }
    }

    @Composable
    private fun DeferredTimeLoadingScreen() {
        Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressBar(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace)
            )
        }
    }

    @Composable
    private fun DeferredTimeSuccessScreen(deferredTimeSettingsUI: DeferredTimeSettingsUI) {
        Column {
            Title(titleText = deferredTimeSettingsUI.title)
            Column(modifier = Modifier.padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)) {
                SimpleCard(
                    modifier = Modifier.clip(mediumRoundedCornerShape),
                    label = R.string.action_deferred_time_asap,
                    hasShadow = false
                ) {
                    sendResult(null)
                }
                NavigationCard(
                    modifier = Modifier.padding(vertical = FoodDeliveryTheme.dimensions.mediumSpace),
                    labelStringId = R.string.action_deferred_time_select_time,
                    hasShadow = false
                ) {
                    showTimePicker(deferredTimeSettingsUI)
                }
            }
        }
    }

    @Preview
    @Composable
    private fun DeferredTimeSuccessScreenPreview() {
        DeferredTimeScreen(
            DeferredTimeSettingsUI(
                title = "Время доставки",
                minTime = TimeUI.Time(3, 30),
                selectedTime = TimeUI.Time(4, 0),
            )
        )
    }

    @Preview
    @Composable
    private fun DeferredTimeLoadingScreenPreview() {
        DeferredTimeScreen(null)
    }
}