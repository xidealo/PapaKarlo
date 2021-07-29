package com.bunbeauty.data.repository

import com.bunbeauty.common.Constants
import com.bunbeauty.common.Constants.ADDRESSES
import com.bunbeauty.common.Constants.COMPANY
import com.bunbeauty.common.Constants.DELIVERY
import com.bunbeauty.common.Constants.L_ORDERS
import com.bunbeauty.common.Constants.MENU_PRODUCTS
import com.bunbeauty.common.Constants.ORDERS
import com.bunbeauty.common.Constants.USERS
import com.bunbeauty.data.BuildConfig
import com.bunbeauty.domain.model.firebase.CafeAddressFirebase
import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.firebase.order.OrderFirebase
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.model.ui.MenuProduct
import com.bunbeauty.domain.model.ui.cafe.Cafe
import com.bunbeauty.domain.model.ui.order.UserOrder
import com.bunbeauty.domain.repo.ApiRepo
import com.google.firebase.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ApiRepository @Inject constructor(private val firebaseDatabase: FirebaseDatabase) : ApiRepo {

    override fun postOrder(orderFirebase: OrderFirebase, cafeUuid: String): String {
        val ordersReference = firebaseDatabase.getReference(ORDERS)
            .child(BuildConfig.APP_ID)
            .child(cafeUuid)
        val orderUuid = getNewKey(ordersReference)

        ordersReference.child(orderUuid).setValue(orderFirebase)
        orderFirebase.orderEntity.userUuid?.let { userUuid ->
            postUserOrder(userUuid, cafeUuid, orderUuid)
        }
        return orderUuid
    }

    private fun postUserOrder(userUuid: String, cafeUuid: String, orderUuid: String) {
        val userOrdersReference = firebaseDatabase.getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userUuid)
            .child(L_ORDERS)
        val userOrderUuid = getNewKey(userOrdersReference)

        val userOrder = UserOrder(cafeUuid = cafeUuid, orderUuid = orderUuid)
        userOrdersReference.child(userOrderUuid).setValue(userOrder)
    }

    override fun insert(userFirebase: UserFirebase, userId: String) {
        val userReference = firebaseDatabase
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
        val userReference = firebaseDatabase
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

    override fun insertToBonusList(userFirebase: UserFirebase, userId: String) {
        val userReference = firebaseDatabase
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)

        val items = HashMap<String, Any>()
        items["bonusList"] = userFirebase.bonusList
        userReference.updateChildren(items)
    }

    override fun insert(addressFirebase: CafeAddressFirebase, userId: String): String {
        val addressUuid = firebaseDatabase.getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)
            .push()
            .key!!

        val addressReference = firebaseDatabase
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
        val cafeReference = this@ApiRepository.firebaseDatabase
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
        val menuProductsReference = this@ApiRepository.firebaseDatabase
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
        val deliveryReference = this@ApiRepository.firebaseDatabase
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
        val userReference = this@ApiRepository.firebaseDatabase
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

            override fun onCancelled(error: DatabaseError) {}
        }
        userReference.addListenerForSingleValueEvent(valueEventListener)
        awaitClose { userReference.removeEventListener(valueEventListener) }
    }

    @ExperimentalCoroutinesApi
    override fun getUserBonusList(userId: String): Flow<List<Int>> = callbackFlow {
        val bonusReference = this@ApiRepository.firebaseDatabase
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userId)
            .child("bonusList")
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    val bonusList = snapshot.children.map { cafeSnapshot ->
                        cafeSnapshot.getValue(Int::class.java)!!
                    }
                    trySend(bonusList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        bonusReference.addValueEventListener(valueEventListener)
        awaitClose { bonusReference.removeEventListener(valueEventListener) }
    }

    @ExperimentalCoroutinesApi
    override fun getOrder(cafeId: String, orderId: String): Flow<OrderFirebase?> = callbackFlow {
        val orderReference = this@ApiRepository.firebaseDatabase
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

    @ExperimentalCoroutinesApi
    override fun getOrderWithSubscribe(cafeId: String, orderId: String): Flow<OrderFirebase?> =
        callbackFlow {
            val orderReference = this@ApiRepository.firebaseDatabase
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
            orderReference.addValueEventListener(valueEventListener)
            awaitClose { orderReference.removeEventListener(valueEventListener) }
        }

    fun getNewKey(reference: DatabaseReference): String {
        return reference.push().key!!
    }

}