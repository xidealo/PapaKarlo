package com.bunbeauty.data

import com.bunbeauty.domain.repo.AuthRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepo {

    override val isAuthorize: Boolean
        get() = (firebaseAuth.currentUser != null)

    override val firebaseUserUuid: String?
        get() = firebaseAuth.currentUser?.uid

    override val firebaseUserPhone: String?
        get() = firebaseAuth.currentUser?.phoneNumber

    override fun observeFirebaseUserUuid(): Flow<String?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.uid)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}