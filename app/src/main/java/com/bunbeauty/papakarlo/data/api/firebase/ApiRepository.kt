package com.bunbeauty.papakarlo.data.api.firebase

import android.util.Log
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
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

    override fun insertOrder(order: Order): String {
        order.uuid = firebaseInstance.getReference(Order.ORDERS).push().key!!

        val orderRef = firebaseInstance
            .getReference(Order.ORDERS)
            .child(BuildConfig.APP_ID)
            .child(order.uuid)

        val orderItems = HashMap<String, Any>()
        orderItems[Address.STREET] = order.address.street
        orderItems[Address.HOUSE] = order.address.house
        orderItems[Address.FLAT] = order.address.flat
        orderItems[Address.ENTRANCE] = order.address.entrance
        orderItems[Address.INTERCOM] = order.address.intercom
        orderItems[Address.FLOOR] = order.address.floor
        orderItems[Order.COMMENT] = order.comment
        orderItems[Order.PHONE] = order.phone
        orderItems[Order.TIMESTAMP] = TIMESTAMP
        orderItems[Order.ORDER_STATUS] = order.orderStatus
        orderRef.updateChildren(orderItems)

        return order.uuid
    }

    override fun insertCartProduct(cartProduct: CartProduct): String {
        cartProduct.uuid = firebaseInstance.getReference(Order.ORDERS).push().key!!

        val cartProductRef = firebaseInstance
            .getReference(Order.ORDERS)
            .child(BuildConfig.APP_ID)
            .child(cartProduct.orderUuid)
            .child(CartProduct.CART_PRODUCTS)
            .child(cartProduct.uuid)

        val cartProductItems = HashMap<String, Any>()
        cartProductItems[CartProduct.COUNT] = cartProduct.count
        cartProductItems[CartProduct.DISCOUNT] = cartProduct.discount
        cartProductItems[MenuProduct.NAME] = cartProduct.menuProduct.name
        cartProductItems[MenuProduct.COST] = cartProduct.menuProduct.cost
        cartProductItems[MenuProduct.WEIGHT] = cartProduct.menuProduct.weight
        cartProductItems[MenuProduct.DESCRIPTION] = cartProduct.menuProduct.description
        cartProductItems[MenuProduct.PHOTO_LINK] = cartProduct.menuProduct.photoLink
        cartProductItems[MenuProduct.PRODUCT_CODE] = cartProduct.menuProduct.productCode
        cartProductRef.updateChildren(cartProductItems)
        return cartProduct.uuid
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
                    cafeSnapshot.getValue(Cafe::class.java)!!.apply {
                        cafeEntity.id = cafeSnapshot.key!!
                    }
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

    override fun getMenuProductList() {
        val menuProductsRef = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(MenuProduct.MENU_PRODUCTS)

        menuProductsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {

                    val menuProduct = MenuProduct(
                        uuid = snapshot.key!!,
                        name = snapshot.child(MenuProduct.NAME).value.toString(),
                        cost = snapshot.child(MenuProduct.COST).value.toString().toInt(),
                        weight = snapshot.child(MenuProduct.WEIGHT).value.toString().toInt(),
                        description = snapshot.child(MenuProduct.DESCRIPTION).value.toString(),
                        photoLink = snapshot.child(MenuProduct.PHOTO_LINK).value.toString(),
                        onFire = snapshot.child(MenuProduct.ON_FIRE).value.toString().toBoolean(),
                        inOven = snapshot.child(MenuProduct.IN_OVEN).value.toString().toBoolean(),
                        productCode = ProductCode.valueOf(
                            snapshot.child(MenuProduct.PRODUCT_CODE).value.toString()
                        )
                    )
                    menuProductRepo.insert(menuProduct)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    companion object {
        private const val COMPANY = "COMPANY"
    }
}