package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.domain.model.ui.address.UserAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAddressDao : BaseDao<UserAddress> {

    @Query("SELECT * FROM UserAddress WHERE uuid == :uuid")
    fun getUserAddressByUuid(uuid: String): Flow<UserAddress?>

    @Query("SELECT * FROM UserAddress WHERE userUuid == :userId")
    fun getUserAddressByUserId(userId: String): Flow<List<UserAddress>>

    @Query("SELECT * FROM UserAddress WHERE userUuid IS NULL")
    fun getUnassignedList(): Flow<List<UserAddress>>
}