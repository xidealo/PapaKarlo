package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.user.ProfileEntity
import com.example.domain_api.model.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao: BaseDao<UserEntity> {


    // OBSERVE

    @Query("SELECT * FROM UserEntity WHERE uuid = :uuid")
    fun observeUserByUuid(uuid: String): Flow<ProfileEntity?>

    // GET

    @Query("SELECT * FROM UserEntity WHERE uuid = :uuid")
    suspend fun getUserByUuid(uuid: String): ProfileEntity?
}