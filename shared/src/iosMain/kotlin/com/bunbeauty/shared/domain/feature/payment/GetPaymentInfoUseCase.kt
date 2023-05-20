package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.Constants.PAPA_KARLO_TARGET_NAME
import com.bunbeauty.shared.Constants.YULIAR_TARGET_NAME
import com.bunbeauty.shared.Constants.PAPA_KARLO_PAYMENT_INFO
import com.bunbeauty.shared.Constants.YULIAR_PAYMENT_INFO
import com.bunbeauty.shared.data.targetName
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

actual class GetPaymentInfoUseCase {
    actual operator fun invoke(): String {
        return when (targetName) {
            PAPA_KARLO_TARGET_NAME -> PAPA_KARLO_PAYMENT_INFO
            YULIAR_TARGET_NAME -> YULIAR_PAYMENT_INFO
            else -> throw UnknownFlavorException()
        }
    }
}