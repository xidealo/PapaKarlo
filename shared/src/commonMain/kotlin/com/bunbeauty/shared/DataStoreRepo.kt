package com.bunbeauty.shared

import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.model.Settings
import com.bunbeauty.shared.domain.model.UserCityUuid
import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    val token: Flow<String?>
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)

    @Deprecated("use clearUserData")
    suspend fun clearToken()

    val userUuid: Flow<String?>
    suspend fun getUserUuid(): String?
    suspend fun saveUserUuid(userId: String)

    @Deprecated("use clearUserData")
    suspend fun clearUserUuid()

    val delivery: Flow<Delivery?>
    suspend fun getDelivery(): Delivery?
    suspend fun saveDelivery(delivery: Delivery)

    val settings: Flow<Settings?>
    suspend fun getSettings(): Settings?
    suspend fun saveSettings(settings: Settings)

    val selectedPaymentMethodUuid: Flow<String?>
    suspend fun saveSelectedPaymentMethodUuid(selectedPaymentMethodUuid: String)

    val selectedCityUuid: Flow<String?>
    suspend fun saveSelectedCityUuid(cityUuid: String)
    suspend fun getSelectedCityUuid(): String?

    val discount: Flow<Discount?>
    suspend fun getDiscount(): Discount?
    suspend fun saveDiscount(discount: Discount)

    val recommendationMaxVisible: Flow<Int?>
    suspend fun getRecommendationMaxVisible(): Int?
    suspend fun saveRecommendationMaxVisible(recommendationMaxVisible: Int)

    val userCafeUuid: Flow<String?>
    suspend fun getUserCafeUuid(): String?
    suspend fun saveUserCafeUuid(userCafeUuid: String)

    fun observeUserAndCityUuid(): Flow<UserCityUuid>
    suspend fun getUserAndCityUuid(): UserCityUuid

    suspend fun clearUserData()
}
