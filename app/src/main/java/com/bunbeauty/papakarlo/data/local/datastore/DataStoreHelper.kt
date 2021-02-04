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

    override val selectedDeliveryAddress: Flow<Long> = selectedDeliveryAddressDataStore.data.map {
        it[ID_KEY] ?: DEFAULT_LONG
    }

    override suspend fun saveSelectedDeliveryAddress(addressId: Long) {
        selectedDeliveryAddressDataStore.edit {
            it[ID_KEY] = addressId
        }
    }

    override val selectedPickupAddress: Flow<Long> = selectedPickupAddressDataStore.data.map {
        it[ID_KEY] ?: DEFAULT_LONG
    }

    override suspend fun saveSelectedPickupAddress(addressId: Long) {
        selectedPickupAddressDataStore.edit {
            it[ID_KEY] = addressId
        }
    }

    override suspend fun clearData() {
        selectedDeliveryAddressDataStore.edit {
            it.clear()
        }
        selectedPickupAddressDataStore.edit {
            it.clear()
        }
    }

    companion object {
        //ADDRESS
        private const val SELECTED_DELIVERY_ADDRESS_DATA_STORE = "selected delivery address data store"
        private const val SELECTED_PICKUP_ADDRESS_DATA_STORE = "selected pickup address data store"
        private const val ID_ADDRESS = "id"
        private val ID_KEY = longPreferencesKey(ID_ADDRESS)
        private const val DEFAULT_LONG = 0L
    }
}