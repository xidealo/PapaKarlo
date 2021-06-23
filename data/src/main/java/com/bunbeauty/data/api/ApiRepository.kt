package com.bunbeauty.data.api

import com.bunbeauty.common.Constants
import com.bunbeauty.common.Constants.ADDRESSES
import com.bunbeauty.common.Constants.COMPANY
import com.bunbeauty.common.Constants.DELIVERY
import com.bunbeauty.common.Constants.MENU_PRODUCTS
import com.bunbeauty.common.Constants.ORDERS
import com.bunbeauty.common.Constants.USERS
import com.bunbeauty.common.Constants.l_ORDERS
import com.bunbeauty.data.BuildConfig
import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.model.firebase.AddressFirebase
import com.bunbeauty.data.model.firebase.OrderFirebase
import com.bunbeauty.data.model.firebase.UserFirebase
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.model.order.UserOrder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue.TIMESTAMP
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ApiRepository @Inject constructor() : IApiRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + IO

    private val testFirebaseInstance =
        FirebaseDatabase.getInstance("https://test-fooddelivery.firebaseio.com")
    private val firebaseInstance = testFirebaseInstance

    override fun insert(orderFirebase: OrderFirebase, cafeId: String): String {
        val orderUuid = firebaseInstance.getReference(ORDERS)
            .child(BuildConfig.APP_ID)
            .push()
            .key!!

        val orderReference = firebaseInstance
            .getReference(ORDERS)
            .child(BuildConfig.APP_ID)
            .child(cafeId)
            .child(orderUuid)
        orderReference.setValue(orderFirebase)
        if (orderFirebase.orderEntity.userId != null) {
            addOrderToUser(orderFirebase, cafeId, orderUuid)
        }
        return orderUuid
    }

    private fun addOrderToUser(orderFirebase: OrderFirebase, cafeId: String, orderUuid: String) {
        val getOrderUserReference = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(orderFirebase.orderEntity.userId!!)
            .child(l_ORDERS)

        val orderToUserReference = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(orderFirebase.orderEntity.userId!!)
            .child(l_ORDERS)
        getOrderUserReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userOrderList = mutableListOf<UserOrder>()
                snapshot.children.forEach {
                    userOrderList.add(it.getValue(UserOrder::class.java) ?: UserOrder("", ""))
                }
                userOrderList.add(UserOrder(cafeId, orderUuid))
                orderToUserReference.setValue(userOrderList)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun insert(userFirebase: UserFirebase, userId: String) {
        val userReference = testFirebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)

        val items = HashMap<String, Any>()
        items["phone"] = userFirebase.phone

        if (userFirebase.email.isNotEmpty())
            items["email"] = userFirebase.email
        userReference.updateChildren(items)
    }

    override fun update(userFirebase: UserFirebase, userId: String) {
        val userReference = testFirebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)
        userReference.setValue(userFirebase)
    }

    override fun insertToBonusList(userFirebase: UserFirebase, userId: String) {
        val userReference = testFirebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)

        val items = HashMap<String, Any>()
        items["bonusList"] = userFirebase.bonusList
        userReference.updateChildren(items)
    }

    override fun insert(addressFirebase: AddressFirebase, userId: String): String {
        val addressUuid = testFirebaseInstance.getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)
            .push()
            .key!!

        val addressReference = testFirebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)
            .child(ADDRESSES)
            .child(addressUuid)
        addressReference.setValue(addressFirebase)

        return addressUuid
    }

    @ExperimentalCoroutinesApi
    override fun getCafeList(): Flow<List<Cafe>> = callbackFlow {
        val cafeReference = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(Constants.CAFES)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cafeList = snapshot.children.map { cafeSnapshot ->
                    cafeSnapshot.getValue(Cafe::class.java)!!
                }
                trySend(cafeList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        cafeReference.addListenerForSingleValueEvent(valueEventListener)

        awaitClose { cafeReference.removeEventListener(valueEventListener) }
    }

    @ExperimentalCoroutinesApi
    override fun getMenuProductList(): Flow<List<MenuProduct>> = callbackFlow {
        val menuProductsReference = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(MENU_PRODUCTS)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val menuProductList = snapshot.children.map { menuProductSnapshot ->
                    menuProductSnapshot.getValue(MenuProduct::class.java)!!
                        .also { it.uuid = menuProductSnapshot.key!! }
                }
                trySend(menuProductList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        menuProductsReference.addListenerForSingleValueEvent(valueEventListener)

        awaitClose { menuProductsReference.removeEventListener(valueEventListener) }
    }

    @ExperimentalCoroutinesApi
    override fun getDelivery(): Flow<Delivery> = callbackFlow {
        val deliveryReference = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(DELIVERY)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    trySend(snapshot.getValue(Delivery::class.java)!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        deliveryReference.addListenerForSingleValueEvent(valueEventListener)
        awaitClose { deliveryReference.removeEventListener(valueEventListener) }
    }

    @ExperimentalCoroutinesApi
    override fun getUser(userId: String): Flow<UserFirebase?> = callbackFlow {
        val userReference = firebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    trySend(snapshot.getValue(UserFirebase::class.java))
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        userReference.addListenerForSingleValueEvent(valueEventListener)
        awaitClose { userReference.removeEventListener(valueEventListener) }
    }

    @ExperimentalCoroutinesApi
    override fun getOrder(cafeId: String, orderId: String): Flow<OrderFirebase?> = callbackFlow {
        val orderReference = firebaseInstance
            .getReference(ORDERS)
            .child(BuildConfig.APP_ID)
            .child(cafeId)
            .child(orderId)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    trySend(snapshot.getValue(OrderFirebase::class.java))
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        orderReference.addListenerForSingleValueEvent(valueEventListener)
        awaitClose { orderReference.removeEventListener(valueEventListener) }
    }

}