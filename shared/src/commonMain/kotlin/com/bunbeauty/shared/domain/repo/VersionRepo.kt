package com.bunbeauty.shared.domain.repo

interface VersionRepo {

    suspend fun getForceUpdateVersion(): Int
}
