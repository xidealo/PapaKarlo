package com.example.data_api.repository

import com.bunbeauty.common.ApiError
import com.bunbeauty.common.ApiResult
import com.example.domain_api.model.server.*
import com.example.domain_api.repo.ApiRepo
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val client: HttpClient,
    private val json: Json
) : ApiRepo {

    // GET

    override suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>> {
        return getData(
            path = "menuProduct",
            serializer = ListServer.serializer(MenuProductServer.serializer())
        )
    }

    override suspend fun getCafeList(): ApiResult<ListServer<CafeServer>> {
        return getData(
            path = "cafe",
            serializer = ListServer.serializer(CafeServer.serializer())
        )
    }

    override suspend fun getCityList(): ApiResult<ListServer<CityServer>> {
        return getData(
            path = "city",
            serializer = ListServer.serializer(CityServer.serializer())
        )
    }

    override suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>> {
        return getData(
            path = "cafe",
            serializer = ListServer.serializer(CafeServer.serializer()),
            parameters = hashMapOf("cityUuid" to cityUuid)
        )
    }

    override suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>> {
        return getData(
            path = "street",
            serializer = ListServer.serializer(StreetServer.serializer()),
            parameters = hashMapOf("cityUuid" to cityUuid)
        )
    }

    override suspend fun getDelivery(): ApiResult<DeliveryServer> {
        return getData(path = "delivery", serializer = DeliveryServer.serializer())
    }

    override suspend fun getUserByUuid(userUuid: String): ApiResult<ProfileServer> {
        return getData(
            path = "profile",
            serializer = ProfileServer.serializer(),
            parameters = hashMapOf("uuid" to userUuid)
        )
    }

    // POST

    override suspend fun postUser(profile: ProfileServer): ApiResult<ProfileServer> {
        return postData(
            path = "profile",
            postBody = profile,
            serializer = ProfileServer.serializer()
        )
    }

    override suspend fun postUserAddress(userAddress: UserAddressPostServer): ApiResult<UserAddressServer> {
        return postData(
            path = "address",
            postBody = userAddress,
            serializer = UserAddressServer.serializer()
        )
    }

    // PATCH

    override suspend fun patchUserEmail(
        userUuid: String,
        userEmailServer: UserEmailServer
    ): ApiResult<ProfileServer> {
        return patchData(
            path = "/profile",
            body = userEmailServer,
            serializer = ProfileServer.serializer(),
            parameters = hashMapOf("uuid" to userUuid)
        )
    }

    // COMMON

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

    suspend fun <T : Any, R> postData(
        path: String,
        postBody: T,
        serializer: KSerializer<R>,
        parameters: HashMap<String, String> = hashMapOf()
    ): ApiResult<R> {
        return try {
//            client.post<HttpResponse> {
//                contentType(ContentType.Application.Json)
//                body = postBody
//                url {
//                    path(path)
//                }
////                parameters.forEach { parameterMap ->
////                    parameter(parameterMap.key, parameterMap.value)
////                }
//            }
//                //.execute()
//            ApiResult.Success(Any() as R)
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
            ApiResult.Error(ApiError(exception.response.status.value, exception.message ?: "-"))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

    suspend fun <T : Any, R : Any> patchData(
        path: String,
        body: T,
        serializer: KSerializer<R>,
        parameters: HashMap<String, String> = hashMapOf()
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
            ApiResult.Error(ApiError(exception.response.status.value, exception.message ?: "-"))
        } catch (exception: Exception) {
            ApiResult.Error(ApiError(0, exception.message ?: "-"))
        }
    }

}