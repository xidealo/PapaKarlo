package com.example.domain_api.repo

import com.bunbeauty.common.ApiResult
import com.example.domain_api.model.server.*

interface ApiRepo {

    suspend fun getMenuProductList(): ApiResult<List<MenuProductServer>>
    suspend fun getCafeList(): ApiResult<List<CafeServer>>
    suspend fun getCityList(): ApiResult<List<CityServer>>
    suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<List<CafeServer>>
    suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<List<StreetServer>>

    suspend fun getDelivery(): ApiResult<DeliveryServer>
    suspend fun getUserByUuid(userUuid: String): ApiResult<UserServer>

    suspend fun postUser(user: UserServer): ApiResult<UserServer>
    suspend fun postUserAddress(userAddress: UserAddressServer): ApiResult<UserAddressServer>

    suspend fun patchUserEmail(
        userUuid: String,
        userEmailServer: UserEmailServer
    ): ApiResult<UserServer>

}