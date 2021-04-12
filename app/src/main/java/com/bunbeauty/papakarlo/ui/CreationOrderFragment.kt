package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.common.extensions.toggleVisibility
import com.bunbeauty.common.extensions.visible
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.CreationAddressViewModel
import com.bunbeauty.papakarlo.presentation.CreationOrderViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher
import com.google.android.material.button.MaterialButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import javax.inject.Inject

class CreationOrderFragment : BarsFragment<FragmentCreationOrderBinding>() {

    override var layoutId = R.layout.fragment_creation_order
    override val viewModel: CreationOrderViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var stringHelper: IStringHelper

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribe(viewModel.hasAddressLiveData) {
            viewDataBinding.fragmentCreationOrderGroupHasAddress.toggleVisibility(it)
            viewDataBinding.fragmentCreationOrderGroupNoAddress.toggleVisibility(!it)
        }
        viewDataBinding.viewModel = viewModel

        subscribe(viewModel.addressLiveData) { address ->
            viewDataBinding.fragmentCreationOrderBtnAddressPick.text =
                stringHelper.toString(address)
        }
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
        viewDataBinding.fragmentOrderEtPhone.setText(viewModel.phoneNumber)
        viewDataBinding.fragmentOrderEtEmail.setText(viewModel.email)
        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentOrderEtPhone)
        viewDataBinding.fragmentOrderEtPhone.addTextChangedListener(phoneTextWatcher)
        viewDataBinding.fragmentCreationOrderBtnDeferred.setOnClickListener {
            showTimePicker()
        }
        viewDataBinding.fragmentCreationOrderBtnSelectedDeferred.setOnClickListener {
            showTimePicker()
        }
        subscribe(viewModel.deferredTextLiveData) { deferredText ->
            viewDataBinding.fragmentCreationOrderBtnSelectedDeferred.text = deferredText
        }
        super.onViewCreated(view, savedInstanceState)
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

        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtComment.text.toString(),
                false,
                100
            )
        ) {
            viewDataBinding.fragmentOrderEtComment.error =
                resources.getString(R.string.error_creation_order_comment)
            viewDataBinding.fragmentOrderEtComment.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtPhone.text.toString(),
                true,
                18,
                18
            )
        ) {
            viewDataBinding.fragmentOrderEtPhone.error =
                resources.getString(R.string.error_creation_order_phone)
            viewDataBinding.fragmentOrderEtPhone.requestFocus()
            return
        }

        viewModel.createOrder(
            viewDataBinding.fragmentOrderEtComment.text.toString().trim(),
            viewDataBinding.fragmentOrderEtPhone.text.toString(),
            viewDataBinding.fragmentOrderEtEmail.text.toString().trim(),
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