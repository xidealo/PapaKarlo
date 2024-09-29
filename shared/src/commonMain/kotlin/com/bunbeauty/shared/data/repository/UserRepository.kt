package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.domain.repo.UserRepo

class UserRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo
) : UserRepo {

    override suspend fun clearUserCache() {
        dataStoreRepo.clearUserData()
    }

    override suspend fun disableUser(token: String) {
        networkConnector.patchSettings(token, PatchUserServer(isActive = false))
    }
}
