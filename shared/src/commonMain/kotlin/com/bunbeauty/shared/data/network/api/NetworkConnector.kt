package com.bunbeauty.shared.data.network.api

import com.bunbeauty.shared.data.network.ApiResult
import com.bunbeauty.shared.data.network.model.*
import com.bunbeauty.shared.data.network.model.login.AuthResponseServer
import com.bunbeauty.shared.data.network.model.login.LoginPostServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.post.OrderPostServer
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import kotlinx.coroutines.flow.Flow

interface NetworkConnector {

    suspend fun getForceUpdateVersion(): ApiResult<ForceUpdateVersionServer>

    suspend fun getCategoryList(): ApiResult<ListServer<CategoryServer>>
    suspend fun getMenuProductList(): ApiResult<ListServer<MenuProductServer>>
    suspend fun getCityList(): ApiResult<ListServer<CityServer>>
    suspend fun getCafeListByCityUuid(cityUuid: String): ApiResult<ListServer<CafeServer>>
    suspend fun getStreetListByCityUuid(cityUuid: String): ApiResult<ListServer<StreetServer>>
    suspend fun getDelivery(): ApiResult<DeliveryServer>

    suspend fun getUserAddressList(token: String): ApiResult<ListServer<AddressServer>>
    suspend fun getPayment(token: String): ApiResult<PaymentServer>
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