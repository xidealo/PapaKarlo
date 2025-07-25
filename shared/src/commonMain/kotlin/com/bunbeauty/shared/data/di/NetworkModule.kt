package com.bunbeauty.shared.data.di

import com.bunbeauty.core.Logger
import com.bunbeauty.core.Logger.NETWORK_TAG
import com.bunbeauty.shared.domain.exeptions.AuthSessionTimeoutException
import com.bunbeauty.shared.domain.exeptions.InvalidCodeException
import com.bunbeauty.shared.domain.exeptions.NoAttemptsException
import com.bunbeauty.shared.domain.exeptions.OrderNotAvailableException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.httpClientEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
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
            HttpResponseValidator {
                validateResponse { response ->
                    when (response.status.value) {
                        800 -> throw TooManyRequestsException()
                        801 -> throw NoAttemptsException()
                        802 -> throw InvalidCodeException()
                        803 -> throw AuthSessionTimeoutException()
                        901 -> throw OrderNotAvailableException()
                    }
                }
            }

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

            install(HttpTimeout) {
                requestTimeoutMillis = 35000
                connectTimeoutMillis = 35000
                socketTimeoutMillis = 35000
            }
        }
    }
}
