package com.bunbeauty.papakarlo.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.bunbeauty.papakarlo.data.model.Address
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHelper @Inject constructor(context: Context) : IDataStoreHelper {

    private val selectedDeliveryAddressDataStore = context.createDataStore(SELECTED_DELIVERY_ADDRESS_DATA_STORE)
    private val selectedPickupAddressDataStore = context.createDataStore(SELECTED_PICKUP_ADDRESS_DATA_STORE)

    override val selectedDeliveryAddress: Flow<Address> = selectedDeliveryAddressDataStore.data.map {
        Address(
            it[ID_KEY] ?: DEFAULT_LONG,
            it[STREET_KEY] ?: DEFAULT_STRING,
            it[HOUSE_KEY] ?: DEFAULT_STRING,
            it[FLAT_KEY] ?: DEFAULT_STRING,
            it[ENTRANCE_KEY] ?: DEFAULT_STRING,
            it[INTERCOM_KEY] ?: DEFAULT_STRING,
            it[FLOOR_KEY] ?: DEFAULT_STRING
        )
    }

    override suspend fun saveSelectedDeliveryAddress(address: Address) {
        selectedDeliveryAddressDataStore.edit {
            it[ID_KEY] = address.id
            it[STREET_KEY] = address.street
            it[HOUSE_KEY] = address.house
            it[FLAT_KEY] = address.flat
            it[ENTRANCE_KEY] = address.entrance
            it[INTERCOM_KEY] = address.intercom
            it[FLOOR_KEY] = address.floor
        }
    }

    override val selectedPickupAddress: Flow<Address> = selectedPickupAddressDataStore.data.map {
        Address(
            it[ID_KEY] ?: DEFAULT_LONG,
            it[STREET_KEY] ?: DEFAULT_STRING,
            it[HOUSE_KEY] ?: DEFAULT_STRING,
            it[FLAT_KEY] ?: DEFAULT_STRING,
            it[ENTRANCE_KEY] ?: DEFAULT_STRING,
            it[INTERCOM_KEY] ?: DEFAULT_STRING,
            it[FLOOR_KEY] ?: DEFAULT_STRING
        )
    }

    override suspend fun saveSelectedPickupAddress(address: Address) {
        selectedPickupAddressDataStore.edit {
            it[ID_KEY] = address.id
            it[STREET_KEY] = address.street
            it[HOUSE_KEY] = address.house
            it[FLAT_KEY] = address.flat
            it[ENTRANCE_KEY] = address.entrance
            it[INTERCOM_KEY] = address.intercom
            it[FLOOR_KEY] = address.floor
        }
    }

    companion object {
        //ADDRESS
        private const val SELECTED_DELIVERY_ADDRESS_DATA_STORE = "selected delivery address data store"
        private const val SELECTED_PICKUP_ADDRESS_DATA_STORE = "selected pickup address data store"
        private const val ID_ADDRESS = "id"
        private const val STREET = "street"
        private const val HOUSE = "house"
        private const val FLAT = "flat"
        private const val ENTRANCE = "entrance"
        private const val INTERCOM = "intercom"
        private const val FLOOR = "floor"
        private val ID_KEY = longPreferencesKey(ID_ADDRESS)
        private val STREET_KEY = stringPreferencesKey(STREET)
        private val HOUSE_KEY = stringPreferencesKey(HOUSE)
        private val FLAT_KEY = stringPreferencesKey(FLAT)
        private val ENTRANCE_KEY = stringPreferencesKey(ENTRANCE)
        private val INTERCOM_KEY = stringPreferencesKey(INTERCOM)
        private val FLOOR_KEY = stringPreferencesKey(FLOOR)

        private const val DEFAULT_STRING = ""
        private const val DEFAULT_DOUBLE = 0.0
        private const val DEFAULT_LONG = 0L
    }
}