package com.bunbeauty.shared.data

expect class FirebaseAuthRepository {
    val firebaseUserUuid: String?
    val firebaseUserPhone: String?
    fun signOut()
}