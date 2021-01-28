package com.bunbeauty.papakarlo.ui.creation_order

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.order.OrderEntity
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.*
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher
import com.bunbeauty.papakarlo.view_model.CreationOrderViewModel
import java.lang.ref.WeakReference

class CreationOrderFragment :
    CartClickableFragment<FragmentCreationOrderBinding, CreationOrderViewModel>(),
    CreationOrderNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_creation_order
    override var viewModelClass = CreationOrderViewModel::class.java
    override lateinit var title: String

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLastDeliveryAddress()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_order)
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        viewDataBinding.fragmentCreationOrderRbDelivery.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                viewModel.isDeliveryField.set(true)
                viewModel.getLastDeliveryAddress()
            } else {
                viewModel.isDeliveryField.set(false)
                viewModel.getLastPickupAddress()
            }
        }
        viewDataBinding.fragmentCreationOrderTvLastAddress.setOnClickListener {
            goToAddresses()
        }
        viewDataBinding.fragmentCreationOrderIvAddress.setOnClickListener {
            goToAddresses()
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            (activity as MainActivity).showError(it)
        }

        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentOrderEtPhone)
        viewDataBinding.fragmentOrderEtPhone.addTextChangedListener(phoneTextWatcher)
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
                email = viewDataBinding.fragmentOrderEtEmail.text.toString().trim()
            )
        )

        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
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
            toAddressesBottomSheet(viewModel.isDeliveryField.get()!!)
        )
    }
}