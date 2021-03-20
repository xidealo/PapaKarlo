package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.data.local.db.delivery.DeliveryRepo
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.utils.product.IProductHelper
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cafeRepo: CafeRepo,
    private val deliveryRepo: DeliveryRepo,
    private val menuProductRepo: MenuProductRepo,
    private val productHelper: IProductHelper
) : BaseViewModel() {

    var navigator: WeakReference<MainNavigator>? = null

    @Inject
    lateinit var dataStoreHelper: IDataStoreHelper

    val cartLiveData by lazy {
        map(cartProductListLiveData) { productList ->
            productHelper.getFullPriceString(productList) + "\n${productList.sumBy { it.count }} шт."
        }
    }

    fun refreshCafeList() {
        viewModelScope.launch(IO) {
            cafeRepo.refreshCafeList()
        }
    }

    fun refreshDeliveryCost() {
        viewModelScope.launch(IO) {
            deliveryRepo.refreshDeliveryCost()
        }
    }

    fun refreshMenuProducts() {
        viewModelScope.launch(IO) {
            menuProductRepo.getMenuProductRequest()
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