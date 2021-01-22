package com.bunbeauty.papakarlo.data.local.db.address

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao : BaseDao<Address> {
    @Query("SELECT * FROM Address")
    fun getAddresses(): Flow<List<Address>>
}