package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.domain.model.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM UserEntity WHERE uuid = :userId")
    fun getUser(userId: String): Flow<UserEntity?>

//    @Query("SELECT * FROM User WHERE userId = :userId")
//    fun getUser(userId: String): User?
}