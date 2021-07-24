package com.example.data_api.repository

import com.bunbeauty.common.ApiError
import com.bunbeauty.common.ApiResult
import com.example.domain_api.model.server.*
import com.example.domain_api.repo.ApiRepo
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val client: HttpClient,
    private val json: Json
) : ApiRepo {

    override suspend fun getMenuProductList(): ApiResult<List<MenuProductServer>> {
        return getDataList(path = "/menuProduct/all", serializer = MenuProductServer.serializer())
    }

    override suspend fun getCafeList(): ApiResult<List<CafeServer>> {
        return getDataList(path = "/cafe/all", CafeServer.serializer())
    }

    override suspend fun getCityList(): ApiResult<List<CityServer>> {
        return getDataList(path = "/city/all", CityServer.serializer())
    }

    override suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<List<CafeServer>> {
        return getDataList(
            path = "/cafe",
            serializer = CafeServer.serializer(),
            parameters = hashMapOf("cityUuid" to cityUuid)
        )
    }

    override suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<List<StreetServer>> {
        return getDataList(
            path = "/street",
            serializer = StreetServer.serializer(),
            parameters = hashMapOf("cityUuid" to cityUuid)
        )
    }

    override suspend fun getDelivery(): ApiResult<DeliveryServer> {
        return getData(path = "/delivery", serializer = DeliveryServer.serializer())
    }

    override suspend fun getUserByUuid(userUuid: String): ApiResult<UserServer> {
        return getData(
            path = "/profile",
            serializer = UserServer.serializer(),
            parameters = hashMapOf("uuid" to userUuid)
        )
    }

    override suspend fun postUser(user: UserServer): ApiResult<UserServer> {
        return postData(
            path = "/profile",
            body = user,
            serializer = UserServer.serializer()
        )
    }

    override suspend fun postUserAddress(userAddress: UserAddressServer): ApiResult<UserAddressServer> {
        return postData(
            path = "/address",
            body = userAddress,
            serializer = UserAddressServer.serializer()
        )
    }

    suspend fun <T> getDataList(
        path: String,
        serializer: KSerializer<T>,
        parameters: HashMap<String, String> = hashMapOf()
    ): ApiResult<List<T>> {
        return try {
            ApiResult.Success(
                json.decodeFromString(
                    ListSerializer(serializer),
                    client.get<HttpStatement> {
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
            ApiResult.Error(ApiError(exception.response.status.value, exception.message ?: "-"))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

    suspend fun <T : Any> getData(
        path: String,
        serializer: KSerializer<T>,
        parameters: HashMap<String, String> = hashMapOf()
    ): ApiResult<T> {
        return try {
            ApiResult.Success(
                json.decodeFromString(
                    serializer,
                    client.get<HttpStatement> {
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
            ApiResult.Error(ApiError(exception.response.status.value, exception.message ?: "-"))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

    suspend fun <T : Any> postData(
        path: String,
        body: T,
        serializer: KSerializer<T>,
        parameters: HashMap<String, String> = hashMapOf()
    ): ApiResult<T> {
        return try {
            ApiResult.Success(
                json.decodeFromString(
                    serializer,
                    client.post<HttpStatement>(
                        body = body
                    ) {
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
            ApiResult.Error(ApiError(exception.response.status.value, exception.message ?: "-"))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

}