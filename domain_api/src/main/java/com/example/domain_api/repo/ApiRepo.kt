package com.example.domain_api.repo

import com.bunbeauty.common.ApiResult
import com.example.domain_api.model.server.*
import com.example.domain_api.model.server.login.AuthResponseServer
import com.example.domain_api.model.server.login.LoginPostServer
import com.example.domain_api.model.server.order.get.OrderServer
import com.example.domain_api.model.server.order.post.OrderPostServer
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.ProfileEmailServer

interface ApiRepo {

    suspend fun getForceUpdateVersion(): ApiResult<ForceUpdateVersionServer>

    suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>>
    suspend fun getCityList(): ApiResult<ListServer<CityServer>>
    suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>>
    suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>>

    suspend fun getDelivery(): ApiResult<DeliveryServer>
    suspend fun getProfile(token: String): ApiResult<ProfileServer>

    suspend fun postLogin(loginPostServer: LoginPostServer): ApiResult<AuthResponseServer>
    suspend fun postUserAddress(userAddress: UserAddressPostServer): ApiResult<AddressServer>
    suspend fun postOrder(order: OrderPostServer): ApiResult<OrderServer>

    suspend fun patchProfileEmail(
        profileUuid: String,
        profileEmailServer: ProfileEmailServer
    ): ApiResult<ProfileServer>

}