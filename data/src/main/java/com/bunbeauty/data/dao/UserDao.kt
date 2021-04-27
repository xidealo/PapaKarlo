package com.bunbeauty.data.dao

import androidx.room.Dao
import com.bunbeauty.data.model.user.User

@Dao
interface UserDao : BaseDao<User>