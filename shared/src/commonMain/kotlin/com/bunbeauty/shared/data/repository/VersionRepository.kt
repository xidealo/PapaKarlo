package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.repo.VersionRepo

class VersionRepository(
    private val networkConnector: NetworkConnector,
) : BaseRepository(),
    VersionRepo {
    override val tag: String = "VERSION_TAG"

    override suspend fun getForceUpdateVersion(): Int =
        networkConnector.getForceUpdateVersion().getNullableResult { forceUpdateVersionServer ->
            forceUpdateVersionServer.version
        } ?: 0
}
