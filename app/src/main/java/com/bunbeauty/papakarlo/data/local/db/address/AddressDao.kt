package com.bunbeauty.papakarlo.data.local.db.address

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao : BaseDao<Address> {
    @Query("SELECT * FROM Address")
    fun getAddresses(): LiveData<List<Address>>

    @Query("SELECT * FROM Address WHERE NOT EXISTS (SELECT * FROM CafeEntity WHERE Address.id = CafeEntity.addressId)")
    fun getNotCafeAddresses(): LiveData<List<Address>>

    @Query("SELECT * FROM Address, CafeEntity WHERE Address.id == CafeEntity.addressId")
    fun getCafeAddresses(): LiveData<List<Address>>
}