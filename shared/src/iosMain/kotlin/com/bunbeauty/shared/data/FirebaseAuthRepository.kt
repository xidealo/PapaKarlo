package com.bunbeauty.shared.data

import cocoapods.FirebaseAuth.FIRAuth

actual class FirebaseAuthRepository(
    private val firebaseAuth:FIRAuth
) {
    actual val firebaseUserUuid: String?
        get() = firebaseAuth.currentUser?.uid
    actual val firebaseUserPhone: String?
        get() = firebaseAuth.currentUser?.phoneNumber

    actual fun signOut(){
        firebaseAuth.signOut(null)
    }
}