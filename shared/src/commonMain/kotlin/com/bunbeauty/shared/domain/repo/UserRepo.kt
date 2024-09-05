package com.bunbeauty.shared.domain.repo

interface UserRepo {

    suspend fun clearUserCache()

    suspend fun disableUser(token: String)
}
