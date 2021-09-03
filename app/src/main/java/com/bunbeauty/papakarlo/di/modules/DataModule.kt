package com.bunbeauty.papakarlo.di.modules

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.bunbeauty.data.LocalDatabase
import com.bunbeauty.papakarlo.BuildConfig.FB_LINK
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
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
    fun provideKtorHttpClient() = HttpClient(Android) {
        val TIME_OUT = 60_000
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })

            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            host = "food-delivery-api-bunbeauty.herokuapp.com"
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }


    @Singleton
    @Provides
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        "PapaKarloDB"
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