package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cafeRepo: CafeRepo
) : BaseViewModel() {

    var navigator: WeakReference<MainNavigator>? = null

    @Inject
    lateinit var dataStoreHelper: IDataStoreHelper

    val cartLiveData by lazy {
        Transformations.map(cartProductListLiveData) { productList ->
            "${productList.sumBy { getFullPrice(it) }} ₽\n${productList.sumBy { it.count }} шт."
        }
    }

    private fun getFullPrice(cartProduct: CartProduct): Int {
        return cartProduct.menuProduct.cost * cartProduct.count
    }

    fun refreshCafeList() {
        viewModelScope.launch(Dispatchers.IO) {
            cafeRepo.refreshCafeList()
        }
    }

    fun getDiscounts() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    /* Uploading menu products to FB
    fun saveMenu(productList: List<String>) {
        viewModelScope.launch {
            val menuRef = FirebaseDatabase.getInstance()
                .getReference("COMPANY")
                .child(BuildConfig.APP_ID)
                .child(MenuProduct.MENU_PRODUCTS)
            val productList = productList.mapIndexed { i, str ->
                val prodFieldList = str.split(";")

                val prod = MenuProductFB(
                    name = prodFieldList[1],
                    cost = prodFieldList[4].toInt(),
                    weight = prodFieldList[3].toInt(),
                    description = prodFieldList[5],
                    photoLink = prodFieldList[6],
                    productCode = ProductCode.valueOf(prodFieldList[2]),
                    barcode = prodFieldList[0].toInt()
                )

                menuRef.child("-$i").setValue(prod)
            }
        }
    }*/
}