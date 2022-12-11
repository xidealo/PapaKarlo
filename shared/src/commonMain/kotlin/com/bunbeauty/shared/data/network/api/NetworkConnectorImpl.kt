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
import com.bunbeauty.shared.data.network.model.AddressServer
import com.bunbeauty.shared.data.network.model.CafeServer
import com.bunbeauty.shared.data.network.model.CategoryServer
import com.bunbeauty.shared.data.network.model.CityServer
import com.bunbeauty.shared.data.network.model.DeliveryServer
import com.bunbeauty.shared.data.network.model.ForceUpdateVersionServer
import com.bunbeauty.shared.data.network.model.ListServer
import com.bunbeauty.shared.data.network.model.MenuProductServer
import com.bunbeauty.shared.data.network.model.PaymentServer
import com.bunbeauty.shared.data.network.model.StreetServer
import com.bunbeauty.shared.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.data.network.model.login.AuthResponseServer
import com.bunbeauty.shared.data.network.model.login.LoginPostServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.data.network.socket.SocketService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSocketException
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.path
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkConnectorImpl(
    private val client: HttpClient,
    private val json: Json,
    private val socketService: SocketService
) : KoinComponent, NetworkConnector {

    // GET

    override suspend fun getForceUpdateVersion(): ApiResult<ForceUpdateVersionServer> {
        return getData(
            serializer = ForceUpdateVersionServer.serializer(),
            path = "force_update_version",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun patchDisableUser(
        token: String,
        patchUserServer: PatchUserServer
    ): ApiResult<ProfileServer> {
        return patchData(
            path = "client",
            patchBody = patchUserServer,
            serializer = ProfileServer.serializer(),
            token = token
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

    override suspend fun getUserAddressList(token: String): ApiResult<ListServer<AddressServer>> {
        return getData(
            path = "address",
            serializer = ListServer.serializer(AddressServer.serializer()),
            token = token
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

    override suspend fun getOrderList(token: String, count: Int): ApiResult<ListServer<OrderServer>> {
        return getData(
            serializer = ListServer.serializer(OrderServer.serializer()),
            path = "v2/client/order",
            parameters = mapOf("count" to count.toString()),
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
            path = "v2/order",
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

    override suspend fun startOrderUpdatesObservation(token: String): Flow<OrderServer> {
        return socketService.observeSocketMessages(
            path = "client/order/subscribe",
            serializer = OrderServer.serializer(),
            token
        )
    }

    override suspend fun stopOrderUpdatesObservation() {
        socketService.closeSession("client/order/subscribe")
    }

    // COMMON

    suspend fun <T> getData(
        serializer: KSerializer<T>,
        path: String,
        parameters: Map<String, Any> = mapOf(),
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
            ApiResult.Error(ApiError(0, exception.message ?: "Bad Internet"))
        }
    }

    private fun HttpRequestBuilder.buildRequest(
        path: String,
        parameters: Map<String, Any> = mapOf(),
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