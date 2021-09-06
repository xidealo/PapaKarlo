package com.bunbeauty.data.repository

import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import com.bunbeauty.domain.model.firebase.order.OrderFirebase
import com.bunbeauty.domain.model.firebase.order.UserOrderFirebase
import com.bunbeauty.domain.model.ktor.CafeServer
import com.bunbeauty.domain.model.ktor.DeliveryServer
import com.bunbeauty.domain.model.ktor.MenuProductServer
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.repo.ApiRepo
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ApiRepositoryKtor @Inject constructor(
    private val client: HttpClient,
    private val json: Json
) : ApiRepo {

    override fun postOrder(orderFirebase: OrderFirebase, cafeUuid: String): String {
        return ""
    }

    override fun postUser(userUuid: String, userFirebase: UserFirebase) {
        TODO("Not yet implemented")
    }

    override fun postUserAddress(userAddress: UserAddressFirebase, userUuid: String): String {
        return ""
    }

    override fun updateUserEmail(userUuid: String, userEmail: String) {
        TODO("Not yet implemented")
    }

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

    override suspend fun getDelivery(): DeliveryServer {
        return getData(path = "/delivery/", DeliveryServer.serializer())
    }

    override fun getUser(userUuid: String): Flow<UserFirebase?> {
        return flow {

        }
    }

    override fun getOrder(userOrderFirebase: UserOrderFirebase): Flow<OrderFirebase?> {
        return flow {

        }
    }

    override fun observeOrder(userOrderFirebase: UserOrderFirebase): Flow<OrderFirebase?> {
        return flow {

        }
    }

    override fun removeOrderObservers() {
        //
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

    suspend fun <T> getData(
        path: String,
        serializer: KSerializer<T>,
        parameters: HashMap<String, String> = hashMapOf()
    ): T {
        return json.decodeFromString(
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
    }

}