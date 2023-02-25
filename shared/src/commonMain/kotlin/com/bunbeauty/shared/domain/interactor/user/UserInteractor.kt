package com.bunbeauty.shared.domain.interactor.user

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.exeptions.NotAuthorizeException
import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import com.bunbeauty.shared.domain.repo.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class UserInteractor(
    private val userRepo: UserRepo,
    private val dataStoreRepo: DataStoreRepo,
) : IUserInteractor {
    @Throws(Throwable::class)
    override suspend fun login(firebaseUserUuid: String?, firebaseUserPhone: String?) {
        if (firebaseUserUuid != null && firebaseUserPhone != null) {
            val loginResponse = userRepo.login(firebaseUserUuid, firebaseUserPhone)
            if (loginResponse != null) {
                dataStoreRepo.saveToken(loginResponse.token)
                dataStoreRepo.saveUserUuid(loginResponse.userUuid)
            } else {
                throw NotAuthorizeException
            }
        }
    }

    override suspend fun clearUserCache() {
        userRepo.clearUserCache()
    }

    override suspend fun isUserAuthorize(): Boolean {
        return dataStoreRepo.getToken() != null
    }

    override fun observeIsUserAuthorize(): CommonFlow<Boolean> {
        return dataStoreRepo.token.map { token ->
            token != null
        }.asCommonFlow()
    }

    override fun observeUser(): Flow<User?> {
        return dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            userRepo.observeUserByUuid(userUuid ?: "")
        }
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