package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.VERSION_TAG
import com.bunbeauty.data.handleResultAndReturn
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.repo.VersionRepo

class VersionRepository  constructor(private val apiRepo: ApiRepo) : VersionRepo {

    override suspend fun getForceUpdateVersion(): Int {
        return apiRepo.getForceUpdateVersion()
            .handleResultAndReturn(VERSION_TAG) { forceUpdateVersionServer ->
                forceUpdateVersionServer.version
            } ?: 0
    }
}