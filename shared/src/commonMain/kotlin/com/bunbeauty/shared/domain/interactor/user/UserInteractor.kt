package com.bunbeauty.shared.domain.interactor.user

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.repo.OrderRepo
import com.bunbeauty.shared.domain.repo.UserRepo

class UserInteractor(
    private val userRepo: UserRepo,
    private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo
) : IUserInteractor {

    override suspend fun clearUserCache() {
        userRepo.clearUserCache()
        orderRepo.clearCache()
    }

    override suspend fun isUserAuthorize(): Boolean {
        return dataStoreRepo.getToken() != null
    }
}
