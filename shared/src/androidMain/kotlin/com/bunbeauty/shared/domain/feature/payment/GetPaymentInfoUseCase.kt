package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.BuildConfig
import com.bunbeauty.shared.Constants.PAPA_KARLO_FLAVOR_NAME
import com.bunbeauty.shared.Constants.PAPA_KARLO_PAYMENT_INFO
import com.bunbeauty.shared.Constants.YULIAR_FLAVOR_NAME
import com.bunbeauty.shared.Constants.YULIAR_PAYMENT_INFO
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

actual class GetPaymentInfoUseCase {
    actual operator fun invoke(): String {
        return when (BuildConfig.FLAVOR) {
            PAPA_KARLO_FLAVOR_NAME -> PAPA_KARLO_PAYMENT_INFO
            YULIAR_FLAVOR_NAME -> YULIAR_PAYMENT_INFO
            else -> throw UnknownFlavorException()
        }
    }
}