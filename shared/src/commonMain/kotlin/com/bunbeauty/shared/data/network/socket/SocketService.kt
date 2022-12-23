package com.bunbeauty.shared.data.network.socket

import com.bunbeauty.shared.Constants
import com.bunbeauty.shared.Logger
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
    private val client: HttpClient,
    private val json: Json
): KoinComponent {

    private var socketSessionMap: MutableMap<String, WebSocketSession> = hashMapOf()

    suspend fun <S> observeSocketMessages(
        path: String,
        serializer: KSerializer<S>,
        token: String
    ): Flow<S> {
        return try {
            val socketSession = client.webSocketSession {
                method = HttpMethod.Get
                url("ws", null, 80, path)
                header(Constants.AUTHORIZATION_HEADER, Constants.BEARER + token)
            }
            socketSessionMap[path] = socketSession
            if (socketSession.isActive) {
                socketSession.incoming
                    .receiveAsFlow()
                    .filter { it is Frame.Text }
                    .map { frame ->
                        val message = frame as Frame.Text
                        Logger.logD(Logger.WEB_SOCKET_TAG, "Message: ${message.readText()}")
                        json.decodeFromString(serializer, message.readText())
                    }.catch { exception ->
                        Logger.logE(Logger.WEB_SOCKET_TAG, "Exception: ${exception.message}")
                    }
            } else {
                flow {  }
            }
        } catch (exception: Exception) {
            socketSessionMap[path]?.close()
            flow {  }
        }
    }

    suspend fun closeSession(path: String) {
        socketSessionMap[path]?.close()
    }
}