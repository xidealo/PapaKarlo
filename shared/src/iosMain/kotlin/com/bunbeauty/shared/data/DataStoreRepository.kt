package com.bunbeauty.shared.data

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.model.Settings
import com.bunbeauty.shared.domain.model.UserCityUuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import platform.Foundation.NSUserDefaults

actual class DataStoreRepository : DataStoreRepo, KoinComponent {

    actual override val token: Flow<String?> = flow {
        emit(getToken())
    }

    actual override suspend fun getToken(): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey(TOKEN_KEY)
    }

    actual override suspend fun saveToken(token: String) {
        NSUserDefaults.standardUserDefaults.setObject(token, TOKEN_KEY)
    }

    actual override suspend fun clearToken() {
        NSUserDefaults.standardUserDefaults.removeObjectForKey(TOKEN_KEY)
    }

    actual override val delivery: Flow<Delivery?> = flow {
        emit(
            Delivery(
                cost = NSUserDefaults.standardUserDefaults.integerForKey(DELIVERY_COST_KEY).toInt(),
                forFree = NSUserDefaults.standardUserDefaults.integerForKey(
                    DELIVERY_FOR_FREE_KEY
                ).toInt()
            )
        )
    }

    actual override suspend fun getDelivery(): Delivery? {
        return delivery.firstOrNull()
    }

    actual override suspend fun saveDelivery(delivery: Delivery) {
        NSUserDefaults.standardUserDefaults.setObject(delivery.cost, DELIVERY_COST_KEY)
        NSUserDefaults.standardUserDefaults.setObject(delivery.forFree, DELIVERY_FOR_FREE_KEY)
    }

    actual override val userUuid: Flow<String?> = flow {
        emit(NSUserDefaults.standardUserDefaults.stringForKey(USER_UUID_KEY))
    }

    actual override suspend fun getUserUuid(): String? {
        return userUuid.firstOrNull()
    }

    actual override suspend fun saveUserUuid(userId: String) {
        NSUserDefaults.standardUserDefaults.setObject(userId, USER_UUID_KEY)
    }

    actual override suspend fun clearUserUuid() {
        NSUserDefaults.standardUserDefaults.removeObjectForKey(USER_UUID_KEY)
    }

    actual override val selectedCityUuid: Flow<String?> = flow {
        emit(NSUserDefaults.standardUserDefaults.stringForKey(SELECTED_CITY_UUID_KEY))
    }

    actual override suspend fun saveSelectedCityUuid(cityUuid: String) {
        NSUserDefaults.standardUserDefaults.setObject(cityUuid, SELECTED_CITY_UUID_KEY)
    }

    actual override suspend fun getSelectedCityUuid(): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey(SELECTED_CITY_UUID_KEY)
    }

    actual override val selectedPaymentMethodUuid: Flow<String?> = flow {
        emit(NSUserDefaults.standardUserDefaults.stringForKey(SELECTED_PAYMENT_METHOD_UUID_KEY))
    }

    actual override suspend fun saveSelectedPaymentMethodUuid(selectedPaymentMethodUuid: String) {
        NSUserDefaults.standardUserDefaults.setObject(
            selectedPaymentMethodUuid,
            SELECTED_PAYMENT_METHOD_UUID_KEY
        )
    }

    actual override val discount: Flow<Discount?> = flow {
        emit(
            Discount(
                firstOrderDiscount = NSUserDefaults.standardUserDefaults
                    .integerForKey(FIRST_DISCOUNT_KEY).toInt()
            )
        )
    }

    actual override suspend fun getDiscount(): Discount? {
        return discount.firstOrNull()
    }

    actual override suspend fun saveDiscount(discount: Discount) {
        NSUserDefaults.standardUserDefaults.setObject(
            discount.firstOrderDiscount,
            FIRST_DISCOUNT_KEY
        )
    }

    actual override fun observeUserAndCityUuid(): Flow<UserCityUuid> {
        return flow {
            emit(
                UserCityUuid(
                    userUuid = NSUserDefaults.standardUserDefaults.stringForKey(
                        USER_UUID_KEY
                    ).toString(),
                    cityUuid = NSUserDefaults.standardUserDefaults.stringForKey(
                        SELECTED_CITY_UUID_KEY
                    ).toString()
                )
            )
        }
    }

    actual override suspend fun getUserAndCityUuid(): UserCityUuid {
        return UserCityUuid(
            userUuid = getUserUuid() ?: "",
            cityUuid = getSelectedCityUuid() ?: ""
        )
    }

    actual override val settings: Flow<Settings?> = flow {
        emit(
            Settings(
                userUuid = NSUserDefaults.standardUserDefaults.stringForKey(
                    USER_UUID_KEY
                ).toString(),
                phoneNumber = NSUserDefaults.standardUserDefaults.stringForKey(
                    SETTINGS_PHONE_NUMBER_KEY
                ).toString(),
                email = NSUserDefaults.standardUserDefaults.stringForKey(
                    SETTINGS_EMAIL_KEY
                ).toString()
            )
        )
    }

    actual override suspend fun getSettings(): Settings? {
        return settings.firstOrNull()
    }

    actual override suspend fun saveSettings(settings: Settings) {
        NSUserDefaults.standardUserDefaults.setObject(settings.userUuid, SETTINGS_USER_UUID_KEY)
        NSUserDefaults.standardUserDefaults.setObject(
            settings.phoneNumber,
            SETTINGS_PHONE_NUMBER_KEY
        )
        NSUserDefaults.standardUserDefaults.setObject(settings.email, SETTINGS_EMAIL_KEY)
    }

    actual override val recommendationMaxVisible: Flow<Int?> = flow {
        emit(
            NSUserDefaults.standardUserDefaults.integerForKey(
                RECOMMENDATION_MAX_VISIBLE_KEY
            ).toInt()
        )
    }

    actual override suspend fun getRecommendationMaxVisible(): Int? {
        return recommendationMaxVisible.firstOrNull()
    }

    actual override suspend fun saveRecommendationMaxVisible(recommendationMaxVisible: Int) {
        NSUserDefaults.standardUserDefaults.setObject(
            recommendationMaxVisible,
            RECOMMENDATION_MAX_VISIBLE_KEY
        )
    }

    actual override val userCafeUuid: Flow<String?> = flow {
        emit(
            NSUserDefaults.standardUserDefaults.stringForKey(
                USER_CAFE_UUID_KEY
            )
        )
    }

    actual override suspend fun getUserCafeUuid(): String? {
        return userCafeUuid.firstOrNull()
    }

    actual override suspend fun saveUserCafeUuid(userCafeUuid: String) {
        NSUserDefaults.standardUserDefaults.setObject(
            userCafeUuid,
            USER_CAFE_UUID_KEY
        )
    }

    actual override suspend fun clearUserData() {
        NSUserDefaults.standardUserDefaults.removeObjectForKey(TOKEN_KEY)
        NSUserDefaults.standardUserDefaults.removeObjectForKey(USER_UUID_KEY)
        removeUserSettings()
    }

    private fun removeUserSettings() {
        NSUserDefaults.standardUserDefaults.removeObjectForKey(SETTINGS_USER_UUID_KEY)
        NSUserDefaults.standardUserDefaults.removeObjectForKey(SETTINGS_PHONE_NUMBER_KEY)
        NSUserDefaults.standardUserDefaults.removeObjectForKey(SETTINGS_EMAIL_KEY)
    }

    companion object {
        const val TOKEN_KEY = "TOKEN_KEY"
        const val SELECTED_CITY_UUID_KEY = "SELECTED_CITY_UUID_KEY"
        const val SELECTED_CITY_TIME_ZONE_KEY = "SELECTED_CITY_TIME_ZONE_KEY"
        const val USER_UUID_KEY = "USER_UUID_KEY"
        const val DELIVERY_COST_KEY = "DELIVERY_COST_KEY"
        const val DELIVERY_FOR_FREE_KEY = "DELIVERY_FOR_FREE_KEY"
        private const val SETTINGS_USER_UUID_KEY = "SETTINGS_USER_UUID_KEY"
        private const val SETTINGS_PHONE_NUMBER_KEY = "SETTINGS_PHONE_NUMBER_KEY"
        private const val SETTINGS_EMAIL_KEY = "SETTINGS_EMAIL_KEY"
        private const val SELECTED_PAYMENT_METHOD_UUID_KEY = "SELECTED_PAYMENT_METHOD_UUID_KEY"
        private const val FIRST_DISCOUNT_KEY = "FIRST_DISCOUNT_KEY"
        private const val RECOMMENDATION_MAX_VISIBLE_KEY = "RECOMMENDATION_MAX_VISIBLE_KEY"
        private const val USER_CAFE_UUID_KEY = "USER_CAFE_UUID_KEY"
    }
}
