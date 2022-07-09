package com.bunbeauty.shared.domain.interactor.user

import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import com.bunbeauty.shared.domain.repo.AuthRepo
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.repo.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class UserInteractor(
    private val userRepo: UserRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val authRepo: AuthRepo
) : IUserInteractor {

    override suspend fun login() {
        val userUuid = authRepo.firebaseUserUuid
        val userPhone = authRepo.firebaseUserPhone
        if (userUuid != null && userPhone != null) {
            val token = userRepo.login(userUuid, userPhone)
            if (token != null) {
                dataStoreRepo.saveToken(token)
                dataStoreRepo.saveUserUuid(userUuid)
            }
        }
    }

    override suspend fun logout() {
        authRepo.signOut()
        userRepo.clearUserCache()
    }

    override suspend fun isUserAuthorize(): Boolean {
        return authRepo.isAuthorize &&
                (dataStoreRepo.getToken() != null) &&
                (dataStoreRepo.getUserUuid() != null)
    }

    override fun observeIsUserAuthorize(): Flow<Boolean> {
        return authRepo.observeIsAuthorize().flatMapLatest { isAuthorize ->
            dataStoreRepo.token.flatMapLatest { token ->
                dataStoreRepo.userUuid.map { userUuid ->
                    isAuthorize && token != null && userUuid != null
                }
            }
        }
    }

    override fun observeUser(): Flow<User?> {
        return dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            userRepo.observeUserByUuid(userUuid ?: "")
        }
    }

    override suspend fun getProfile(): Profile? {
        val token = dataStoreRepo.getToken()
        return if (isUserAuthorize()) {
            dataStoreRepo.getUserAndCityUuid().let { userCityUuid ->
                userRepo.getProfileByUserUuidAndCityUuid(
                    userUuid = userCityUuid.userUuid,
                    cityUuid = userCityUuid.cityUuid,
                    token = token!!
                )
            }
        } else {
            Profile.Unauthorized
        }
    }

    override suspend fun updateUserEmail(email: String): User? {
        val userUuid = dataStoreRepo.getUserUuid()
        val token = dataStoreRepo.getToken()
        return if (isUserAuthorize() && userUuid != null && token != null) {
            userRepo.updateUserEmail(token, userUuid, email)
        } else {
            null
        }
    }
}