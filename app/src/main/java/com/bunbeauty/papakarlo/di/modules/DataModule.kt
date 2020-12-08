package com.bunbeauty.papakarlo.di.modules

import android.content.Context
import androidx.room.Room
import com.bunbeauty.papakarlo.data.local.db.LocalDatabase
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
        "EldDatabase"
    ).fallbackToDestructiveMigration().build()

    //DAO

    @Provides
    fun provideCartDao(localDatabase: LocalDatabase) = localDatabase.getCartDao()

}