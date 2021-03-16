package com.bunbeauty.papakarlo.data.api.firebase

import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.data.model.Delivery
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue.TIMESTAMP
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ApiRepository @Inject constructor() : IApiRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + IO

    private val firebaseInstance = FirebaseDatabase.getInstance()

    override fun insertOrder(order: Order) {
        val orderUuid = firebaseInstance.getReference(OrderEntity.ORDERS)
            .child(BuildConfig.APP_ID)
            .push()
            .key!!
        order.timestamp = TIMESTAMP

        val orderReference = firebaseInstance
            .getReference(OrderEntity.ORDERS)
            .child(BuildConfig.APP_ID)
            .child(order.cafeId)
            .child(orderUuid)
        orderReference.setValue(order)
    }

    @ExperimentalCoroutinesApi
    override fun getCafeList(): SharedFlow<List<Cafe>> {
        val cafeListSharedFlow = MutableSharedFlow<List<Cafe>>()
        val contactInfoRef = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(Cafe.CAFES)

        contactInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cafeList = snapshot.children.map { cafeSnapshot ->
                    cafeSnapshot.getValue(Cafe::class.java)!!
                }
                launch(IO) {
                    cafeListSharedFlow.emit(cafeList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return cafeListSharedFlow
    }

    override fun getMenuProductList(): Flow<List<MenuProduct>> {
        val menuProductListSharedFlow = MutableSharedFlow<List<MenuProduct>>()
        val menuProductsRef = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(MENU_PRODUCTS)

        menuProductsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val menuProductList = snapshot.children.map { menuProductSnapshot ->
                    menuProductSnapshot.getValue(MenuProduct::class.java)!!.also { it.uuid = menuProductSnapshot.key!! }

                }
                launch(IO) {
                    menuProductListSharedFlow.emit(menuProductList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return menuProductListSharedFlow
    }

    override fun getDeliveryCost(): Flow<Delivery> {
        val cafeListSharedFlow = MutableSharedFlow<Delivery>()
        val deliveryCostReference = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(DELIVERY)

        deliveryCostReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch(IO) {
                    cafeListSharedFlow.emit(snapshot.getValue(Delivery::class.java)!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return cafeListSharedFlow
    }

    companion object {
        private const val COMPANY = "COMPANY"
        private const val MENU_PRODUCTS: String = "menu_products"
        private const val DELIVERY: String = "delivery"
        private const val DISCOUNTS: String = "discounts"
    }
}