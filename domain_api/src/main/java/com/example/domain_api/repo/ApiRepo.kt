package com.example.domain_api.repo

import com.bunbeauty.common.ApiResult
import com.example.domain_api.model.server.*

interface ApiRepo {

    suspend fun getMenuProductList(): ApiResult<List<MenuProductServer>>
    suspend fun getCafeList(): ApiResult<List<CafeServer>>
    suspend fun getCafeListByCity(city: String): ApiResult<List<CafeServer>>

    suspend fun getDelivery(): ApiResult<DeliveryServer>
    suspend fun getUserByUuid(userUuid: String): ApiResult<UserServer>

}