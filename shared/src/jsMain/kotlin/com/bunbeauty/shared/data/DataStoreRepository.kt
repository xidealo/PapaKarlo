package com.bunbeauty.shared.data

import com.bunbeauty.core.model.Delivery
import com.bunbeauty.core.model.Discount
import com.bunbeauty.core.model.Settings
import com.bunbeauty.core.model.UserCityUuid
import com.bunbeauty.shared.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

actual class DataStoreRepository :
    DataStoreRepo,
    KoinComponent {
    actual override val token: Flow<String?> = flow { emit(getToken()) }

    actual override suspend fun getToken(): String? = lsGet(TOKEN_KEY)

    actual override suspend fun saveToken(token: String) {
        lsSet(TOKEN_KEY, token)
    }

    actual override suspend fun clearToken() {
        lsRemove(TOKEN_KEY)
    }

    actual override val userUuid: Flow<String?> = flow { emit(getUserUuid()) }

    actual override suspend fun getUserUuid(): String? = lsGet(USER_UUID_KEY)

    actual override suspend fun saveUserUuid(userId: String) {
        lsSet(USER_UUID_KEY, userId)
    }

    actual override suspend fun clearUserUuid() {
        lsRemove(USER_UUID_KEY)
    }

    actual override val delivery: Flow<Delivery?> = flow { emit(getDelivery()) }

    actual override suspend fun getDelivery(): Delivery? =
        Delivery(
            cost = lsGet(DELIVERY_COST_KEY)?.toIntOrNull() ?: 0,
            forFree = lsGet(DELIVERY_FOR_FREE_KEY)?.toIntOrNull() ?: 0,
        )

    actual override suspend fun saveDelivery(delivery: Delivery) {
        lsSet(DELIVERY_COST_KEY, delivery.cost.toString())
        lsSet(DELIVERY_FOR_FREE_KEY, delivery.forFree.toString())
    }

    actual override val settings: Flow<Settings?> = flow { emit(getSettings()) }

    actual override suspend fun getSettings(): Settings? =
        Settings(
            userUuid = lsGet(SETTINGS_USER_UUID_KEY).orEmpty(),
            phoneNumber = lsGet(SETTINGS_PHONE_NUMBER_KEY).orEmpty(),
            email = lsGet(SETTINGS_EMAIL_KEY),
        )

    actual override suspend fun saveSettings(settings: Settings) {
        lsSet(SETTINGS_USER_UUID_KEY, settings.userUuid)
        lsSet(SETTINGS_PHONE_NUMBER_KEY, settings.phoneNumber)
        lsSet(SETTINGS_EMAIL_KEY, settings.email.orEmpty())
    }

    actual override val selectedPaymentMethodUuid: Flow<String?> =
        flow { emit(lsGet(SELECTED_PAYMENT_METHOD_UUID_KEY)) }

    actual override suspend fun saveSelectedPaymentMethodUuid(selectedPaymentMethodUuid: String) {
        lsSet(SELECTED_PAYMENT_METHOD_UUID_KEY, selectedPaymentMethodUuid)
    }

    actual override val withoutUtensils: Flow<Boolean?> = flow { emit(getWithoutUtensils()) }

    actual override suspend fun getWithoutUtensils(): Boolean? = lsGet(WITHOUT_UTENSILS_KEY)?.toBoolean()

    actual override suspend fun saveWithoutUtensils(withoutUtensils: Boolean) {
        lsSet(WITHOUT_UTENSILS_KEY, withoutUtensils.toString())
    }

    actual override suspend fun clearWithoutUtensils() {
        lsRemove(WITHOUT_UTENSILS_KEY)
    }

    actual override val selectedCityUuid: Flow<String?> = flow { emit(getSelectedCityUuid()) }

    actual override suspend fun getSelectedCityUuid(): String? = lsGet(SELECTED_CITY_UUID_KEY)

    actual override suspend fun saveSelectedCityUuid(cityUuid: String) {
        lsSet(SELECTED_CITY_UUID_KEY, cityUuid)
    }

    actual override val discount: Flow<Discount?> = flow { emit(getDiscount()) }

    actual override suspend fun getDiscount(): Discount? =
        Discount(
            firstOrderDiscount = lsGet(FIRST_DISCOUNT_KEY)?.toIntOrNull(),
        )

    actual override suspend fun saveDiscount(discount: Discount) {
        discount.firstOrderDiscount?.let { firstOrderDiscount ->
            lsSet(FIRST_DISCOUNT_KEY, firstOrderDiscount.toString())
        } ?: lsRemove(FIRST_DISCOUNT_KEY)
    }

    actual override suspend fun clearUserData() {
        lsRemove(TOKEN_KEY)
        lsRemove(USER_UUID_KEY)
        lsRemove(SETTINGS_USER_UUID_KEY)
        lsRemove(SETTINGS_PHONE_NUMBER_KEY)
        lsRemove(SETTINGS_EMAIL_KEY)
        clearWithoutUtensils()
    }

    actual override fun observeUserAndCityUuid(): Flow<UserCityUuid> = flow { emit(getUserAndCityUuid()) }

    actual override suspend fun getUserAndCityUuid(): UserCityUuid =
        UserCityUuid(
            userUuid = getUserUuid().orEmpty(),
            cityUuid = getSelectedCityUuid().orEmpty(),
        )

    actual override val recommendationMaxVisible: Flow<Int?> =
        flow { emit(getRecommendationMaxVisible()) }

    actual override suspend fun getRecommendationMaxVisible(): Int? = lsGet(RECOMMENDATION_MAX_VISIBLE_KEY)?.toIntOrNull()

    actual override suspend fun saveRecommendationMaxVisible(recommendationMaxVisible: Int) {
        lsSet(RECOMMENDATION_MAX_VISIBLE_KEY, recommendationMaxVisible.toString())
    }

    actual override val userCafeUuid: Flow<String?> = flow { emit(getUserCafeUuid()) }

    actual override suspend fun getUserCafeUuid(): String? = lsGet(USER_CAFE_UUID_KEY)

    actual override suspend fun saveUserCafeUuid(userCafeUuid: String) {
        lsSet(USER_CAFE_UUID_KEY, userCafeUuid)
    }

    private fun lsGet(key: String): String? {
        val value: String? = js("window.localStorage.getItem(key)")
        return value
    }

    private fun lsSet(
        key: String,
        value: String,
    ) {
        js("window.localStorage.setItem(key, value)")
    }

    private fun lsRemove(key: String) {
        js("window.localStorage.removeItem(key)")
    }

    companion object {
        private const val TOKEN_KEY = "TOKEN_KEY"
        private const val SELECTED_CITY_UUID_KEY = "SELECTED_CITY_UUID_KEY"
        private const val USER_UUID_KEY = "USER_UUID_KEY"
        private const val DELIVERY_COST_KEY = "DELIVERY_COST_KEY"
        private const val DELIVERY_FOR_FREE_KEY = "DELIVERY_FOR_FREE_KEY"
        private const val SETTINGS_USER_UUID_KEY = "SETTINGS_USER_UUID_KEY"
        private const val SETTINGS_PHONE_NUMBER_KEY = "SETTINGS_PHONE_NUMBER_KEY"
        private const val SETTINGS_EMAIL_KEY = "SETTINGS_EMAIL_KEY"
        private const val SELECTED_PAYMENT_METHOD_UUID_KEY = "SELECTED_PAYMENT_METHOD_UUID_KEY"
        private const val WITHOUT_UTENSILS_KEY = "WITHOUT_UTENSILS_KEY"
        private const val FIRST_DISCOUNT_KEY = "FIRST_DISCOUNT_KEY"
        private const val RECOMMENDATION_MAX_VISIBLE_KEY = "RECOMMENDATION_MAX_VISIBLE_KEY"
        private const val USER_CAFE_UUID_KEY = "USER_CAFE_UUID_KEY"
    }
}
