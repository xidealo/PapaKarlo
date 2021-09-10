package com.example.data_api.repository

import com.bunbeauty.common.ApiError
import com.bunbeauty.common.ApiResult
import com.bunbeauty.domain.model.User
import com.example.domain_api.model.server.CafeServer
import com.example.domain_api.model.server.DeliveryServer
import com.example.domain_api.model.server.MenuProductServer
import com.example.domain_api.model.server.UserServer
import com.example.domain_api.repo.ApiRepo
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val client: HttpClient,
    private val json: Json
) : ApiRepo {

    override suspend fun getCafeServerList(): List<CafeServer> {
        return getDataList(path = "/cafe/all/", CafeServer.serializer())
    }

    override suspend fun getCafeServerByCityList(city: String): List<CafeServer> {
        return getDataList(
            path = "/cafe",
            CafeServer.serializer(),
            hashMapOf(Pair("city", city))
        )
    }

    override suspend fun getMenuProductList(): List<MenuProductServer> {
        return getDataList(path = "/menuProduct/all/", MenuProductServer.serializer())
    }

    override suspend fun getDelivery(): ApiResult<DeliveryServer> {
        return getData(path = "/delivery/", DeliveryServer.serializer())
    }

    override suspend fun getUserByUuid(uuid: String): ApiResult<UserServer> {
        return getData(path = "/profile", UserServer.serializer(), hashMapOf(Pair("uuid", uuid)))
    }


    suspend fun <T> getDataList(
        path: String,
        serializer: KSerializer<T>,
        parameters: HashMap<String, String> = hashMapOf()
    ): List<T> {
        return json.decodeFromString(
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
        } catch (ex: ClientRequestException) {
            ApiResult.Error(ApiError(ex.response.status.value, ex.message ?: "no message text"))
        }
        finally {
            ApiResult.Error(ApiError(404,"No catch error"))
        }
    }

}