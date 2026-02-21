package com.bunbeauty.core.domain.user

import com.bunbeauty.core.model.profile.Profile

interface IUserInteractor {
    suspend fun clearUserCache()

    suspend fun isUserAuthorize(): Boolean

    suspend fun getProfile(): Profile?
}
