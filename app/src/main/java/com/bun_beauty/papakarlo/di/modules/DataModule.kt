package com.bun_beauty.papakarlo.di.modules

import android.content.Context
import androidx.room.Room
import com.bun_beauty.papakarlo.data.local.db.LocalDatabase
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
/*
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val moshi = Moshi.Builder().add(object {
            @FromJson
            fun fromJson(reader: JsonReader): String? {
                val result = if (reader.peek() === JsonReader.Token.NULL) {
                    reader.nextNull()
                } else {
                    reader.nextString()
                }

                return result ?: ""
            }
        }).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(ApiService::class.java)
    }
*/

    //DAO
/*
    @Provides
    fun provideDriverDao(localDatabase: LocalDatabase) = localDatabase.getDriverDao()*/

}