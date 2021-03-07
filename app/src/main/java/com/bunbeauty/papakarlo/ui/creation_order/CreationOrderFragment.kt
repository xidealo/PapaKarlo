package com.bunbeauty.papakarlo.ui.creation_order

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.order.OrderEntity
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.*
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.utils.string.IStringHelper
import com.bunbeauty.papakarlo.view_model.CreationOrderViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderFragment :
    CartClickableFragment<FragmentCreationOrderBinding, CreationOrderViewModel>(),
    CreationOrderNavigator {

    override lateinit var title: String

    @Inject
    lateinit var stringHelper: IStringHelper

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_order)

        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        viewModel.addressLiveData.observe(viewLifecycleOwner) { address ->
            viewDataBinding.fragmentCreationOrderTvLastAddress.text = stringHelper.toString(address)
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            (activity as MainActivity).showError(it)
        }
        viewModel.orderStringLiveData.observe(viewLifecycleOwner) { orderString ->
            viewDataBinding.fragmentCreationOrderBtnCreateOrder.text = orderString
        }

        viewDataBinding.viewModel = viewModel
        viewDataBinding.fragmentCreationOrderMcvAddressPick.setOnClickListener {
            goToAddresses()
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
                (activity as MainActivity).showError(resourcesProvider.getString(R.string.error_creation_order_deferred))
            }
        }
        picker.addOnNegativeButtonClickListener {
            viewDataBinding.fragmentCreationOrderMcvDeferred.gone()
            viewDataBinding.fragmentCreationOrderGroupDeferred.visible()
            viewModel.deferredHoursLiveData.value = null
            viewModel.deferredMinutesLiveData.value = null
        }
    }

    override fun createDeliveryOrder() {
        if (!viewModel.isNetworkConnected()) {
            (activity as MainActivity).showError(requireContext().getString(R.string.error_creation_order_connect))
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

        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    override fun goToMain(orderEntity: OrderEntity) {
        (activity as MainActivity).showMessage(resourcesProvider.getString(R.string.msg_creation_order_order_code) + orderEntity.code)
        findNavController().navigate(backToMainFragment())
    }

    override fun goToCart(view: View) {
        findNavController().navigate(backToCartFragment())
    }

    override fun goToCreationAddress() {
        findNavController().navigate(toCreationAddressFragment())
    }

    private fun goToAddresses() {
        findNavController().navigate(
            toAddressesBottomSheet(viewModel.isDeliveryLiveData.value!!)
        )
    }
}