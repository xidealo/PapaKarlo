package com.bunbeauty.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.domain.model.ui.address.CafeAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface CafeAddressDao : BaseDao<CafeAddress> {

    @Query("SELECT * FROM CafeAddress WHERE cafeId IS NOT NULL")
    fun getCafeAddresses(): Flow<List<CafeAddress>>

    @Query("SELECT * FROM CafeAddress WHERE id == :id")
    fun getAddressById(id: Long): Flow<CafeAddress?>

    @Query("SELECT * FROM CafeAddress WHERE cafeId == :cafeId")
    fun getAddressByCafeId(cafeId: String): Flow<CafeAddress?>

    @Query("SELECT * FROM CafeAddress WHERE uuid == :cafeUuid")
    fun getAddressByCafeUuid(cafeUuid: String): Flow<CafeAddress?>

    @Query("SELECT * FROM CafeAddress LIMIT 1")
    fun getFirstAddress(): LiveData<CafeAddress?>
}