package com.bunbeauty.domain.repository.address

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.model.Address
import com.bunbeauty.domain.repository.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao : BaseDao<Address> {
    @Query("SELECT * FROM Address")
    fun getAddresses(): LiveData<List<Address>>

    @Query("SELECT * FROM Address WHERE cafeId IS NULL")
    fun getNotCafeAddresses(): LiveData<List<Address>>

    @Query("SELECT * FROM Address WHERE cafeId IS NOT NULL")
    fun getCafeAddresses(): LiveData<List<Address>>

    @Query("SELECT * FROM Address WHERE id == :id")
    fun getAddressById(id: Long): LiveData<Address?>

    @Query("SELECT * FROM Address WHERE cafeId == :cafeId")
    fun getAddressByCafeId(cafeId: String): Flow<Address?>

    @Query("SELECT * FROM Address LIMIT 1")
    fun getFirstAddress(): LiveData<Address?>
}