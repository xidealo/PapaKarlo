package com.bunbeauty.domain.feature.payment

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.shared.domain.repo.PaymentRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetSelectablePaymentMethodListUseCaseTest {

    private val paymentRepo: PaymentRepo = mock()
    private val dataStoreRepo: DataStoreRepo = mock()
    private val useCase: GetSelectablePaymentMethodListUseCase =
        GetSelectablePaymentMethodListUseCase(paymentRepo, dataStoreRepo)

    @Test
    fun `should return selectable payment method list`() = runTest {
        val paymentMethodList = listOf(
            PaymentMethod("uuid1", PaymentMethodName.CARD, null, null),
            PaymentMethod("uuid2", PaymentMethodName.CASH, null, null),
            PaymentMethod("uuid3", PaymentMethodName.CARD_NUMBER, null, null)
        )

        val selectedPaymentMethodUuid = "uuid2"

        everySuspend { paymentRepo.getPaymentMethodList() } returns paymentMethodList
        everySuspend { dataStoreRepo.selectedPaymentMethodUuid } returns MutableStateFlow(
            selectedPaymentMethodUuid
        )

        val result = useCase.invoke()

        val expectedSelectablePaymentMethodList = listOf(
            SelectablePaymentMethod(paymentMethodList[0], false),
            SelectablePaymentMethod(paymentMethodList[1], true),
            SelectablePaymentMethod(paymentMethodList[2], false)
        )

        assertEquals(expectedSelectablePaymentMethodList, result)

        verifySuspend(mode = VerifyMode.atLeast(1)) { paymentRepo.getPaymentMethodList() }
        verifySuspend(mode = VerifyMode.atLeast(1)) { dataStoreRepo.selectedPaymentMethodUuid }
    }

    @Test
    fun `should return list with all not selected elements`() = runTest {
        val paymentMethodList = listOf(
            PaymentMethod("uuid1", PaymentMethodName.CARD, null, null),
            PaymentMethod("uuid2", PaymentMethodName.CASH, null, null),
            PaymentMethod("uuid3", PaymentMethodName.CARD_NUMBER, null, null)
        )

        val selectedPaymentMethodUuid = null

        everySuspend { paymentRepo.getPaymentMethodList() } returns paymentMethodList
        everySuspend { dataStoreRepo.selectedPaymentMethodUuid } returns MutableStateFlow(
            selectedPaymentMethodUuid
        )

        val result = useCase.invoke()

        val expectedSelectablePaymentMethodList = listOf(
            SelectablePaymentMethod(paymentMethodList[0], false),
            SelectablePaymentMethod(paymentMethodList[1], false),
            SelectablePaymentMethod(paymentMethodList[2], false)
        )

        assertEquals(expectedSelectablePaymentMethodList, result)

        verifySuspend(mode = VerifyMode.atLeast(1)) { paymentRepo.getPaymentMethodList() }
        verifySuspend(mode = VerifyMode.atLeast(1)) { dataStoreRepo.selectedPaymentMethodUuid }
    }
}
