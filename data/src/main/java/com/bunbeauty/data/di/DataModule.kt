package com.bunbeauty.data.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()
}