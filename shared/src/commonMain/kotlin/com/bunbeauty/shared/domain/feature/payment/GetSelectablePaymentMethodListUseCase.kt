package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.shared.domain.repo.PaymentRepo
import kotlinx.coroutines.flow.firstOrNull

//TODO TEST
class GetSelectablePaymentMethodListUseCase(
    private val paymentRepo: PaymentRepo,
    private val dataStoreRepo: DataStoreRepo,
) {
    suspend operator fun invoke(): List<SelectablePaymentMethod> {
        val paymentMethodList = paymentRepo.getPaymentMethodList()
            .filterNot { paymentMethod -> paymentMethod.name == PaymentMethodName.UNKNOWN }
            .sortedBy { paymentMethod ->
                if (paymentMethod.valueToShow == null || paymentMethod.valueToCopy == null) {
                    0
                } else {
                    1
                }
            }

        val selectedPaymentMethodUuid = dataStoreRepo.selectedPaymentMethodUuid.firstOrNull()

        return paymentMethodList.mapIndexed { index, paymentMethod ->
            SelectablePaymentMethod(
                paymentMethod = paymentMethod,
                isSelected = paymentMethod.uuid == selectedPaymentMethodUuid
            )
        }
    }
}
