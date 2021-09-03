package com.bunbeauty.data.repository

import com.bunbeauty.domain.model.firebase.MenuProductFirebase
import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.firebase.address.UserAddressFirebase
import com.bunbeauty.domain.model.firebase.cafe.CafeFirebase
import com.bunbeauty.domain.model.firebase.order.OrderFirebase
import com.bunbeauty.domain.model.firebase.order.UserOrderFirebase
import com.bunbeauty.domain.model.ktor.CafeServer
import com.bunbeauty.domain.model.ui.Delivery
import com.bunbeauty.domain.repo.ApiRepo
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getCafeList(): Flow<List<CafeFirebase>> {
        return flow {
            val request = client.get<HttpStatement> {
                url {
                    path("/cafe/all/")
                }
            }
            val t = request.execute().readText()
            print(t)
            emit(listOf(CafeFirebase()))
        }
    }

    override fun getCafeServerList(): Flow<List<CafeServer>> {
        return flow {
            val request = client.get<HttpStatement> {
                url {
                    path("/cafe/all/")
                }
            }

            emit(
                json.decodeFromString(
                    ListSerializer(CafeServer.serializer()),
                    request.execute().readText()
                )
            )
        }
    }

    override fun getMenuProductList(): Flow<List<MenuProductFirebase>> {
        return flow {

        }
    }

    override fun getDelivery(): Flow<Delivery?> {
        return flow {

        }
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

}