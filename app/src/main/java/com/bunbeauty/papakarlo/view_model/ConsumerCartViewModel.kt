package com.bunbeauty.papakarlo.view_model

import android.content.Context
import androidx.databinding.ObservableField
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import javax.inject.Inject

class ConsumerCartViewModel @Inject constructor() : BaseViewModel() {

    val cartProductsField = ObservableField<List<CartProduct>?>()

    fun setCartProducts(cartProductList: ArrayList<CartProduct>) {
        cartProductsField.set(cartProductList)
    }

}