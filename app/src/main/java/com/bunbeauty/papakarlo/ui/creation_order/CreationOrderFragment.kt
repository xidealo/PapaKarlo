package com.bunbeauty.papakarlo.ui.creation_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.toAddressesBottomSheet
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.toCreationAddressFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.utils.string.IStringHelper
import com.bunbeauty.papakarlo.view_model.CreationOrderViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderFragment : BarsFragment<FragmentCreationOrderBinding, CreationOrderViewModel>() {

    @Inject
    lateinit var stringHelper: IStringHelper

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe(viewModel.addressLiveData) { address ->
            viewDataBinding.fragmentCreationOrderTvLastAddress.text = stringHelper.toString(address)
        }
        subscribe(viewModel.deliveryStringLiveData) { deliveryString ->
            viewDataBinding.fragmentCreationOrderTvDelivery.text = deliveryString
        }
        subscribe(viewModel.orderStringLiveData) { orderString ->
            viewDataBinding.fragmentCreationOrderBtnCreateOrder.text = orderString
        }
        subscribe(viewModel.isDeliveryLiveData) { isDelivery ->
            viewDataBinding.fragmentCreationOrderTvDelivery.toggleVisibility(isDelivery)
        }

        viewDataBinding.viewModel = viewModel
        viewDataBinding.fragmentCreationOrderMcvAddressPick.setOnClickListener {
            viewModel.onAddressClicked()
        }
        viewDataBinding.fragmentCreationOrderBtnCreateAddress.setOnClickListener {
            viewModel.onCreateAddressClicked()
        }
        viewDataBinding.fragmentCreationOrderBtnCreateOrder.setOnClickListener {
            createOrder()
        }
        viewDataBinding.fragmentCreationOrderRbDelivery.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isDeliveryLiveData.value = isChecked
        }
        viewDataBinding.fragmentOrderEtPhone.setText(viewModel.phoneNumber)
        viewDataBinding.fragmentOrderEtEmail.setText(viewModel.email)
        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentOrderEtPhone)
        viewDataBinding.fragmentOrderEtPhone.addTextChangedListener(phoneTextWatcher)
        viewDataBinding.fragmentCreationOrderBtnDeferred.setOnClickListener {
            showTimePicker()
        }
        viewDataBinding.fragmentCreationOrderMcvDeferred.setOnClickListener {
            showTimePicker()
        }
        subscribe(viewModel.deferredTextLiveData) { deferredText ->
            viewDataBinding.fragmentCreationOrderTvDeferred.text = deferredText
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
                viewDataBinding.fragmentCreationOrderGroupDeferred.gone()
                viewDataBinding.fragmentCreationOrderMcvDeferred.visible()
                viewModel.deferredHoursLiveData.value = picker.hour
                viewModel.deferredMinutesLiveData.value = picker.minute
            } else {
                showError(resourcesProvider.getString(R.string.error_creation_order_deferred))
            }
        }
        picker.addOnNegativeButtonClickListener {
            viewDataBinding.fragmentCreationOrderMcvDeferred.gone()
            viewDataBinding.fragmentCreationOrderGroupDeferred.visible()
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
}