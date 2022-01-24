package com.example.data_api.repository

import com.bunbeauty.common.Logger.VERSION_TAG
import com.bunbeauty.domain.repo.VersionRepo
import com.example.data_api.handleResultAndReturn
import com.example.domain_api.repo.ApiRepo
import javax.inject.Inject

class VersionRepository @Inject constructor(private val apiRepo: ApiRepo) : VersionRepo {

    override suspend fun getForceUpdateVersion(): Int {
        return apiRepo.getForceUpdateVersion()
            .handleResultAndReturn(VERSION_TAG) { forceUpdateVersionServer ->
                forceUpdateVersionServer.version
            } ?: 0
    }
}