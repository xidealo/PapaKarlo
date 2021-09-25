package com.example.domain_api.repo

import com.bunbeauty.common.ApiResult
import com.example.domain_api.model.server.*
import com.example.domain_api.model.server.order.get.OrderServer
import com.example.domain_api.model.server.order.post.OrderPostServer
import com.example.domain_api.model.server.profile.get.ProfileServer
import com.example.domain_api.model.server.profile.patch.ProfileEmailServer
import com.example.domain_api.model.server.profile.post.ProfilePostServer

interface ApiRepo {

    suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>>
    suspend fun getCafeList(): ApiResult<ListServer<CafeServer>>
    suspend fun getCityList(): ApiResult<ListServer<CityServer>>
    suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>>
    suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>>

    suspend fun getDelivery(): ApiResult<DeliveryServer>
    suspend fun getProfileByUuid(userUuid: String): ApiResult<ProfileServer>

    suspend fun postProfile(profile: ProfilePostServer): ApiResult<ProfileServer>
    suspend fun postUserAddress(userAddress: UserAddressPostServer): ApiResult<UserAddressServer>
    suspend fun postOrder(order: OrderPostServer): ApiResult<OrderServer>

    suspend fun patchProfileEmail(
        profileUuid: String,
        profileEmailServer: ProfileEmailServer
    ): ApiResult<ProfileServer>

}