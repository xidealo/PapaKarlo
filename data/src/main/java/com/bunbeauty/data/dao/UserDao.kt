package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.domain.model.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<UserEntity> {

    // OBSERVE

    @Query("SELECT * FROM UserEntity WHERE uuid = :uuid")
    fun observeByUuid(uuid: String): Flow<UserEntity?>

    // GET

    @Query("SELECT * FROM UserEntity WHERE uuid = :uuid")
    suspend fun getByUuid(uuid: String): UserEntity?
}