package com.bunbeauty.core.domain.user

import com.bunbeauty.core.domain.repo.CafeRepo
import com.bunbeauty.core.domain.repo.CreateOrderSettingsRepo
import com.bunbeauty.core.domain.repo.OrderRepo
import com.bunbeauty.core.domain.repo.UserAddressRepo
import com.bunbeauty.core.domain.repo.UserRepo
import com.bunbeauty.core.model.profile.Profile

class UserInteractor(
    private val userRepo: UserRepo,
    private val orderRepo: OrderRepo,
    private val cafeRepo: CafeRepo,
    private val userAddressRepo: UserAddressRepo,
    private val createOrderSettingsRepo: CreateOrderSettingsRepo,
) : IUserInteractor {
    override suspend fun clearUserCache() {
        userRepo.clearUserCache()
        orderRepo.clearCache()
        cafeRepo.clearCache()
        userAddressRepo.clearCache()
        createOrderSettingsRepo.clearWithoutUtensils()
    }

    override suspend fun isUserAuthorize(): Boolean = userRepo.getToken() != null

    override suspend fun getProfile(): Profile? =
        if (isUserAuthorize()) {
            userRepo.getProfile()
        } else {
            Profile.Unauthorized
        }
}
