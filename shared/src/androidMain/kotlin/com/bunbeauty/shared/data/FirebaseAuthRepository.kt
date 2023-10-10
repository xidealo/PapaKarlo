package com.bunbeauty.shared.data

actual class FirebaseAuthRepository {
    actual val firebaseUserUuid: String? = ""

    actual val firebaseUserPhone: String? = ""

    actual fun signOut(){}
}