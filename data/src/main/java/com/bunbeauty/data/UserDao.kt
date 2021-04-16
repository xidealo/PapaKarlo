package com.bunbeauty.data

import androidx.room.Dao
import com.bunbeauty.data.model.user.User
import com.bunbeauty.data.BaseDao

@Dao
interface UserDao : BaseDao<User>