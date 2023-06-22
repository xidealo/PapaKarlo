package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.shared.domain.repo.PaymentRepo
import kotlinx.coroutines.flow.first

class GetSelectablePaymentMethodListUseCase(
    private val paymentRepo: PaymentRepo,
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke(): List<SelectablePaymentMethod> {
        val paymentMethodList = paymentRepo.getPaymentMethodList().sortedBy { paymentMethod ->
            if (paymentMethod.valueToShow == null || paymentMethod.valueToCopy == null) {
                0
            } else {
                1
            }
        }

        val selectedPaymentMethodUuid = dataStoreRepo.selectedPaymentMethodUuid.first()
        val hasSelectedItem =
            paymentMethodList.any { it.uuid == selectedPaymentMethodUuid }

        return paymentMethodList.mapIndexed { index, paymentMethod ->
            SelectablePaymentMethod(
                paymentMethod = paymentMethod,
                isSelected = if (hasSelectedItem) {
                    selectedPaymentMethodUuid == paymentMethod.uuid
                } else {
                    index == 0
                }
            )
        }
    }
}