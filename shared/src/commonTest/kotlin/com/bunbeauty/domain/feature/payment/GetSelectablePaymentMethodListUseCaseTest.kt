package com.bunbeauty.domain.feature.payment

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName
import com.bunbeauty.shared.domain.model.payment_method.SelectablePaymentMethod
import com.bunbeauty.shared.domain.repo.PaymentRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetSelectablePaymentMethodListUseCaseTest {

    private lateinit var useCase: GetSelectablePaymentMethodListUseCase
    private lateinit var paymentRepo: PaymentRepo
    private lateinit var dataStoreRepo: DataStoreRepo

    @BeforeTest
    fun setUp() {
        paymentRepo = mockk()
        dataStoreRepo = mockk()
        useCase = GetSelectablePaymentMethodListUseCase(paymentRepo, dataStoreRepo)
    }

    @Test
    fun `should return selectable payment method list`() = runTest {
        val paymentMethodList = listOf(
            PaymentMethod("uuid1", PaymentMethodName.CARD, null, null),
            PaymentMethod("uuid2", PaymentMethodName.CASH, null, null),
            PaymentMethod("uuid3", PaymentMethodName.CARD_NUMBER, null, null)
        )

        val selectedPaymentMethodUuid = "uuid2"

        coEvery { paymentRepo.getPaymentMethodList() } returns paymentMethodList
        coEvery { dataStoreRepo.selectedPaymentMethodUuid } returns MutableStateFlow(
            selectedPaymentMethodUuid
        )

        val result = useCase.invoke()

        val expectedSelectablePaymentMethodList = listOf(
            SelectablePaymentMethod(paymentMethodList[0], false),
            SelectablePaymentMethod(paymentMethodList[1], true),
            SelectablePaymentMethod(paymentMethodList[2], false)
        )

        assertEquals(expectedSelectablePaymentMethodList, result)

        coVerify(exactly = 1) { paymentRepo.getPaymentMethodList() }
        coVerify(exactly = 1) { dataStoreRepo.selectedPaymentMethodUuid }
    }

    @Test
    fun `should return list with all not selected elements`() = runTest {
        val paymentMethodList = listOf(
            PaymentMethod("uuid1", PaymentMethodName.CARD, null, null),
            PaymentMethod("uuid2", PaymentMethodName.CASH, null, null),
            PaymentMethod("uuid3", PaymentMethodName.CARD_NUMBER, null, null)
        )

        val selectedPaymentMethodUuid = null

        coEvery { paymentRepo.getPaymentMethodList() } returns paymentMethodList
        coEvery { dataStoreRepo.selectedPaymentMethodUuid } returns MutableStateFlow(
            selectedPaymentMethodUuid
        )

        val result = useCase.invoke()

        val expectedSelectablePaymentMethodList = listOf(
            SelectablePaymentMethod(paymentMethodList[0], false),
            SelectablePaymentMethod(paymentMethodList[1], false),
            SelectablePaymentMethod(paymentMethodList[2], false)
        )

        assertEquals(expectedSelectablePaymentMethodList, result)

        coVerify(exactly = 1) { paymentRepo.getPaymentMethodList() }
        coVerify(exactly = 1) { dataStoreRepo.selectedPaymentMethodUuid }
    }
}