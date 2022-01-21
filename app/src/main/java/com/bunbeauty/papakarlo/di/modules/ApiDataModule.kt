package com.bunbeauty.papakarlo.di.modules

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.data_api.ApiLocalDatabase
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
class ApiDataModule {

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

        install(WebSockets)

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
    fun provideDatabase(context: Context): ApiLocalDatabase = Room.databaseBuilder(
        context,
        ApiLocalDatabase::class.java,
        "ApiLocalDatabase"
    ).fallbackToDestructiveMigration().build()

    //DAO

    @Singleton
    @Provides
    fun provideMenuProductDao(apiLocalDatabase: ApiLocalDatabase) =
        apiLocalDatabase.getMenuProductDao()

    @Singleton
    @Provides
    fun provideCartProductDao(apiLocalDatabase: ApiLocalDatabase) =
        apiLocalDatabase.getCartProductDao()

    @Singleton
    @Provides
    fun provideCafeDao(apiLocalDatabase: ApiLocalDatabase) = apiLocalDatabase.getCafeDao()

    @Singleton
    @Provides
    fun provideUserDao(apiLocalDatabase: ApiLocalDatabase) = apiLocalDatabase.getUserDao()

    @Singleton
    @Provides
    fun provideUserAddressDao(apiLocalDatabase: ApiLocalDatabase) =
        apiLocalDatabase.getUserAddressDao()

    @Singleton
    @Provides
    fun provideStreetDao(apiLocalDatabase: ApiLocalDatabase) = apiLocalDatabase.getStreetDao()

    @Singleton
    @Provides
    fun provideCityDao(apiLocalDatabase: ApiLocalDatabase) = apiLocalDatabase.getCityDao()

    @Singleton
    @Provides
    fun provideOrderDao(apiLocalDatabase: ApiLocalDatabase) = apiLocalDatabase.getOrderDao()

    @Singleton
    @Provides
    fun provideCategoryDao(apiLocalDatabase: ApiLocalDatabase) = apiLocalDatabase.getCategoryDao()

}

fun apiDataModule() = module {
    //json
    single {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
    //ktor
    single {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
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
            ApiLocalDatabase::class.java,
            "ApiLocalDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    //dao
    single {
        get<ApiLocalDatabase>().getMenuProductDao()
    }
    single {
        get<ApiLocalDatabase>().getCartProductDao()
    }
    single {
        get<ApiLocalDatabase>().getCafeDao()
    }
    single {
        get<ApiLocalDatabase>().getUserDao()
    }
    single {
        get<ApiLocalDatabase>().getUserAddressDao()
    }
    single {
        get<ApiLocalDatabase>().getStreetDao()
    }
    single {
        get<ApiLocalDatabase>().getCityDao()
    }
    single {
        get<ApiLocalDatabase>().getOrderDao()
    }
    single {
        get<ApiLocalDatabase>().getCategoryDao()
    }
}

