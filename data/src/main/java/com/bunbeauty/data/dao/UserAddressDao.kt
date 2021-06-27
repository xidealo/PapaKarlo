package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.domain.model.address.UserAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAddressDao : BaseDao<UserAddress> {

    @Query("SELECT * FROM UserAddress WHERE uuid == :uuid")
    fun getUserAddressByUuid(uuid: String): Flow<UserAddress?>

    @Query("SELECT * FROM UserAddress WHERE userId == :userId")
    fun getUserAddressByUserId(userId: String): Flow<List<UserAddress>>
}