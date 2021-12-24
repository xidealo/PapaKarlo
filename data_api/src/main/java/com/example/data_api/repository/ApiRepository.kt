package com.example.data_api.repository

import com.bunbeauty.common.ApiError
import com.bunbeauty.common.ApiResult
import com.bunbeauty.common.Constants.AUTHORIZATION_HEADER
import com.bunbeauty.common.Constants.BEARER
import com.bunbeauty.common.Constants.CITY_UUID_PARAMETER
import com.bunbeauty.common.Constants.COMPANY_UUID
import com.bunbeauty.common.Constants.COMPANY_UUID_PARAMETER
import com.example.domain_api.model.server.*
import com.example.domain_api.model.server.login.AuthResponseServer
import com.example.domain_api.model.server.login.LoginPostServer
import com.example.domain_api.model.server.order.get.OrderServer
import com.example.domain_api.model.server.order.post.OrderPostServer
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.PatchUserServer
import com.example.domain_api.repo.ApiRepo
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val client: HttpClient,
    private val json: Json
) : ApiRepo {

    // GET

    override suspend fun getForceUpdateVersion(): ApiResult<ForceUpdateVersionServer> {
        return getData(
            path = "force_update_version",
            serializer = ForceUpdateVersionServer.serializer(),
            parameters = mapOf(COMPANY_UUID_PARAMETER to COMPANY_UUID)
        )
    }

    override suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>> {
        return getData(
            path = "menu_product",
            serializer = ListServer.serializer(MenuProductServer.serializer()),
            parameters = mapOf(COMPANY_UUID_PARAMETER to COMPANY_UUID)
        )
    }

    override suspend fun getCityList(): ApiResult<ListServer<CityServer>> {
        return getData(
            path = "city",
            serializer = ListServer.serializer(CityServer.serializer()),
            parameters = mapOf(COMPANY_UUID_PARAMETER to COMPANY_UUID)
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
            parameters = mapOf(COMPANY_UUID_PARAMETER to COMPANY_UUID)
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

    override suspend fun postUserAddress(userAddress: UserAddressPostServer): ApiResult<AddressServer> {
        return postData(
            path = "address",
            postBody = userAddress,
            serializer = AddressServer.serializer()
        )
    }

    override suspend fun postOrder(order: OrderPostServer): ApiResult<OrderServer> {
        return postData(
            path = "user_order",
            postBody = order,
            serializer = OrderServer.serializer()
        )
    }

    // PATCH

    override suspend fun patchProfileEmail(
        profileUuid: String,
        patchUserServer: PatchUserServer
    ): ApiResult<ProfileServer> {
        return patchData(
            path = "profile",
            body = patchUserServer,
            serializer = ProfileServer.serializer(),
            parameters = hashMapOf("uuid" to profileUuid)
        )
    }

    // COMMON

    suspend fun <T : Any> getDataWithAuth(
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

    suspend fun <T : Any> getData(
        path: String,
        serializer: KSerializer<T>,
        parameters: Map<String, String> = mapOf(),
        headers: Map<String, String> = mapOf(),
    ): ApiResult<T> {
        return try {
            ApiResult.Success(
                json.decodeFromString(
                    serializer,
                    client.get<HttpStatement> {
                        url {
                            path(path)
                        }
                        parameters.forEach { parameterEntry ->
                            parameter(parameterEntry.key, parameterEntry.value)
                        }
                        headers.forEach { headerEntry ->
                            header(headerEntry.key, headerEntry.value)
                        }
                    }.execute().readText()
                )
            )
        } catch (exception: ClientRequestException) {
            ApiResult.Error(ApiError(exception.response.status.value, exception.message))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

    suspend fun <T : Any, R> postData(
        path: String,
        postBody: T,
        serializer: KSerializer<R>,
        parameters: Map<String, String> = mapOf()
    ): ApiResult<R> {
        return try {
            ApiResult.Success(
                json.decodeFromString(
                    serializer,
                    client.post<HttpStatement>(body = postBody) {
                        contentType(ContentType.Application.Json)
                        url {
                            path(path)
                        }
                        parameters.forEach { parameterMap ->
                            parameter(parameterMap.key, parameterMap.value)
                        }
                    }.execute().readText()
                )
            )
        } catch (exception: ClientRequestException) {
            ApiResult.Error(ApiError(exception.response.status.value, exception.message))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

    suspend fun <T : Any, R> patchData(
        path: String,
        body: T,
        serializer: KSerializer<R>,
        parameters: Map<String, String> = mapOf()
    ): ApiResult<R> {
        return try {
            ApiResult.Success(
                json.decodeFromString(
                    serializer,
                    client.patch<HttpStatement>(body = body) {
                        url {
                            path(path)
                        }
                        parameters.forEach { parameterMap ->
                            parameter(parameterMap.key, parameterMap.value)
                        }
                    }.execute().readText()
                )
            )
        } catch (exception: ClientRequestException) {
            ApiResult.Error(ApiError(exception.response.status.value, exception.message))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

}