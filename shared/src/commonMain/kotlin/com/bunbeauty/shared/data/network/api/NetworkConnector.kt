package com.bunbeauty.shared.data.network.api

import com.bunbeauty.shared.data.network.ApiResult
import com.bunbeauty.shared.data.network.model.AddressServer
import com.bunbeauty.shared.data.network.model.CafeServer
import com.bunbeauty.shared.data.network.model.CategoryServer
import com.bunbeauty.shared.data.network.model.CityServer
import com.bunbeauty.shared.data.network.model.DeliveryServer
import com.bunbeauty.shared.data.network.model.ForceUpdateVersionServer
import com.bunbeauty.shared.data.network.model.ListServer
import com.bunbeauty.shared.data.network.model.MenuProductServer
import com.bunbeauty.shared.data.network.model.PaymentServer
import com.bunbeauty.shared.data.network.model.SettingsServer
import com.bunbeauty.shared.data.network.model.StreetServer
import com.bunbeauty.shared.data.network.model.UserAddressPostServer
import com.bunbeauty.shared.data.network.model.login.AuthResponseServer
import com.bunbeauty.shared.data.network.model.login.LoginPostServer
import com.bunbeauty.shared.data.network.model.order.get.OrderServer
import com.bunbeauty.shared.data.network.model.order.get.OrderUpdateServer
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
    suspend fun getOrderList(token: String, count: Int): ApiResult<ListServer<OrderServer>>
    suspend fun getSettings(token: String): ApiResult<SettingsServer>

    suspend fun postLogin(loginPostServer: LoginPostServer): ApiResult<AuthResponseServer>
    suspend fun postUserAddress(
        token: String,
        userAddress: UserAddressPostServer,
    ): ApiResult<AddressServer>

    suspend fun postOrder(token: String, order: OrderPostServer): ApiResult<OrderServer>

    suspend fun patchSettings(
        token: String,
        patchUserServer: PatchUserServer
    ): ApiResult<SettingsServer>

    suspend fun startOrderUpdatesObservation(token: String): Flow<OrderUpdateServer>
    suspend fun stopOrderUpdatesObservation()
}