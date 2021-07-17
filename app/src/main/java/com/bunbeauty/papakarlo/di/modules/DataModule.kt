package com.bunbeauty.papakarlo.di.modules

import android.content.Context
import androidx.room.Room
import com.bunbeauty.data.LocalDatabase
import com.bunbeauty.papakarlo.BuildConfig.FB_LINK
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance(FB_LINK)

    @Singleton
    @Provides
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        "PapaKarloDatabase"
    ).fallbackToDestructiveMigration().build()

    //DAO

    @Singleton
    @Provides
    fun provideCartProductDao(localDatabase: LocalDatabase) = localDatabase.getCartProductDao()

    @Singleton
    @Provides
    fun provideOrderDao(localDatabase: LocalDatabase) = localDatabase.getOrderDao()

    @Singleton
    @Provides
    fun provideMenuProductDao(localDatabase: LocalDatabase) = localDatabase.getMenuProductDao()

    @Singleton
    @Provides
    fun provideCafeAddressDao(localDatabase: LocalDatabase) = localDatabase.getCafeAddressDao()

    @Singleton
    @Provides
    fun provideUserAddressDao(localDatabase: LocalDatabase) = localDatabase.getUserAddressDao()

    @Singleton
    @Provides
    fun provideCafeDao(localDatabase: LocalDatabase) = localDatabase.getCafeDao()

    @Singleton
    @Provides
    fun provideDistrictDao(localDatabase: LocalDatabase) = localDatabase.getDistrictDao()

    @Singleton
    @Provides
    fun provideStreetDao(localDatabase: LocalDatabase) = localDatabase.getStreetDao()

    @Singleton
    @Provides
    fun provideUserDao(localDatabase: LocalDatabase) = localDatabase.getUserDao()

}