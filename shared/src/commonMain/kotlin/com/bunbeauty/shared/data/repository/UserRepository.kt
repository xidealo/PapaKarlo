package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.data.repository.base.CacheRepository
import com.bunbeauty.shared.domain.model.profile.User
import com.bunbeauty.shared.domain.repo.UserRepo

class UserRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo
) : CacheRepository<User>(), UserRepo {

    override val tag: String = "USER_TAG"

    override suspend fun clearUserCache() {
        dataStoreRepo.clearUserData()
    }

    override suspend fun disableUser(token: String) {
        networkConnector.patchSettings(token, PatchUserServer(isActive = false))
    }
}
