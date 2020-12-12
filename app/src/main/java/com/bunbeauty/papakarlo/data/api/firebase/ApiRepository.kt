package com.bunbeauty.papakarlo.data.api.firebase

import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.order.Order
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class ApiRepository @Inject constructor() : IApiRepository {

    private val firebaseInstance = FirebaseDatabase.getInstance()

    override fun insertOrder(order: Order): String {

        order.uuid = firebaseInstance.getReference(Order.ORDERS).push().key!!

        val orderRef = firebaseInstance
            .getReference(Order.ORDERS)
            .child(order.uuid)

        val orderItems = HashMap<String, Any>()
        orderItems[Order.STREET] = order.street
        orderItems[Order.HOUSE] = order.house
        orderItems[Order.FLAT] = order.flat
        orderItems[Order.ENTRANCE] = order.entrance
        orderItems[Order.INTERCOM] = order.intercom
        orderItems[Order.FLOOR] = order.floor
        orderItems[Order.COMMENT] = order.comment
        orderItems[Order.PHONE] = order.phone
        orderRef.updateChildren(orderItems)
        return order.uuid
    }

    override fun insertCartProduct(cartProduct: CartProduct): String {
        cartProduct.uuid = firebaseInstance.getReference(Order.ORDERS).push().key!!

        val cartProductRef = firebaseInstance
            .getReference(Order.ORDERS)
            .child(cartProduct.orderUuid)
            .child(CartProduct.CART_PRODUCTS)
            .child(cartProduct.uuid)

        val cartProductItems = HashMap<String, Any>()
        cartProductItems[CartProduct.COUNT] = cartProduct.count
        cartProductItems[CartProduct.DISCOUNT] = cartProduct.discount
        cartProductItems[MenuProduct.NAME] = cartProduct.menuProduct.name
        cartProductItems[MenuProduct.COST] = cartProduct.menuProduct.cost
        cartProductItems[MenuProduct.GRAM] = cartProduct.menuProduct.gram
        cartProductItems[MenuProduct.DESCRIPTION] = cartProduct.menuProduct.description
        cartProductItems[MenuProduct.PHOTO_LINK] = cartProduct.menuProduct.photoLink
        cartProductItems[MenuProduct.PRODUCT_CODE] = cartProduct.menuProduct.productCode
        cartProductRef.updateChildren(cartProductItems)
        return cartProduct.uuid
    }


}