package com.bunbeauty.shared.data.network.socket

import com.bunbeauty.shared.Constants.AUTHORIZATION_HEADER
import com.bunbeauty.shared.Constants.BEARER
import com.bunbeauty.shared.Logger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class SocketService(
    private val client: HttpClient,
    private val json: Json
) : KoinComponent {

    private var socketSessionMap: MutableMap<String, WebSocketSession> = hashMapOf()

    suspend fun <S> observeSocketMessages(
        path: String,
        serializer: KSerializer<S>,
        token: String
    ): Flow<S> {
        return flow {
            client.webSocket(
                method = HttpMethod.Get,
                port = 80,
                path = path,
                request = {
                    header(AUTHORIZATION_HEADER, BEARER + token)
                }
            ) {
                Logger.logD(Logger.WEB_SOCKET_TAG, "Connect")
                socketSessionMap[path] = this
                while (true) {
                    val frame = incoming.receive() as? Frame.Text
                    if (frame != null) {
                        Logger.logD(Logger.WEB_SOCKET_TAG, "Message: ${frame.readText()}")
                        emit(json.decodeFromString(serializer, frame.readText()))
                    }
                }
            }
        }.catch { throwable ->
            socketSessionMap[path]?.close()
            Logger.logE(Logger.WEB_SOCKET_TAG, "exception ${throwable.message}")
        }
    }

    suspend fun closeSession(path: String) {
        socketSessionMap[path]?.close()
    }
}