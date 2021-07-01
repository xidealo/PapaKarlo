package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.domain.model.local.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserFlow(userId: String): Flow<User?>
}