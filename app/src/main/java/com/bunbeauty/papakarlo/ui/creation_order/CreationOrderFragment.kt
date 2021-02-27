package com.bunbeauty.papakarlo.ui.creation_order

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
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
import com.bunbeauty.papakarlo.view_model.MainViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderFragment :
    CartClickableFragment<FragmentCreationOrderBinding, CreationOrderViewModel>(),
    CreationOrderNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_creation_order
    override var viewModelClass = CreationOrderViewModel::class.java
    override lateinit var title: String

    @Inject
    lateinit var stringHelper: IStringHelper

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    var deferredTime = ""

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_order)

        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)

        viewDataBinding.fragmentCreationOrderRbDelivery.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isDeliveryLiveData.value = isChecked
        }
        setAddressesObserver()
        viewDataBinding.fragmentCreationOrderMcvAddressPick.setOnClickListener {
            goToAddresses()
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            (activity as MainActivity).showError(it)
        }
        viewModel.cartLiveData.observe(viewLifecycleOwner) {
            viewDataBinding.fragmentCreationOrderBtnCreateOrder.text =
                "${viewDataBinding.fragmentCreationOrderBtnCreateOrder.text} на $it"
        }
        viewDataBinding.fragmentOrderEtPhone.setText(viewModel.phoneNumber)
        viewDataBinding.fragmentOrderEtEmail.setText(viewModel.email)
        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentOrderEtPhone)
        viewDataBinding.fragmentOrderEtPhone.addTextChangedListener(phoneTextWatcher)
        viewDataBinding.fragmentCreationOrderBtnDeferred.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(0)
                .setMinute(0)
                .setTitleText("Выберите время доставки")
                .build()
            picker.show(parentFragmentManager, "TimePicker")

            picker.addOnPositiveButtonClickListener {
                viewDataBinding.fragmentCreationOrderBtnDeferred.text =
                    "Время доставки ${picker.hour}:${picker.minute}"
                deferredTime = "${picker.hour}:${picker.minute}"
            }
            picker.addOnNegativeButtonClickListener {
                viewDataBinding.fragmentCreationOrderBtnDeferred.text =
                    "Отложенный заказ"
                deferredTime = ""
            }
        }
    }

    private fun setAddressesObserver() {
        viewModel.addressLiveData.observe(viewLifecycleOwner) { address ->
            viewDataBinding.fragmentCreationOrderTvLastAddress.text = stringHelper.toString(address)
        }
    }

    override fun createDeliveryOrder() {
        if (!(activity as MainActivity).viewModel.isNetworkConnected) {
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
            OrderEntity(
                comment = viewDataBinding.fragmentOrderEtComment.text.toString().trim(),
                phone = viewDataBinding.fragmentOrderEtPhone.text.toString(),
                email = viewDataBinding.fragmentOrderEtEmail.text.toString().trim(),
                deferred = deferredTime
            )
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

    fun goToAddresses() {
        findNavController().navigate(
            toAddressesBottomSheet(viewModel.isDeliveryLiveData.value!!)
        )
    }
}