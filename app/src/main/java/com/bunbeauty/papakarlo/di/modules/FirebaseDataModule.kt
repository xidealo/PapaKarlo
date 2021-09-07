package com.bunbeauty.papakarlo.di.modules

import android.content.Context
import androidx.room.Room
import com.bunbeauty.data_firebase.FirebaseLocalDatabase
import com.bunbeauty.papakarlo.BuildConfig.FB_LINK
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseDataModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance(FB_LINK)

    @Singleton
    @Provides
    fun provideDatabase(context: Context): FirebaseLocalDatabase = Room.databaseBuilder(
        context,
        FirebaseLocalDatabase::class.java,
        "FirebaseLocalDatabase"
    ).fallbackToDestructiveMigration().build()

    //DAO

    @Singleton
    @Provides
    fun provideCartProductDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getCartProductDao()

    @Singleton
    @Provides
    fun provideOrderDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getOrderDao()

    @Singleton
    @Provides
    fun provideMenuProductDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getMenuProductDao()

    @Singleton
    @Provides
    fun provideUserAddressDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getUserAddressDao()

    @Singleton
    @Provides
    fun provideCafeDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getCafeDao()

    @Singleton
    @Provides
    fun provideDistrictDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getDistrictDao()

    @Singleton
    @Provides
    fun provideStreetDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getStreetDao()

    @Singleton
    @Provides
    fun provideUserDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getUserDao()

}