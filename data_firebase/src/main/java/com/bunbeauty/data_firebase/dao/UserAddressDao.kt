package com.bunbeauty.data_firebase.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_firebase.model.entity.address.UserAddressEntity
import com.example.domain_firebase.model.entity.address.UserAddressWithStreet
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAddressDao : BaseDao<UserAddressEntity> {

    // OBSERVE

    @Query("SELECT * FROM UserAddressEntity WHERE uuid == :uuid")
    fun observeByUuid(uuid: String): Flow<UserAddressWithStreet?>

    @Query("SELECT * FROM UserAddressEntity WHERE userUuid == :userUuid")
    fun observeListByUserUuid(userUuid: String): Flow<List<UserAddressWithStreet>>

    @Query("SELECT * FROM UserAddressEntity WHERE userUuid IS NULL")
    fun observeUnassignedList(): Flow<List<UserAddressWithStreet>>

    // GET

    @Query("SELECT * FROM UserAddressEntity WHERE userUuid IS NULL")
    suspend fun getUnassignedList(): List<UserAddressWithStreet>

    // DELETE

    @Query("DELETE FROM UserAddressEntity")
    suspend fun deleteAll()
}