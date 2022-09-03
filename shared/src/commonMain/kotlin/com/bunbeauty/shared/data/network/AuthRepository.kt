package com.bunbeauty.shared.data.network

import com.bunbeauty.shared.domain.repo.AuthRepo

import kotlinx.coroutines.flow.*

class AuthRepository(
    //private val firebase: Firebase
) : AuthRepo {

    override val isAuthorize: Boolean
        get() = true //firebase.auth.currentUser != null //(firebaseAuth.currentUser != null)

    override val firebaseUserUuid: String?
        get() =  null //firebase.auth.currentUser?.uid

    override val firebaseUserPhone: String?
        get() = null //firebase.auth.currentUser?.phoneNumber

    override fun observeIsAuthorize(): Flow<Boolean> =
        flow {

        }

    override suspend fun signOut() {
        //firebase.auth.signOut()
    }
}