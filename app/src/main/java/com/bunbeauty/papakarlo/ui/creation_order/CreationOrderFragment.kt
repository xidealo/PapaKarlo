package com.bunbeauty.papakarlo.ui.creation_order

import android.os.Bundle
import android.view.View
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
import com.bunbeauty.papakarlo.utils.ResourcesProvider
import com.bunbeauty.papakarlo.view_model.CreationOrderViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderFragment :
    CartClickableFragment<FragmentCreationOrderBinding, CreationOrderViewModel>(),
    CreationOrderNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_creation_order
    override var viewModelClass = CreationOrderViewModel::class.java

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

        if (viewDataBinding.fragmentOrderEtStreet.text.isNullOrEmpty()) {
            viewDataBinding.fragmentOrderEtStreet.error =
                resources.getString(R.string.error_creation_order_street)
            return
        }
        if (viewDataBinding.fragmentOrderEtHouse.text.isNullOrEmpty()) {
            viewDataBinding.fragmentOrderEtHouse.error =
                resources.getString(R.string.error_creation_order_house)
            return
        }
        if (viewDataBinding.fragmentOrderEtPhone.text.isNullOrEmpty()) {
            viewDataBinding.fragmentOrderEtPhone.error =
                resources.getString(R.string.error_creation_order_phone)
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
    }

    override fun goToMain(order: Order) {
        (activity as MainActivity).showMessage("Код заказа ${order.uuid}")
        findNavController().navigate(backToMainFragment())
    }

    override fun goToCart(view: View) {
        findNavController().navigate(backToCartFragment())
    }
}