package com.bunbeauty.shared.domain.feature.payment

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod
import kotlinx.coroutines.flow.firstOrNull

class GetSelectablePaymentMethodListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val getPaymentMethodListUseCase: GetPaymentMethodListUseCase,
) {
    suspend operator fun invoke(): List<SelectablePaymentMethod> {
        val paymentMethodList = getPaymentMethodListUseCase()
        val selectedPaymentMethodUuid = dataStoreRepo.selectedPaymentMethodUuid.firstOrNull()

        return paymentMethodList.mapIndexed { index, paymentMethod ->
            SelectablePaymentMethod(
                paymentMethod = paymentMethod,
                isSelected = paymentMethod.uuid == selectedPaymentMethodUuid
            )
        }
    }
}
