package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.auth.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

fun appRepositoryModule() = module {
    single { FirebaseAuthRepository(firebaseAuth = FirebaseAuth.getInstance()) }
}
