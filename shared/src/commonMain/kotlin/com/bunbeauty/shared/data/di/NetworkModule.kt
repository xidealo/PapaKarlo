package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.Logger
import com.bunbeauty.shared.Logger.NETWORK_TAG
import com.bunbeauty.shared.httpClientEngine
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.Logger as KtorLogger
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule() = module {
    single {
        Json {
            isLenient = false
            ignoreUnknownKeys = true
        }
    }
    single {
        HttpClient(httpClientEngine) {

//        engine {
//            preconfigured = OkHttpClient.Builder()
//                .pingInterval(20, TimeUnit.SECONDS)
//                .build()
//        }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }

            install(WebSockets) {
                pingInterval = 10_000
            }

            install(Logging) {
                logger = object : KtorLogger {
                    override fun log(message: String) {
                        Logger.logD(NETWORK_TAG, message)
                    }
                }
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                host = "food-delivery-api-bunbeauty.herokuapp.com"
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                contentType(ContentType.Application.Json)

                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
}