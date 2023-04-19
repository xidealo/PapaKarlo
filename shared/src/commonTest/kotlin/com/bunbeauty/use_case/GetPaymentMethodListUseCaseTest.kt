package com.bunbeauty.use_case

import com.bunbeauty.shared.domain.feature.payment.GetPaymentMethodListUseCase
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethodName
import com.bunbeauty.shared.domain.repo.PaymentRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetPaymentMethodListUseCaseTest {

    private val paymentRepo: PaymentRepo = mockk()
    private lateinit var getPaymentMethodList: GetPaymentMethodListUseCase

    @BeforeTest
    fun setup() {
        getPaymentMethodList = GetPaymentMethodListUseCase(paymentRepo)
    }

    @Test
    fun `should return non-copyable methods first and then copyable ones`() = runTest {
        // Given
        val cashMethod = PaymentMethod("111", PaymentMethodName.CASH, null, null)
        val cardNumberMethod = PaymentMethod(
            "222",
            PaymentMethodName.CARD_NUMBER,
            "1111 2222 3333 4444",
            "1111222233334444"
        )
        val cardMethod = PaymentMethod("333", PaymentMethodName.CARD, "Карта", null)
        val phoneNumber = PaymentMethod(
            "444",
            PaymentMethodName.PHONE_NUMBER,
            "+7 (900) 111-22-33",
            "+79001112233"
        )
        coEvery { paymentRepo.getPaymentMethodList() } returns listOf(
            cashMethod,
            cardNumberMethod,
            cardMethod,
            phoneNumber
        )

        // When
        val paymentMethodList = getPaymentMethodList()

        // Then
        assertContentEquals(
            listOf(
                cashMethod,
                cardMethod,
                cardNumberMethod,
                phoneNumber,
            ),
            paymentMethodList
        )
    }
}
