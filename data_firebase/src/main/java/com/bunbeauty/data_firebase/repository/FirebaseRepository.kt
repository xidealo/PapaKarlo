package com.bunbeauty.data_firebase.repository

import com.bunbeauty.common.Constants.ADDRESSES
import com.bunbeauty.common.Constants.CAFES
import com.bunbeauty.common.Constants.COMPANY
import com.bunbeauty.common.Constants.DELIVERY
import com.bunbeauty.common.Constants.EMAIL
import com.bunbeauty.common.Constants.L_ORDERS
import com.bunbeauty.common.Constants.MENU_PRODUCTS
import com.bunbeauty.common.Constants.ORDERS
import com.bunbeauty.common.Constants.USERS
import com.bunbeauty.data_firebase.BuildConfig
import com.bunbeauty.data_firebase.extensions.getValue
import com.bunbeauty.data_firebase.extensions.getValueList
import com.bunbeauty.data_firebase.extensions.getValueMap
import com.example.domain_firebase.model.firebase.MenuProductFirebase
import com.example.domain_firebase.model.firebase.address.UserAddressFirebase
import com.example.domain_firebase.model.firebase.cafe.CafeFirebase
import com.example.domain_firebase.model.firebase.cafe.DeliveryFirebase
import com.example.domain_firebase.model.firebase.order.OrderFirebase
import com.example.domain_firebase.model.firebase.order.UserOrderFirebase
import com.example.domain_firebase.repo.FirebaseRepo
import com.google.firebase.database.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseDatabase: FirebaseDatabase) : FirebaseRepo {

    val orderObserverMap: MutableMap<DatabaseReference, ValueEventListener> = mutableMapOf()

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

        val userOrder = UserOrderFirebase(cafeUuid = cafeUuid, orderUuid = orderUuid)
        userOrdersReference.child(userOrderUuid).setValue(userOrder)
    }

    override fun postUser(userUuid: String, userFirebase: com.example.domain_firebase.model.firebase.UserFirebase) {
        val userReference = firebaseDatabase
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userUuid)
        userReference.setValue(userFirebase)
    }

    override fun postUserAddress(userAddress: UserAddressFirebase, userUuid: String): String {
        val addressesReference = firebaseDatabase.getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userUuid)
            .child(ADDRESSES)
        val addressUuid = getNewKey(addressesReference)
        addressesReference.child(addressUuid).setValue(userAddress)

        return addressUuid
    }

    override fun updateUserEmail(userUuid: String, userEmail: String) {
        val userReference = firebaseDatabase
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userUuid)
            .child(EMAIL)
        userReference.setValue(userEmail)
    }

    @ExperimentalCoroutinesApi
    override fun getCafeList(): Flow<List<CafeFirebase>> {
        val childSnapshot = firebaseDatabase
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(CAFES)
        return childSnapshot.getValueList()
    }

    @ExperimentalCoroutinesApi
    override fun getMenuProductMap(): Flow<Map<String, MenuProductFirebase>> {
        val menuProductsReference = firebaseDatabase
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(MENU_PRODUCTS)
        return menuProductsReference.getValueMap()
    }

    @ExperimentalCoroutinesApi
    override fun getDelivery(): Flow<DeliveryFirebase?> {
        val deliveryReference = firebaseDatabase
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(DELIVERY)
        return deliveryReference.getValue()
    }

    @ExperimentalCoroutinesApi
    override fun getUser(userUuid: String): Flow<com.example.domain_firebase.model.firebase.UserFirebase?> {
        val userReference = firebaseDatabase
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(USERS)
            .child(userUuid)
        return userReference.getValue<com.example.domain_firebase.model.firebase.UserFirebase>()
    }

    @ExperimentalCoroutinesApi
    override fun getOrder(userOrderFirebase: UserOrderFirebase): Flow<OrderFirebase?> {
        val orderReference = firebaseDatabase
            .getReference(ORDERS)
            .child(BuildConfig.APP_ID)
            .child(userOrderFirebase.cafeUuid)
            .child(userOrderFirebase.orderUuid)
        return orderReference.getValue<OrderFirebase>()
    }

    override fun removeOrderObservers() {
        orderObserverMap.entries.forEach { mapEntry ->
            mapEntry.key.removeEventListener(mapEntry.value)
        }
        orderObserverMap.clear()
    }

    @ExperimentalCoroutinesApi
    override fun observeOrder(userOrderFirebase: UserOrderFirebase): Flow<OrderFirebase?> =
        callbackFlow {
            val orderReference = firebaseDatabase
                .getReference(ORDERS)
                .child(BuildConfig.APP_ID)
                .child(userOrderFirebase.cafeUuid)
                .child(userOrderFirebase.orderUuid)
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.getValue(OrderFirebase::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            orderObserverMap[orderReference] = listener
            orderReference.addValueEventListener(listener)
            awaitClose { orderReference.removeEventListener(listener) }
        }

    fun getNewKey(reference: DatabaseReference): String {
        return reference.push().key!!
    }

    /*@Deprecated("Bonuses deprecated")
    @ExperimentalCoroutinesApi
    fun getUserBonusList(userId: String): Flow<List<Int>> = callbackFlow {
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
    }*/

}