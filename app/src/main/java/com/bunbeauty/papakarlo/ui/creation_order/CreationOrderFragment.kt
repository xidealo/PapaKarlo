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
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.*
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher
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

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_order)

        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        setAddressesObserver()
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            (activity as MainActivity).showError(it)
        }
        viewModel.getCartProductsCost()
        viewModel.cartLiveData.observe(viewLifecycleOwner) {
            viewDataBinding.fragmentCreationOrderBtnCreateOrder.text = "Оформить заказ на $it"
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
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(CLOCK_24H)
                .setHour(viewModel.deferredHours ?: 0)
                .setMinute(viewModel.deferredMinutes ?: 0)
                .setTitleText(requireContext().getString(R.string.title_creation_order_deferred_time))
                .build()
            picker.show(parentFragmentManager, "TimePicker")

            picker.addOnPositiveButtonClickListener {
                viewModel.deferredHours = picker.hour
                viewModel.deferredMinutes = picker.minute
                viewDataBinding.fragmentCreationOrderBtnDeferred.text =
                    "Время доставки " + stringHelper.toStringTime(picker.hour, picker.minute)
            }
            picker.addOnNegativeButtonClickListener {
                viewDataBinding.fragmentCreationOrderBtnDeferred.text =
                    requireContext().getString(R.string.action_creation_order_deferred)
                viewModel.deferredHours = null
                viewModel.deferredMinutes = null
            }
        }
    }

    private fun setAddressesObserver() {
        viewModel.addressLiveData.observe(viewLifecycleOwner) { address ->
            viewDataBinding.fragmentCreationOrderTvLastAddress.text = stringHelper.toString(address)
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
            viewModel.deferredHours,
            viewModel.deferredMinutes
        )

        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    override fun goToMain(orderEntity: OrderEntity) {
        (activity as MainActivity).showMessage("Код заказа ${orderEntity.code}")
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