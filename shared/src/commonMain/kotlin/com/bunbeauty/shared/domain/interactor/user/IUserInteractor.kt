package com.bunbeauty.shared.domain.interactor.user

interface IUserInteractor {

    suspend fun clearUserCache()
    suspend fun isUserAuthorize(): Boolean
}
