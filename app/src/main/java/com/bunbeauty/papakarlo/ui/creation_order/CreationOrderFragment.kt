package com.bunbeauty.papakarlo.ui.creation_order

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.*
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.ui.view.PhoneTextWatcher
import com.bunbeauty.papakarlo.view_model.CreationOrderViewModel
import com.google.android.material.internal.NavigationMenu
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
        viewModel.getLastAddress()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_order)
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        viewDataBinding.fragmentCreationOrderRbDelivery.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                viewModel.isDeliveryField.set(true)
                viewModel.getLastAddress()
            }
        }
        viewDataBinding.fragmentCreationOrderRbPickup.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                viewModel.isDeliveryField.set(false)
                viewModel.lastAddressField.set("Нажмите, чтобы выбрать адрес")
            }
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

        /* viewModel.createOrder(
             Order(
                 street = viewDataBinding.fragmentOrderEtStreet.text.toString(),
                 house = viewDataBinding.fragmentOrderEtHouse.text.toString(),
                 flat = viewDataBinding.fragmentOrderEtFlat.text.toString(),
                 entrance = viewDataBinding.fragmentOrderEtEntrance.text.toString(),
                 intercom = viewDataBinding.fragmentOrderEtIntercom.text.toString(),
                 floor = viewDataBinding.fragmentOrderEtFloor.text.toString(),
                 comment = viewDataBinding.fragmentOrderEtComment.text.toString(),
                 phone = viewDataBinding.fragmentOrderEtPhone.text.toString()
             )
         )*/

        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
    }

    override fun goToMain(order: Order) {
        (activity as MainActivity).showMessage("Код заказа ${order.uuid}")
        findNavController().navigate(backToMainFragment())
    }

    override fun goToCart(view: View) {
        findNavController().navigate(backToCartFragment())
    }

    override fun goToCreationAddress() {
        findNavController().navigate(toCreationAddressFragment())
    }
}