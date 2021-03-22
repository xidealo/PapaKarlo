package com.bunbeauty.papakarlo.di.modules

import android.content.Context
import androidx.room.Room
import com.bunbeauty.domain.LocalDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        "PapaKarloDatabase"
    ).fallbackToDestructiveMigration().build()

    //DAO

    @Provides
    fun provideCartProductDao(localDatabase: LocalDatabase) = localDatabase.getCartProductDao()

    @Provides
    fun provideOrderDao(localDatabase: LocalDatabase) = localDatabase.getOrderDao()

    @Provides
    fun provideMenuProductDao(localDatabase: LocalDatabase) = localDatabase.getMenuProductDao()

    @Provides
    fun provideAddressDao(localDatabase: LocalDatabase) = localDatabase.getAddressDao()

    @Provides
    fun provideCafeDao(localDatabase: LocalDatabase) = localDatabase.getCafeDao()

    @Provides
    fun provideDistrictDao(localDatabase: LocalDatabase) = localDatabase.getDistrictDao()

    @Provides
    fun provideStreetDao(localDatabase: LocalDatabase) = localDatabase.getStreetDao()

}