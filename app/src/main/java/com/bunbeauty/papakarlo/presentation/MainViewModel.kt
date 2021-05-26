package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.domain.repo.DeliveryRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cafeRepo: CafeRepo,
    private val deliveryRepo: DeliveryRepo,
    private val userRepo: UserRepo,
    private val addressRepo: UserAddressRepo,
    private val orderRepo: OrderRepo,
    private val menuProductRepo: MenuProductRepo,
    private val dataStoreRepo: DataStoreRepo
) : BaseViewModel() {

    fun refreshCafeList() {
        viewModelScope.launch(IO) {
            cafeRepo.refreshCafeList()
        }
    }

    fun refreshMenuProducts() {
        viewModelScope.launch(IO) {
            menuProductRepo.getMenuProductRequest()
        }
    }

    fun refreshDeliveryInfo() {
        viewModelScope.launch(IO) {
            deliveryRepo.refreshDeliveryCost()
        }
    }

    fun refreshUserInfo() {
        viewModelScope.launch(IO) {
            val userId = dataStoreRepo.userId.first()
            userRepo.getUserFirebaseAsFlow(userId).onEach { userFirebase ->
                if (userFirebase != null) {
                    userRepo.insert(userFirebase, userId)
                    addressRepo.insert(userFirebase.addresses, userId)
                    orderRepo.loadOrders(userFirebase.orders)
                }
            }.launchIn(viewModelScope)
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