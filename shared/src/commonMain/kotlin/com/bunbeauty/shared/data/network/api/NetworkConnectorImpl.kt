package com.bunbeauty.shared.data.network.api

import com.bunbeauty.core.ApiError
import com.bunbeauty.core.ApiResult
import com.bunbeauty.core.Constants.AUTHORIZATION_HEADER
import com.bunbeauty.core.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.core.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.core.Constants.QUERY_PARAMETER
import com.bunbeauty.core.Constants.UUID_PARAMETER
import com.bunbeauty.core.domain.exeptions.FoodDeliveryNetworkException
import com.bunbeauty.shared.data.CompanyUuidProvider
import com.bunbeauty.shared.data.network.logger.NetworkErrorLogger
import com.bunbeauty.shared.data.network.model.AddressServer
import com.bunbeauty.shared.data.network.model.CafeServer
import com.bunbeauty.shared.data.network.model.CategoryServer
import com.bunbeauty.shared.data.network.model.CityServer
import com.bunbeauty.shared.data.network.model.DiscountServer
import com.bunbeauty.shared.data.network.model.ForceUpdateVersionServer
import com.bunbeauty.shared.data.network.model.LinkServer
import com.bunbeauty.shared.data.network.model.ListServer
import com.bunbeauty.shared.data.network.model.MenuProductServer
import com.bunbeauty.shared.data.network.model.PaymentMethodServer
import com.bunbeauty.shared.data.network.model.PaymentServer
import com.bunbeauty.shared.data.network.model.RecommendationDataServer
import com.bunbeauty.shared.data.network.model.SettingsServer
import com.bunbeauty.shared.data.network.model.StreetServer
import com.bunbeauty.shared.data.network.model.SuggestionServer
import com.bunbeauty.shared.data.network.model.UpdateNotificationTokenRequest
import com.bunbeauty.shared.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.data.network.model.login.AuthResponseServer
import com.bunbeauty.shared.data.network.model.login.AuthSessionServer
import com.bunbeauty.shared.data.network.model.login.CodeRequestServer
import com.bunbeauty.shared.data.network.model.login.CodeServer
import com.bunbeauty.shared.data.network.model.order.get.LightOrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderCodeServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderUpdateServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.data.network.socket.SocketService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.timeout
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

private const val COMMON_TIMEOUT = 7000L
private const val FORCE_UPDATE_TIMEOUT = 5000L

internal class NetworkConnectorImpl(
    private val client: HttpClient,
    private val socketService: SocketService,
    private val companyUuidProvider: CompanyUuidProvider,
    private val errorLogger: NetworkErrorLogger,
) : KoinComponent,
    NetworkConnector {
    override suspend fun getForceUpdateVersion(): ApiResult<ForceUpdateVersionServer> =
        getData(path = "force_update_version", parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuidProvider.companyUuid), timeout = FORCE_UPDATE_TIMEOUT)

    override suspend fun getDiscount(): ApiResult<DiscountServer> =
        getData(path = "discount", parameters = mapOf(COMPANY_UUID_PARAMETER to companyUuidProvider.companyUuid))

    private suspend inline fun <reified R> getData(path: String, parameters: Map<String, Any> = mapOf(), token: String? = null, timeout: Long = COMMON_TIMEOUT): ApiResult<R> = safeCall { client.get { buildRequest(path = path, parameters = parameters, token = token, timeout = timeout) } }

    private suspend inline fun <reified R> safeCall(networkCall: () -> HttpResponse): ApiResult<R> = try { ApiResult.Success(networkCall().body()) } catch (exception: FoodDeliveryNetworkException) { throw exception } catch (exception: ClientRequestException) { ApiResult.Error(ApiError(exception.response.status.value, exception.message)) } catch (exception: Throwable) { ApiResult.Error(ApiError(0, exception.message.toString())) }

    private fun HttpRequestBuilder.buildRequest(path: String, parameters: Map<String, Any> = mapOf(), body: Any? = null, token: String? = null, timeout: Long) { if (body != null) setBody(body); url { path(path) }; parameters.forEach { parameter(it.key, it.value) }; if (token != null) header(AUTHORIZATION_HEADER, "Bearer $token"); timeout { requestTimeoutMillis = timeout; connectTimeoutMillis = timeout; socketTimeoutMillis = timeout } }
}
