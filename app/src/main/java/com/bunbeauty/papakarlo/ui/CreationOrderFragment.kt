package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.common.extensions.toggleVisibility
import com.bunbeauty.common.extensions.visible
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.CreationOrderViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher
import com.google.android.material.button.MaterialButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreationOrderFragment : BarsFragment<FragmentCreationOrderBinding>() {

    override var layoutId = R.layout.fragment_creation_order
    override val viewModel: CreationOrderViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.viewModel = viewModel

        viewModel.hasAddressState.onEach { state ->
            when (state) {
                is State.Success -> {
                    viewDataBinding.fragmentCreationOrderGroupHasAddress.toggleVisibility(state.data)
                    viewDataBinding.fragmentCreationOrderGroupNoAddress.toggleVisibility(!state.data)
                }
                else -> { }
            }
        }.launchWhenStarted(lifecycleScope)

        viewModel.selectedAddressState.onEach { state ->
            when (state) {
                is State.Success -> {
                    viewDataBinding.fragmentCreationOrderBtnAddressPick.text = state.data
                }
                else -> { }
            }
        }.launchWhenStarted(lifecycleScope)
        viewModel.getAddress()
        subscribe(viewModel.deliveryStringLiveData) { deliveryString ->
            viewDataBinding.fragmentCreationOrderTvDelivery.text = deliveryString
        }

        subscribe(viewModel.orderStringLiveData) { orderString ->
            viewDataBinding.fragmentCreationOrderBtnCreateOrder.text = orderString
        }

        subscribe(viewModel.isDeliveryLiveData) { isDelivery ->
            if (isDelivery) {
                activateButton(viewDataBinding.fragmentCreationOrderBtnDelivery)
                inactivateButton(viewDataBinding.fragmentCreationOrderBtnPickup)
            } else {
                inactivateButton(viewDataBinding.fragmentCreationOrderBtnDelivery)
                activateButton(viewDataBinding.fragmentCreationOrderBtnPickup)
            }
            viewDataBinding.fragmentCreationOrderTvDelivery.toggleVisibility(isDelivery)
        }
        subscribe(viewModel.deferredTextLiveData) { deferredText ->
            viewDataBinding.fragmentCreationOrderBtnSelectedDeferred.text = deferredText
        }

        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentCreationOrderEtPhone)
        viewDataBinding.fragmentCreationOrderEtPhone.addTextChangedListener(phoneTextWatcher)

        setOnClickListeners()
        super.onViewCreated(view, savedInstanceState)
    }


    fun setOnClickListeners() {
        viewDataBinding.fragmentCreationOrderBtnAddressPick.setOnClickListener {
            viewModel.onAddressClicked()
        }

        viewDataBinding.fragmentCreationOrderBtnCreateAddress.setOnClickListener {
            viewModel.onCreateAddressClicked()
        }
        viewDataBinding.fragmentCreationOrderBtnCreateOrder.setOnClickListener {
            createOrder()
        }

        viewDataBinding.fragmentCreationOrderBtnDelivery.setOnClickListener {
            viewModel.isDeliveryLiveData.value = true
        }
        viewDataBinding.fragmentCreationOrderBtnPickup.setOnClickListener {
            viewModel.isDeliveryLiveData.value = false
        }

        viewDataBinding.fragmentCreationOrderBtnSelectedDeferred.setOnClickListener {
            showTimePicker()
        }
        viewDataBinding.fragmentCreationOrderBtnDeferred.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .setHour(viewModel.deferredHoursLiveData.value ?: 0)
            .setMinute(viewModel.deferredMinutesLiveData.value ?: 0)
            .setTitleText(requireContext().getString(R.string.title_creation_order_deferred_time))
            .build()
        picker.show(parentFragmentManager, "TimePicker")

        picker.addOnPositiveButtonClickListener {
            if (viewModel.isDeferredTimeCorrect(picker.hour, picker.minute)) {
                viewDataBinding.fragmentCreationOrderGroupAddDeferred.gone()
                viewDataBinding.fragmentCreationOrderGroupPickedDeferred.visible()
                viewModel.deferredHoursLiveData.value = picker.hour
                viewModel.deferredMinutesLiveData.value = picker.minute

            } else {
                showError(resourcesProvider.getString(R.string.error_creation_order_deferred))
            }
        }
        picker.addOnNegativeButtonClickListener {
            viewDataBinding.fragmentCreationOrderGroupAddDeferred.visible()
            viewDataBinding.fragmentCreationOrderGroupPickedDeferred.gone()
            viewModel.deferredHoursLiveData.value = null
            viewModel.deferredMinutesLiveData.value = null
        }
    }

    private fun createOrder() {
        if (!viewModel.isNetworkConnected()) {
            showError(requireContext().getString(R.string.error_creation_order_connect))
            return
        }

        if (!viewModel.iFieldHelper.isCorrectFieldContent(
                viewDataBinding.fragmentCreationOrderEtComment.text.toString(),
                false,
                100
            )
        ) {
            viewDataBinding.fragmentCreationOrderEtComment.error =
                resources.getString(R.string.error_creation_order_comment)
            viewDataBinding.fragmentCreationOrderEtComment.requestFocus()
            return
        }
        if (!viewModel.iFieldHelper.isCorrectFieldContent(
                viewDataBinding.fragmentCreationOrderEtPhone.text.toString(),
                true,
                18,
                18
            )
        ) {
            viewDataBinding.fragmentCreationOrderEtPhone.error =
                resources.getString(R.string.error_creation_order_phone)
            viewDataBinding.fragmentCreationOrderEtPhone.requestFocus()
            return
        }

        viewModel.createOrder(
            viewDataBinding.fragmentCreationOrderEtComment.text.toString().trim(),
            viewDataBinding.fragmentCreationOrderEtPhone.text.toString(),
            viewDataBinding.fragmentCreationOrderEtEmail.text.toString().trim(),
            viewModel.deferredHoursLiveData.value,
            viewModel.deferredMinutesLiveData.value
        )
    }

    private fun activateButton(button: MaterialButton) {
        button.backgroundTintList =
            ColorStateList.valueOf(resourcesProvider.getColor(R.color.colorPrimary))
        button.setTextColor(resourcesProvider.getColor(R.color.white))
    }

    private fun inactivateButton(button: MaterialButton) {
        button.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        button.setTextColor(resourcesProvider.getColor(R.color.grey))
    }
}