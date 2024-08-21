package com.bunbeauty.shared.domain.repo

interface RecommendationRepo {
    suspend fun getMaxVisibleCount(): Int
}
