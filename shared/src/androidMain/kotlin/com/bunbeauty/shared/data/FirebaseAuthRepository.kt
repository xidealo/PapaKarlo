package com.bunbeauty.shared.data

import com.google.firebase.auth.FirebaseAuth

actual class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) {
    actual val firebaseUserUuid: String?
        get() = firebaseAuth.currentUser?.uid

    actual val firebaseUserPhone: String?
        get() = firebaseAuth.currentUser?.phoneNumber

    actual fun signOut(){
        firebaseAuth.signOut()
    }
}