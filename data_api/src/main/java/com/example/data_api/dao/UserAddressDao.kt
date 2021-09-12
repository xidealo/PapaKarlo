package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.user.UserAddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAddressDao: BaseDao<UserAddressEntity> {

    @Query("SELECT * FROM UserAddressEntity WHERE userUuid = :userUuid")
    fun observeUserAddressListByUserUuid(userUuid: String): Flow<List<UserAddressEntity>>
}