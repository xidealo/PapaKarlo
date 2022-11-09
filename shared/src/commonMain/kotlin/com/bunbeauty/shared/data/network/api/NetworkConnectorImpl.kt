package com.bunbeauty.shared.data.network.api

import com.bunbeauty.shared.Constants.AUTHORIZATION_HEADER
import com.bunbeauty.shared.Constants.BEARER
import com.bunbeauty.shared.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.shared.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.shared.Constants.UUID_PARAMETER
import com.bunbeauty.shared.Logger.WEB_SOCKET_TAG
import com.bunbeauty.shared.Logger.logD
import com.bunbeauty.shared.Logger.logE
import com.bunbeauty.shared.data.companyUuid
import com.bunbeauty.shared.data.network.ApiError
import com.bunbeauty.shared.data.network.ApiResult
import com.bunbeauty.shared.data.network.model.*
import com.bunbeauty.shared.data.network.model.login.AuthResponseServer
import com.bunbeauty.shared.data.network.model.login.LoginPostServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkConnectorImpl : KoinComponent, NetworkConnector {

    private val client: HttpClient by inject()
    private val json: Json by inject()

    private var webSocketSession: DefaultClientWebSocketSession? = null

    // GET

    override suspend fun getForceUpdateVersion(): ApiResult<ForceUpdateVersionServer> {
        return getData(
            serializer = ForceUpdateVersionServer.serializer(),
            path = "force_update_version",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCategoryList(): ApiResult<ListServer<CategoryServer>> {
        return getData(
            serializer = ListServer.serializer(CategoryServer.serializer()),
            path = "category",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>> {
        return getData(
            serializer = ListServer.serializer(MenuProductServer.serializer()),
            path = "menu_product",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCityList(): ApiResult<ListServer<CityServer>> {
        return getData(
            serializer = ListServer.serializer(CityServer.serializer()),
            path = "city",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>> {
        return getData(
            serializer = ListServer.serializer(CafeServer.serializer()),
            path = "cafe",
            parameters = hashMapOf(CITY_UUID_PARAMETER to cityUuid)
        )
    }

    override suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>> {
        return getData(
            serializer = ListServer.serializer(StreetServer.serializer()),
            path = "street",
            parameters = hashMapOf(CITY_UUID_PARAMETER to cityUuid)
        )
    }

    override suspend fun getDelivery(): ApiResult<DeliveryServer> {
        return getData(
            serializer = DeliveryServer.serializer(),
            path = "delivery",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getPayment(token: String): ApiResult<PaymentServer> {
        return getData(
            serializer = PaymentServer.serializer(),
            path = "payment",
            token = token
        )
    }

    override suspend fun getProfile(token: String): ApiResult<ProfileServer> {
        return getData(
            serializer = ProfileServer.serializer(),
            path = "client",
            token = token
        )
    }

    // POST

    override suspend fun postLogin(loginPostServer: LoginPostServer): ApiResult<AuthResponseServer> {
        return postData(
            serializer = AuthResponseServer.serializer(),
            path = "client/login",
            postBody = loginPostServer,
        )
    }

    override suspend fun postUserAddress(
        token: String,
        userAddress: UserAddressPostServer
    ): ApiResult<AddressServer> {
        return postData(
            serializer = AddressServer.serializer(),
            path = "address",
            postBody = userAddress,
            token = token
        )
    }

    override suspend fun postOrder(token: String, order: OrderPostServer): ApiResult<OrderServer> {
        return postData(
            serializer = OrderServer.serializer(),
            path = "order",
            postBody = order,
            token = token
        )
    }

    // PATCH

    override suspend fun patchProfileEmail(
        token: String,
        userUuid: String,
        patchUserServer: PatchUserServer
    ): ApiResult<ProfileServer> {
        return patchData(
            serializer = ProfileServer.serializer(),
            path = "client",
            parameters = hashMapOf(UUID_PARAMETER to userUuid),
            patchBody = patchUserServer,
            token = token
        )
    }

    // WEB_SOCKET

    override fun subscribeOnOrderUpdates(token: String): Flow<OrderServer> {
        return subscribeOnWebSocket(
            path = "client/order/subscribe",
            serializer = OrderServer.serializer(),
            token
        )
    }

    override suspend fun unsubscribeOnOrderUpdates() {
        if (webSocketSession != null) {
            webSocketSession?.close(CloseReason(CloseReason.Codes.NORMAL, "User logout"))
            webSocketSession = null
        }
    }

    // COMMON

    private fun <S> subscribeOnWebSocket(path: String, serializer: KSerializer<S>, token: String): Flow<S> {
        return flow {
            try {
                client.webSocket(
                    HttpMethod.Get,
                    path = path,
                    port = 80,
                    request = {
                        header(AUTHORIZATION_HEADER, BEARER + token)
                    }
                ) {
                    logD(WEB_SOCKET_TAG, "WebSocket connected")
                    webSocketSession = this
                    while (true) {
                        val message = incoming.receive() as? Frame.Text ?: continue
                        logD(WEB_SOCKET_TAG, "Message: ${message.readText()}")
                        val serverModel = json.decodeFromString(serializer, message.readText())
                        emit(serverModel)
                    }
                }
            } catch (e: WebSocketException) {
                logE(WEB_SOCKET_TAG, "WebSocketException: ${e.message}")
            } catch (e: Throwable) {
                logE(WEB_SOCKET_TAG, "Exception: ${e.message}")
            } finally {
                unsubscribeOnOrderUpdates()
            }
        }
    }

    suspend fun <T> getData(
        serializer: KSerializer<T>,
        path: String,
        parameters: Map<String, String> = mapOf(),
        token: String? = null
    ): ApiResult<T> {
        return handleNetworkCall(serializer) {
            client.get {
                buildRequest(
                    path = path,
                    parameters = parameters,
                    token = token
                )
            }
        }
    }

    private suspend fun <R> postData(
        serializer: KSerializer<R>,
        path: String,
        parameters: Map<String, String> = mapOf(),
        postBody: Any,
        token: String? = null
    ): ApiResult<R> {
        return handleNetworkCall(serializer) {
            client.post {
                buildRequest(
                    path = path,
                    parameters = parameters,
                    body = postBody,
                    token = token
                )
            }
        }
    }

    private suspend fun <R> patchData(
        path: String,
        patchBody: Any,
        serializer: KSerializer<R>,
        parameters: Map<String, String> = mapOf(),
        token: String? = null
    ): ApiResult<R> {
        return handleNetworkCall(serializer) {
            client.patch {
                buildRequest(
                    path = path,
                    parameters = parameters,
                    body = patchBody,
                    token = token
                )
            }
        }
    }

    private suspend inline fun <R> handleNetworkCall(
        serializer: KSerializer<R>,
        networkCall: () -> HttpResponse,
    ): ApiResult<R> {
        return try {
            ApiResult.Success(json.decodeFromString(serializer, networkCall().bodyAsText()))
        } catch (exception: ClientRequestException) {
            ApiResult.Error(ApiError(exception.response.status.value, exception.message))
        } catch (exception: Throwable) {
            ApiResult.Error(ApiError(0, "Bad Internet"))
        }
    }

    private fun HttpRequestBuilder.buildRequest(
        path: String,
        parameters: Map<String, String> = mapOf(),
        body: Any? = null,
        token: String? = null
    ) {
        if (body != null) {
            setBody(body)
        }
        url {
            path(path)
        }
        parameters.forEach { parameterMap ->
            parameter(parameterMap.key, parameterMap.value)
        }
        if (token != null) {
            header(AUTHORIZATION_HEADER, "Bearer $token")
        }
    }

}