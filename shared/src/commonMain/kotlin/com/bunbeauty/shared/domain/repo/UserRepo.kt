package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.profile.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun clearUserCache()

    suspend fun disableUser(token: String)
}
