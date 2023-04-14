package com.bunbeauty.shared.data.network.api

import com.bunbeauty.shared.Constants.AUTHORIZATION_HEADER
import com.bunbeauty.shared.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.shared.Constants.COMPANY_UUID_PARAMETER
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
import com.bunbeauty.shared.data.network.model.PaymentMethodServer
import com.bunbeauty.shared.data.network.model.PaymentServer
import com.bunbeauty.shared.data.network.model.SettingsServer
import com.bunbeauty.shared.data.network.model.StreetServer
import com.bunbeauty.shared.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.data.network.model.login.AuthResponseServer
import com.bunbeauty.shared.data.network.model.login.LoginPostServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderUpdateServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.data.network.socket.SocketService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class NetworkConnectorImpl(
    private val client: HttpClient,
    private val socketService: SocketService
) : KoinComponent, NetworkConnector {

    // GET

    override suspend fun getForceUpdateVersion(): ApiResult<ForceUpdateVersionServer> {
        return getData(
            path = "force_update_version",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCategoryList(): ApiResult<ListServer<CategoryServer>> {
        return getData(
            path = "category",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>> {
        return getData(
            path = "menu_product",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCityList(): ApiResult<ListServer<CityServer>> {
        return getData(
            path = "city",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>> {
        return getData(
            path = "cafe",
            parameters = hashMapOf(CITY_UUID_PARAMETER to cityUuid)
        )
    }

    override suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>> {
        return getData(
            path = "street",
            parameters = hashMapOf(CITY_UUID_PARAMETER to cityUuid)
        )
    }

    override suspend fun getDelivery(): ApiResult<DeliveryServer> {
        return getData(
            path = "delivery",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    override suspend fun getUserAddressListByCityUuid(
        token: String,
        cityUuid: String
    ): ApiResult<ListServer<AddressServer>> {
        return getData(
            path = "address",
            parameters = mapOf(CITY_UUID_PARAMETER to cityUuid),
            token = token
        )
    }

    override suspend fun getPayment(token: String): ApiResult<PaymentServer> {
        return getData(
            path = "payment",
            token = token
        )
    }

    override suspend fun getProfile(token: String): ApiResult<ProfileServer> {
        return getData(
            path = "client",
            token = token
        )
    }

    override suspend fun getOrderList(
        token: String,
        count: Int?,
        uuid: String?,
    ): ApiResult<ListServer<OrderServer>> {
        return getData(
            path = "v2/client/order",
            parameters = buildMap {
                if (count != null) {
                    put("count", count)
                }
                if (uuid != null) {
                    put("uuid", uuid)
                }
            },
            token = token
        )
    }

    override suspend fun getSettings(token: String): ApiResult<SettingsServer> {
        return getData(
            path = "client/settings",
            token = token
        )
    }

    override suspend fun getPaymentMethodList(token: String): ApiResult<List<PaymentMethodServer>> {
        return getData(
            path = "payment_method",
            parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuid)
        )
    }

    // POST

    override suspend fun postLogin(loginPostServer: LoginPostServer): ApiResult<AuthResponseServer> {
        return postData(
            path = "client/login",
            postBody = loginPostServer,
        )
    }

    override suspend fun postUserAddress(
        token: String,
        userAddress: UserAddressPostServer
    ): ApiResult<AddressServer> {
        return postData(
            path = "address",
            postBody = userAddress,
            token = token
        )
    }

    override suspend fun postOrder(token: String, order: OrderPostServer): ApiResult<OrderServer> {
        return postData(
            path = "v2/order",
            postBody = order,
            token = token
        )
    }

    // PATCH

    override suspend fun patchSettings(
        token: String,
        patchUserServer: PatchUserServer
    ): ApiResult<SettingsServer> {
        return patchData(
            path = "client/settings",
            patchBody = patchUserServer,
            token = token
        )
    }

    // WEB_SOCKET

    override suspend fun startOrderUpdatesObservation(token: String): Pair<String?, Flow<OrderUpdateServer>> {
        return socketService.observeSocketMessages(
            path = "client/order/v2/subscribe",
            serializer = OrderUpdateServer.serializer(),
            token = token
        )
    }

    override suspend fun stopOrderUpdatesObservation(uuid: String) {
        socketService.closeSession(uuid)
    }

    // COMMON

    private suspend inline fun <reified R> getData(
        path: String,
        parameters: Map<String, Any> = mapOf(),
        token: String? = null
    ): ApiResult<R> {
        return safeCall {
            client.get {
                buildRequest(
                    path = path,
                    parameters = parameters,
                    token = token
                )
            }
        }
    }

    private suspend inline fun <reified R> postData(
        path: String,
        parameters: Map<String, String> = mapOf(),
        postBody: Any,
        token: String? = null
    ): ApiResult<R> {
        return safeCall {
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

    private suspend inline fun <reified R> patchData(
        path: String,
        patchBody: Any,
        parameters: Map<String, String> = mapOf(),
        token: String? = null
    ): ApiResult<R> {
        return safeCall {
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

    private suspend inline fun <reified R> safeCall(
        networkCall: () -> HttpResponse
    ): ApiResult<R> {
        return try {
            ApiResult.Success(networkCall().body())
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