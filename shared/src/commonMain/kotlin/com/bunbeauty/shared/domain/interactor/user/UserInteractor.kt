package com.bunbeauty.shared.domain.interactor.user

import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.repo.CafeRepo
import com.bunbeauty.shared.domain.repo.OrderRepo
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import com.bunbeauty.shared.domain.repo.UserRepo

class UserInteractor(
    private val userRepo: UserRepo,
    private val orderRepo: OrderRepo,
    private val cafeRepo: CafeRepo,
    private val userAddressRepo: UserAddressRepo,
) : IUserInteractor {
    override suspend fun clearUserCache() {
        userRepo.clearUserCache()
        orderRepo.clearCache()
        cafeRepo.clearCache()
        userAddressRepo.clearCache()
    }

    override suspend fun isUserAuthorize(): Boolean = userRepo.getToken() != null

    override suspend fun getProfile(): Profile? =
        if (isUserAuthorize()) {
            userRepo.getProfile()
        } else {
            Profile.Unauthorized
        }
}
