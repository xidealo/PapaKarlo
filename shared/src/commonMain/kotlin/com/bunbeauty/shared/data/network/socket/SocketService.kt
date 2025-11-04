package com.bunbeauty.shared.data.network.socket

import com.bunbeauty.core.Logger
import com.bunbeauty.shared.Constants.AUTHORIZATION_HEADER
import com.bunbeauty.shared.Constants.BEARER
import com.bunbeauty.shared.data.UuidGenerator
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class SocketService(
    private val uuidGenerator: UuidGenerator,
    private val client: HttpClient,
    private val json: Json
) : KoinComponent {

    private var socketSessionMap: MutableMap<String, WebSocketSession> = hashMapOf()

    suspend fun <S> observeSocketMessages(
        path: String,
        serializer: KSerializer<S>,
        token: String
    ): Pair<String?, Flow<S>> {
        return try {
            val socketSession = client.webSocketSession {
                method = HttpMethod.Get
                url("wss", null, 443, path)
                header(AUTHORIZATION_HEADER, BEARER + token)
            }
            val uuid = uuidGenerator.generateUuid()
            socketSessionMap[uuid] = socketSession
            return if (socketSession.isActive) {
                Logger.logD(Logger.WEB_SOCKET_TAG, "Connect $uuid")
                val flow = socketSession.incoming
                    .receiveAsFlow()
                    .filter { it is Frame.Text }
                    .map { frame ->
                        val message = frame as Frame.Text
                        Logger.logD(Logger.WEB_SOCKET_TAG, "Message: ${message.readText()}")
                        json.decodeFromString(serializer, message.readText())
                    }.catch { exception ->
                        Logger.logE(Logger.WEB_SOCKET_TAG, "Exception: ${exception.message}")
                    }
                uuid to flow
            } else {
                null to flow { }
            }
        } catch (exception: Exception) {
            Logger.logE(Logger.WEB_SOCKET_TAG, "Exception: ${exception.message}")
            socketSessionMap[path]?.close()
            null to flow { }
        }
    }

    suspend fun closeSession(uuid: String) {
        Logger.logD(Logger.WEB_SOCKET_TAG, "CloseSession")
        socketSessionMap[uuid]?.close()
    }
}
