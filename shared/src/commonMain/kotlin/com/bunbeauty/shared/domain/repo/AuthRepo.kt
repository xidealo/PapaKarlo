package com.bunbeauty.shared.domain.repo

import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    val isAuthorize: Boolean
    val firebaseUserUuid: String?
    val firebaseUserPhone: String?

    fun observeIsAuthorize(): Flow<Boolean>
    suspend fun signOut()
}