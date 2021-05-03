package com.bunbeauty.data.api

import com.bunbeauty.common.Constants
import com.bunbeauty.common.Constants.ADDRESSES
import com.bunbeauty.common.Constants.COMPANY
import com.bunbeauty.common.Constants.DELIVERY
import com.bunbeauty.common.Constants.MENU_PRODUCTS
import com.bunbeauty.data.BuildConfig
import com.bunbeauty.data.model.Delivery
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.model.firebase.AddressFirebase
import com.bunbeauty.data.model.firebase.OrderFirebase
import com.bunbeauty.data.model.firebase.UserFirebase
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

    private val firebaseInstance = FirebaseDatabase.getInstance()
    private val testFirebaseInstance = FirebaseDatabase.getInstance("https://test-fooddelivery.firebaseio.com")

    override fun insert(orderFirebase: OrderFirebase, cafeId: String): String {
        val orderUuid = firebaseInstance.getReference(Constants.ORDERS)
            .child(BuildConfig.APP_ID)
            .push()
            .key!!
        orderFirebase.timestamp = TIMESTAMP

        val orderReference = firebaseInstance
            .getReference(Constants.ORDERS)
            .child(BuildConfig.APP_ID)
            .child(cafeId)
            .child(orderUuid)
        orderReference.setValue(orderFirebase)

        return orderUuid
    }

    override fun insert(userFirebase: UserFirebase, userId: String) {
        val userReference = testFirebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(Constants.USERS)
            .child(userId)
        userReference.setValue(userFirebase)
    }

    override fun insert(addressFirebase: AddressFirebase, userId: String) :String{
        val addressUuid = testFirebaseInstance.getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(Constants.USERS)
            .child(userId)
            .push()
            .key!!

        val addressReference = testFirebaseInstance
            .getReference(COMPANY)
            .child(BuildConfig.APP_ID)
            .child(Constants.USERS)
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
                offer(cafeList)
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
                offer(menuProductList)
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
                    offer(snapshot.getValue(Delivery::class.java)!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        deliveryReference.addListenerForSingleValueEvent(valueEventListener)
        awaitClose { deliveryReference.removeEventListener(valueEventListener) }
    }
}