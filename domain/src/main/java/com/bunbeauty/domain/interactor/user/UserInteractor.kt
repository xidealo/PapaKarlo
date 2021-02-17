package com.bunbeauty.domain.interactor.user

import com.bunbeauty.domain.interactor.order.OrderInteractor
import com.bunbeauty.domain.model.profile.LightProfile
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.AuthRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.domain.worker.IUserWorkerUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserInteractor @Inject constructor(
    @Api private val userRepo: UserRepo,
    private val userWorkerUtil: IUserWorkerUtil,
    private val dataStoreRepo: DataStoreRepo,
    private val authRepo: AuthRepo,
    private val orderInteractor: OrderInteractor
) : IUserInteractor {

    override suspend fun login() {
        val userUuid = authRepo.firebaseUserUuid
        val userPhone = authRepo.firebaseUserPhone
        if (userUuid != null && userPhone != null) {
            val token = userRepo.login(userUuid, userPhone)
            if (token != null) {
                dataStoreRepo.saveToken(token)
                userWorkerUtil.refreshUserBlocking(token)
            }
        }
    }

    override suspend fun logout() {
        userWorkerUtil.cancelRefreshUser()
        authRepo.signOut()
        dataStoreRepo.clearToken()
        dataStoreRepo.clearUserUuid()
    }

    override suspend fun isUserAuthorize(): Boolean {
        return authRepo.isAuthorize &&
                (dataStoreRepo.getToken() != null) &&
                (dataStoreRepo.getUserUuid() != null)
    }

    override fun observeUser(): Flow<User?> {
        return dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            userRepo.observeUserByUuid(userUuid ?: "")
        }
    }

    override fun observeLightProfile(): Flow<LightProfile?> {
        return dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            userRepo.observeProfileByUuid(userUuid ?: "").map { profile ->
                if (profile == null) {
                    null
                } else {
                    val lastOrderItem = profile.orderList.maxByOrNull { order ->
                        order.time
                    }?.let { order ->
                        orderInteractor.getLightOrder(order)
                    }
                    LightProfile(
                        userUuid = profile.user.uuid,
                        hasAddresses = profile.addressList.isNotEmpty(),
                        lastOrder = lastOrderItem
                    )
                }
            }
        }
    }

    override suspend fun updateUserEmail(email: String): User? {
        val userUuid = dataStoreRepo.getUserUuid()
        if (isUserAuthorize() && userUuid != null) {
            userRepo.updateUserEmail(userUuid, email)
        }

        return null
    }
}