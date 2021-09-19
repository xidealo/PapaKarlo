package com.example.domain_api.repo

import com.bunbeauty.common.ApiResult
import com.example.domain_api.model.server.*

interface ApiRepo {

    suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>>
    suspend fun getCafeList(): ApiResult<ListServer<CafeServer>>
    suspend fun getCityList(): ApiResult<ListServer<CityServer>>
    suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>>
    suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>>

    suspend fun getDelivery(): ApiResult<DeliveryServer>
    suspend fun getUserByUuid(userUuid: String): ApiResult<ProfileServer>

    suspend fun postUser(profile: ProfileServer): ApiResult<ProfileServer>
    suspend fun postUserAddress(userAddress: UserAddressServer): ApiResult<UserAddressServer>

    suspend fun patchUserEmail(
        userUuid: String,
        userEmailServer: UserEmailServer
    ): ApiResult<ProfileServer>

}