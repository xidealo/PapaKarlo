package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.ui.ConsumerCartNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ConsumerCartViewModel @Inject constructor() : BaseViewModel() {
    lateinit var consumerCartNavigator: WeakReference<ConsumerCartNavigator>

    val cartProductsField = ObservableField<List<CartProduct>?>()

    fun setCartProducts(cartProductList: ArrayList<CartProduct>) {
        cartProductsField.set(cartProductList)
    }

    fun goToOrderClick(){
        consumerCartNavigator.get()?.goToOrder()
    }

}