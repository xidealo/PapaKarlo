package com.bunbeauty.shared.domain.interactor.user

import com.bunbeauty.shared.domain.model.profile.Profile

interface IUserInteractor {

    suspend fun clearUserCache()
    suspend fun isUserAuthorize(): Boolean

    suspend fun getProfile(): Profile?
}
