package com.bunbeauty.papakarlo.ui.creation_order

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.Order
import com.bunbeauty.papakarlo.databinding.FragmentCreationOrderBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.OrderViewModel
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.WeakReference

class CreationOrderFragment : BaseFragment<FragmentCreationOrderBinding, OrderViewModel>(),
    CreationOrderNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_creation_order
    override var viewModelClass = OrderViewModel::class.java

    lateinit var cartProducts: ArrayList<CartProduct>

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cartProducts = it.getParcelableArrayList(CartProduct.CART_PRODUCT)!!
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setTitle("Оформление заказа")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.creationOrderNavigator = WeakReference(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun createOrder() {
        viewModel.createOrder(
            Order(
                street = viewDataBinding.fragmentOrderEtStreet.text.toString(),
                house = viewDataBinding.fragmentOrderEtHouse.text.toString(),
                flat = viewDataBinding.fragmentOrderEtFlat.text.toString(),
                entrance = viewDataBinding.fragmentOrderEtEntrance.text.toString(),
                intercom = viewDataBinding.fragmentOrderEtIntercom.text.toString(),
                floor = viewDataBinding.fragmentOrderEtFloor.text.toString(),
                comment = viewDataBinding.fragmentOrderEtComment.text.toString(),
                phone = viewDataBinding.fragmentOrderEtPhone.text.toString(),
                cartProducts = cartProducts
            )
        )
    }

    companion object {
        const val TAG = "CreationOrderFragment"

        @JvmStatic
        fun newInstance(cartProducts: ArrayList<CartProduct>) =
            CreationOrderFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CartProduct.CART_PRODUCT, cartProducts)
                }
            }
    }
}