package com.bunbeauty.papakarlo.data.api.firebase

import android.util.Log
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.ContactInfo
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.utils.contact_info.IContactInfoHelper
import com.google.firebase.database.*
import com.google.firebase.database.ServerValue.TIMESTAMP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ApiRepository @Inject constructor(
    private val contactInfoHelper: IContactInfoHelper,
    private val dataStoreHelper: IDataStoreHelper,
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
        orderItems[Order.STREET] = order.address.street
        orderItems[Order.HOUSE] = order.address.house
        orderItems[Order.FLAT] = order.address.flat
        orderItems[Order.ENTRANCE] = order.address.entrance
        orderItems[Order.INTERCOM] = order.address.intercom
        orderItems[Order.FLOOR] = order.address.floor
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

    override fun getContactInfo() {
        val contactInfoRef = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(ContactInfo.CONTACT_INFO)

        contactInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val contactInfo = contactInfoHelper.getContactInfoFromSnapshot(snapshot)
                launch {
                    dataStoreHelper.saveContactInfo(contactInfo)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
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