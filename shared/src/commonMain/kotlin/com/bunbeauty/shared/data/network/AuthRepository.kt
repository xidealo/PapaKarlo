package com.bunbeauty.shared.data.network

import com.bunbeauty.shared.domain.repo.AuthRepo
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
//import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val firebase:Firebase
) : AuthRepo {

    override val isAuthorize: Boolean
        get() = firebase.auth.currentUser != null //(firebaseAuth.currentUser != null)

    override val firebaseUserUuid: String?
        get() = firebase.auth.currentUser?.uid

    override val firebaseUserPhone: String?
        get() = firebase.auth.currentUser?.phoneNumber

    override fun observeIsAuthorize(): Flow<Boolean> = flow {}
      //  callbackFlow {
//        val listener = FirebaseAuth.AuthStateListener { auth ->
//            trySend(auth.currentUser != null)
//        }
//        firebaseAuth.addAuthStateListener(listener)
//        awaitClose {
//            //firebaseAuth.removeAuthStateListener(listener)
//        }
   // }

    override suspend fun signOut() {
        firebase.auth.signOut()
    }
}