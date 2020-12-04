package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import javax.inject.Inject

class ConsumerCartViewModel @Inject constructor() : BaseViewModel() {

    val cartProductsField = ObservableField<List<CartProduct>?>()

    fun setCartProducts(menuProductList: ArrayList<MenuProduct>) {
        val setOfMenuProduct = mutableSetOf<MenuProduct>()

        setOfMenuProduct.addAll(menuProductList)
        val cartProductList = arrayListOf<CartProduct>()
        for (menuProduct in setOfMenuProduct) {
            cartProductList.add(
                CartProduct(
                    menuProduct = menuProduct,
                    count = menuProductList.count { it.name == menuProduct.name },
                    discount = 0.1f,
                    fullPrice = menuProductList.count { it.name == menuProduct.name } * menuProduct.cost
                )
            )
        }
        cartProductsField.set(cartProductList)
    }

}