package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.Logger
import com.bunbeauty.shared.Logger.NETWORK_TAG
import com.bunbeauty.shared.httpClientEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import io.ktor.client.plugins.logging.Logger as KtorLogger

fun networkModule() = module {
    single {
        Json {
            isLenient = false
            ignoreUnknownKeys = true
        }
    }
    single {
        HttpClient(httpClientEngine) {

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

            install(WebSockets)

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

                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
}