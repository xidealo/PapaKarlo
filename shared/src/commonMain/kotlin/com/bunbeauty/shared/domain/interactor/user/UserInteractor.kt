package com.bunbeauty.shared.domain.interactor.user

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import com.bunbeauty.shared.domain.repo.OrderRepo
import com.bunbeauty.shared.domain.repo.UserRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class UserInteractor(
    private val userRepo: UserRepo,
    private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
) : IUserInteractor {

    override suspend fun clearUserCache() {
        userRepo.clearUserCache()
        orderRepo.clearCache()
    }

    override suspend fun isUserAuthorize(): Boolean {
        return dataStoreRepo.getToken() != null
    }

    override suspend fun getProfile(): Profile? {
        val token = dataStoreRepo.getToken() ?: return Profile.Unauthorized
        return if (isUserAuthorize()) {
            dataStoreRepo.getUserAndCityUuid().let { userCityUuid ->
                userRepo.getProfileByUserUuidAndCityUuid(
                    userUuid = userCityUuid.userUuid,
                    cityUuid = userCityUuid.cityUuid,
                    token = token
                )
            }
        } else {
            Profile.Unauthorized
        }
    }

}