package com.bunbeauty.domain.repo

interface VersionRepo {

    suspend fun getForceUpdateVersion(): Int
}