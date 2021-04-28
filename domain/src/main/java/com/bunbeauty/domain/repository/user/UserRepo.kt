package com.bunbeauty.domain.repository.user

import com.bunbeauty.data.model.user.User

interface UserRepo {
    suspend fun insert(user: User)
}