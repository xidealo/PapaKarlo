package com.bunbeauty.core.domain.repo

interface CreateOrderSettingsRepo {
    suspend fun getWithoutUtensils(): Boolean

    suspend fun saveWithoutUtensils(withoutUtensils: Boolean)

    suspend fun clearWithoutUtensils()
}
