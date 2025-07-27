package com.bunbeauty.shared.data.network.socket

import com.bunbeauty.core.Logger
import com.bunbeauty.shared.Constants.AUTHORIZATION_HEADER
import com.bunbeauty.shared.Constants.BEARER
import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.data.network.model.order.get.OrderUpdateServer
import io.ktor.client.HttpClient
import io.ktor.client.plugins.sse.sse
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.http.headers
import io.ktor.http.headersOf
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class SocketService(
    private val uuidGenerator: UuidGenerator,
    private val client: HttpClient,
    private val json: Json,
) : KoinComponent {

    private var socketSessionMap: MutableMap<String, WebSocketSession> = hashMapOf()

    suspend fun <S> observeSocketMessages(
        path: String,
        serializer: KSerializer<S>,
        token: String,
    ): Pair<String?, Flow<OrderUpdateServer>> {
        return try {
            var sharedFlow: SharedFlow<OrderUpdateServer> = MutableSharedFlow()

            val uuid = uuidGenerator.generateUuid()

            client.sse(
                urlString = path,

                request = {
                    header(AUTHORIZATION_HEADER, BEARER + token)
                }
            ) {
                incoming
                    .catch { exception ->
                        Logger.logE(Logger.WEB_SOCKET_TAG, "Exception: ${exception.message}")
                    }
                    .collect { sseEvent ->
                        Logger.logD(Logger.WEB_SOCKET_TAG, "Message: ${sseEvent.event}")
                        sseEvent.event?.let { eventText ->
                            json.decodeFromString(serializer, eventText)
                        }
                    }
            }
            return uuid to sharedFlow
        } catch (exception: Exception) {
            Logger.logE(Logger.WEB_SOCKET_TAG, "Exception: ${exception.message}")
            null to flow { }
        }
    }

    suspend fun closeSession(uuid: String) {
        Logger.logD(Logger.WEB_SOCKET_TAG, "CloseSession")
    }
}
