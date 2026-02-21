package com.bunbeauty.core.domain.repo

interface VersionRepo {
    suspend fun getForceUpdateVersion(): Int
}
