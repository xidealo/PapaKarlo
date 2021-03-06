package com.bunbeauty.papakarlo.data.api.firebase

import android.util.Log
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.data.local.db.discount.DiscountRepo
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.data.model.discount.Discount
import com.bunbeauty.papakarlo.data.model.order.OrderEntity
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.enums.ProductCode
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue.TIMESTAMP
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ApiRepository @Inject constructor(
    private val menuProductRepo: MenuProductRepo
) : IApiRepository, CoroutineScope {

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

    //TODO(refactor to repository call)
    override fun getMenuProductList() {
        val menuProductsRef = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(MENU_PRODUCTS)

        menuProductsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {

                    val menuProduct = snapshot.getValue(MenuProduct::class.java)!!
                    menuProduct.uuid = snapshot.key!!
                    launch {
                        menuProductRepo.insert(menuProduct)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun getDiscounts(): SharedFlow<List<Discount>> {
        val discountsSharedFlow = MutableSharedFlow<List<Discount>>()

        val discountsRef = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(DISCOUNTS)

        discountsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val discountList = mutableListOf<Discount>()
                for (snapshot in dataSnapshot.children) {
                    val discount = snapshot.getValue(Discount::class.java)!!
                    discount.discountEntity.discountEntityUuid = snapshot.key!!
                    discountList.add(discount)
                }
                launch(IO) {
                    discountsSharedFlow.emit(discountList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return discountsSharedFlow
    }

    companion object {
        private const val COMPANY = "COMPANY"
        private const val MENU_PRODUCTS: String = "menu_products"
        private const val DISCOUNTS: String = "discounts"
    }
}