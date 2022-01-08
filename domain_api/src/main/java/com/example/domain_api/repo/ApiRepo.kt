package com.example.domain_api.repo

import com.bunbeauty.common.ApiResult
import com.example.domain_api.model.server.*
import com.example.domain_api.model.server.login.AuthResponseServer
import com.example.domain_api.model.server.login.LoginPostServer
import com.example.domain_api.model.server.order.get.OrderServer
import com.example.domain_api.model.server.order.post.OrderPostServer
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.PatchUserServer
import kotlinx.coroutines.flow.Flow

interface ApiRepo {

    suspend fun getForceUpdateVersion(): ApiResult<ForceUpdateVersionServer>

    suspend fun getCategoryList(): ApiResult<ListServer<CategoryServer>>
    suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>>
    suspend fun getCityList(): ApiResult<ListServer<CityServer>>
    suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>>
    suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>>

    suspend fun getDelivery(): ApiResult<DeliveryServer>
    suspend fun getProfile(token: String): ApiResult<ProfileServer>

    suspend fun postLogin(loginPostServer: LoginPostServer): ApiResult<AuthResponseServer>
    suspend fun postUserAddress(
        token: String,
        userAddress: UserAddressPostServer,
    ): ApiResult<AddressServer>

    suspend fun postOrder(token: String, order: OrderPostServer): ApiResult<OrderServer>

    suspend fun patchProfileEmail(
        token: String,
        userUuid: String,
        patchUserServer: PatchUserServer
    ): ApiResult<ProfileServer>

    fun subscribeOnOrderUpdates(token: String): Flow<OrderServer>
    suspend fun unsubscribeOnOrderUpdates()

}