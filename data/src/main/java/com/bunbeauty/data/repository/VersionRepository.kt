package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.VERSION_TAG
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.shared.domain.repo.VersionRepo

class VersionRepository(private val apiRepo: ApiRepo) : BaseRepository(), VersionRepo {

    override val tag: String = VERSION_TAG

    override suspend fun getForceUpdateVersion(): Int {
        return apiRepo.getForceUpdateVersion().getNullableResult { forceUpdateVersionServer ->
            forceUpdateVersionServer.version
        } ?: 0
    }
}