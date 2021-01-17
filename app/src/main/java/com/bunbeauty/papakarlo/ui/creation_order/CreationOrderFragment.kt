package com.bunbeauty.papakarlo.ui.creation_order

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.backToCartFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.backToMainFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_order)

        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        viewDataBinding.fragmentCreationOrderBtnOrder.setOnClickListener {
            createOrder()
        }
        val phoneTextWatcher = PhoneTextWatcher(viewDataBinding.fragmentOrderEtPhone)
        viewDataBinding.fragmentOrderEtPhone.addTextChangedListener(phoneTextWatcher)
    }

    private fun createOrder() {
        if (!(activity as MainActivity).viewModel.isNetworkConnected) {
            (activity as MainActivity).showError("Нет подключения к интернету")
            return
        }

        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtStreet.text.toString(),
                true,
                50
            )) {
            viewDataBinding.fragmentOrderEtStreet.error =
                resources.getString(R.string.error_creation_order_street)
            viewDataBinding.fragmentOrderEtStreet.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtHouse.text.toString(),
                true,
                5
            )) {
            viewDataBinding.fragmentOrderEtHouse.error =
                resources.getString(R.string.error_creation_order_house)
            viewDataBinding.fragmentOrderEtHouse.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtFlat.text.toString(),
                false,
                5
            )) {
            viewDataBinding.fragmentOrderEtFlat.error =
                resources.getString(R.string.error_creation_order_flat)
            viewDataBinding.fragmentOrderEtFlat.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtEntrance.text.toString(),
                false,
                5
            )) {
            viewDataBinding.fragmentOrderEtEntrance.error =
                resources.getString(R.string.error_creation_order_entrance)
            viewDataBinding.fragmentOrderEtEntrance.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtIntercom.text.toString(),
                false,
                5
            )) {
            viewDataBinding.fragmentOrderEtIntercom.error =
                resources.getString(R.string.error_creation_order_intercom)
            viewDataBinding.fragmentOrderEtIntercom.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtFloor.text.toString(),
                false,
                5
            )) {
            viewDataBinding.fragmentOrderEtFloor.error =
                resources.getString(R.string.error_creation_order_floor)
            viewDataBinding.fragmentOrderEtFloor.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentOrderEtComment.text.toString(),
                false,
                100
            )) {
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
            )) {
            viewDataBinding.fragmentOrderEtPhone.error =
                resources.getString(R.string.error_creation_order_phone)
            viewDataBinding.fragmentOrderEtPhone.requestFocus()
            return
        }

        viewModel.createOrder(
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
        )

        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
    }

    override fun goToMain(order: Order) {
        (activity as MainActivity).showMessage("Код заказа ${order.uuid}")
        findNavController().navigate(backToMainFragment())
    }

    override fun goToCart(view: View) {
        findNavController().navigate(backToCartFragment())
    }
}