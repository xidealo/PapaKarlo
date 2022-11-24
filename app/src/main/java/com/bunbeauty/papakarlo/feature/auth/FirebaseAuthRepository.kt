package com.bunbeauty.papakarlo.feature.auth

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) {

    val firebaseUserUuid: String?
        get() = firebaseAuth.currentUser?.uid

    val firebaseUserPhone: String?
        get() = firebaseAuth.currentUser?.phoneNumber

    fun signOut() {
        firebaseAuth.signOut()
    }
}
