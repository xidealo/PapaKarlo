package com.bunbeauty.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.bunbeauty.data.database.LocalDatabase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideKtorHttpClient() = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(WebSockets) {
            pingInterval = 10
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

    @Provides
    fun provideJson(): Json = Json {
        isLenient = false
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): LocalDatabase = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        "ApiLocalDatabase"
    ).fallbackToDestructiveMigration().build()

    //DAO

    @Singleton
    @Provides
    fun provideMenuProductDao(localDatabase: LocalDatabase) =
        localDatabase.getMenuProductDao()

    @Singleton
    @Provides
    fun provideCartProductDao(localDatabase: LocalDatabase) =
        localDatabase.getCartProductDao()

    @Singleton
    @Provides
    fun provideCafeDao(localDatabase: LocalDatabase) = localDatabase.getCafeDao()

    @Singleton
    @Provides
    fun provideUserDao(localDatabase: LocalDatabase) = localDatabase.getUserDao()

    @Singleton
    @Provides
    fun provideUserAddressDao(localDatabase: LocalDatabase) =
        localDatabase.getUserAddressDao()

    @Singleton
    @Provides
    fun provideStreetDao(localDatabase: LocalDatabase) = localDatabase.getStreetDao()

    @Singleton
    @Provides
    fun provideCityDao(localDatabase: LocalDatabase) = localDatabase.getCityDao()

    @Singleton
    @Provides
    fun provideOrderDao(localDatabase: LocalDatabase) = localDatabase.getOrderDao()

    @Singleton
    @Provides
    fun provideCategoryDao(localDatabase: LocalDatabase) = localDatabase.getCategoryDao()
}

fun apiDataModule() = module {
    //json
    single {
        Json {
            isLenient = false
            ignoreUnknownKeys = true
        }
    }
    single {
        FirebaseAuth.getInstance()
    }
    //ktor
    single {
        HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(WebSockets) {
                pingInterval = 10
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
    }
    //room
    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java,
            "ApiLocalDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    //dao
    single {
        get<LocalDatabase>().getMenuProductDao()
    }
    single {
        get<LocalDatabase>().getCartProductDao()
    }
    single {
        get<LocalDatabase>().getCafeDao()
    }
    single {
        get<LocalDatabase>().getUserDao()
    }
    single {
        get<LocalDatabase>().getUserAddressDao()
    }
    single {
        get<LocalDatabase>().getStreetDao()
    }
    single {
        get<LocalDatabase>().getCityDao()
    }
    single {
        get<LocalDatabase>().getOrderDao()
    }
    single {
        get<LocalDatabase>().getCategoryDao()
    }
}