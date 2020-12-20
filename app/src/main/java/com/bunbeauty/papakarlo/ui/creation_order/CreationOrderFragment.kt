package com.bunbeauty.papakarlo.ui.creation_order

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderFragmentDirections.actionCreationOrderToCartFragment
import com.bunbeauty.papakarlo.view_model.CreationOrderViewModel
import java.lang.ref.WeakReference

class CreationOrderFragment : TopBarFragment<FragmentCreationOrderBinding, CreationOrderViewModel>(),
    CreationOrderNavigator {

    override var title: String = "Оформление заказа"
    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_creation_order
    override var viewModelClass = CreationOrderViewModel::class.java

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)

        viewDataBinding.fragmentCreationOrderBtnOrder.setOnClickListener {
            createOrder()
        }
    }

    override fun goToCart(view: View) {
        findNavController().navigate(actionCreationOrderToCartFragment())
    }

    private fun createOrder() {
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

    companion object {
        const val TAG = "CreationOrderFragment"
    }
}