package com.bunbeauty.shared.data.network.api

import com.bunbeauty.shared.Constants.AUTHORIZATION_HEADER
import com.bunbeauty.shared.Constants.BEARER
import com.bunbeauty.shared.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.shared.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.shared.Constants.UUID_PARAMETER
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
import io.ktor.util.*
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
            path = "force_update_version",
            serializer = ForceUpdateVersionServer.serializer(),
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCategoryList(): ApiResult<ListServer<CategoryServer>> {
        return getData(
            path = "category",
            serializer = ListServer.serializer(CategoryServer.serializer()),
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>> {
        return getData(
            path = "menu_product",
            serializer = ListServer.serializer(MenuProductServer.serializer()),
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCityList(): ApiResult<ListServer<CityServer>> {
        return getData(
            path = "city",
            serializer = ListServer.serializer(CityServer.serializer()),
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>> {
        return getData(
            path = "cafe",
            serializer = ListServer.serializer(CafeServer.serializer()),
            parameters = hashMapOf(CITY_UUID_PARAMETER to cityUuid)
        )
    }

    override suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>> {
        return getData(
            path = "street",
            serializer = ListServer.serializer(StreetServer.serializer()),
            parameters = hashMapOf(CITY_UUID_PARAMETER to cityUuid)
        )
    }

    override suspend fun getDelivery(): ApiResult<DeliveryServer> {
        return getData(
            path = "delivery",
            serializer = DeliveryServer.serializer(),
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getProfile(token: String): ApiResult<ProfileServer> {
        return getDataWithAuth(
            path = "client",
            serializer = ProfileServer.serializer(),
            token = token
        )
    }

    // POST

    override suspend fun postLogin(loginPostServer: LoginPostServer): ApiResult<AuthResponseServer> {
        return postData(
            path = "client/login",
            postBody = loginPostServer,
            serializer = AuthResponseServer.serializer()
        )
    }

    override suspend fun postUserAddress(
        token: String,
        userAddress: UserAddressPostServer
    ): ApiResult<AddressServer> {
        return postDataWithAuth(
            path = "address",
            postBody = userAddress,
            serializer = AddressServer.serializer(),
            token = token
        )
    }

    override suspend fun postOrder(token: String, order: OrderPostServer): ApiResult<OrderServer> {
        return postDataWithAuth(
            path = "order",
            postBody = order,
            serializer = OrderServer.serializer(),
            token = token
        )
    }

    // PATCH

    override suspend fun patchProfileEmail(
        token: String,
        userUuid: String,
        patchUserServer: PatchUserServer
    ): ApiResult<ProfileServer> {
        return patchDataWithAuth(
            path = "client",
            patchBody = patchUserServer,
            serializer = ProfileServer.serializer(),
            parameters = hashMapOf(UUID_PARAMETER to userUuid),
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
            //logD(WEB_SOCKET_TAG, "webSocketSession closed")
        }
    }

    // COMMON

    fun <S> subscribeOnWebSocket(path: String, serializer: KSerializer<S>, token: String): Flow<S> {
        return flow {
            try {
                client.webSocket(
                    HttpMethod.Get,
                    path = path,
                    request = {
                        header(AUTHORIZATION_HEADER, BEARER + token)
                    }
                ) {
                    webSocketSession = this
                    while (true) {
                        val message = incoming.receive() as? Frame.Text ?: continue
                        val serverModel = json.decodeFromString(serializer, message.readText())
                        emit(serverModel)
                        //logD(WEB_SOCKET_TAG, "Message: ${message.readText()}")
                    }
                }
            } catch (e: Exception) {
                //logE(WEB_SOCKET_TAG, "Exception: ${e.message}")
                unsubscribeOnOrderUpdates()
            }
        }
    }

    suspend fun <T> getDataWithAuth(
        path: String,
        serializer: KSerializer<T>,
        parameters: Map<String, String> = mapOf(),
        token: String
    ): ApiResult<T> {
        return getData(
            path = path,
            serializer = serializer,
            parameters = parameters,
            headers = mapOf(AUTHORIZATION_HEADER to BEARER + token)
        )
    }

    suspend fun <T> getData(
        path: String,
        serializer: KSerializer<T>,
        parameters: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf(),
    ): ApiResult<T> {
        val request = client.get {
            buildRequest(path, null, parameters, headers)
        }
        return handleResponse(serializer, request)
    }

    suspend fun <R> postDataWithAuth(
        path: String,
        postBody: Any,
        serializer: KSerializer<R>,
        parameters: Map<String, String> = mapOf(),
        token: String
    ): ApiResult<R> {
        return postData(
            path = path,
            postBody = postBody,
            serializer = serializer,
            parameters = parameters,
            headers = mapOf(AUTHORIZATION_HEADER to BEARER + token)
        )
    }

    suspend fun <R> postData(
        path: String,
        postBody: Any,
        serializer: KSerializer<R>,
        parameters: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf(),
    ): ApiResult<R> {
        val request = client.post{
            buildRequest(path, postBody, parameters, headers)
        }
        return handleResponse(serializer, request)
    }

    suspend fun <R> patchDataWithAuth(
        path: String,
        patchBody: Any,
        serializer: KSerializer<R>,
        parameters: Map<String, String> = mapOf(),
        token: String
    ): ApiResult<R> {
        return patchData(
            path = path,
            patchBody = patchBody,
            serializer = serializer,
            parameters = parameters,
            headers = mapOf(AUTHORIZATION_HEADER to BEARER + token)
        )
    }

    suspend fun <R> patchData(
        path: String,
        patchBody: Any,
        serializer: KSerializer<R>,
        parameters: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf(),
    ): ApiResult<R> {
        val request = client.patch {
            buildRequest(path, patchBody, parameters, headers)
        }
        return handleResponse(serializer, request)
    }

    suspend fun <R> handleResponse(
        serializer: KSerializer<R>,
        request: HttpResponse
    ): ApiResult<R> {
        return try {
            ApiResult.Success(json.decodeFromString(serializer, request.bodyAsText()))
        } catch (exception: ClientRequestException) {
            ApiResult.Error(ApiError(exception.response.status.value, exception.message))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

    @OptIn(InternalAPI::class)
    fun HttpRequestBuilder.buildRequest(
        path: String,
        body: Any?,
        parameters: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf()
    ) {
        if (body != null) {
            this.body = body
        }
        url {
            path(path)
        }
        parameters.forEach { parameterMap ->
            parameter(parameterMap.key, parameterMap.value)
        }
        headers.forEach { headerEntry ->
            header(headerEntry.key, headerEntry.value)
        }
    }


}