package com.bunbeauty.data

import com.bunbeauty.domain.auth.IAuthUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthUtil @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : IAuthUtil {

    override val isAuthorize: Boolean
        get() = userUuid != null

    override val userUuid: String?
        get() = firebaseAuth.currentUser?.uid

    override val userPhone: String?
        get() = firebaseAuth.currentUser?.phoneNumber

    override fun observeUserUuid(): Flow<String?> = callbackFlow {
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