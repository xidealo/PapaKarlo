package com.bunbeauty.core.domain.repo

interface RecommendationRepo {
    suspend fun getMaxVisibleCount(): Int
}
