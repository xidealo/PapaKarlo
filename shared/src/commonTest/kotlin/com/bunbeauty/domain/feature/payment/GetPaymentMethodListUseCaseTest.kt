package com.bunbeauty.domain.feature.payment

import com.bunbeauty.core.domain.payment.GetPaymentMethodListUseCase
import com.bunbeauty.core.model.payment_method.PaymentMethod
import com.bunbeauty.core.model.payment_method.PaymentMethodName
import com.bunbeauty.core.domain.repo.PaymentRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class GetPaymentMethodListUseCaseTest {
    private val paymentRepo: PaymentRepo = mock()
    private val getPaymentMethodList: GetPaymentMethodListUseCase = GetPaymentMethodListUseCase(paymentRepo)

    @Test
    fun `should return non-copyable methods first and then copyable ones`() =
        runTest {
            // Given
            val cashMethod = PaymentMethod("111", PaymentMethodName.CASH, null, null)
            val cardNumberMethod =
                PaymentMethod(
                    "222",
                    PaymentMethodName.CARD_NUMBER,
                    "1111 2222 3333 4444",
                    "1111222233334444",
                )
            val cardMethod = PaymentMethod("333", PaymentMethodName.CARD, "Карта", null)
            val phoneNumber =
                PaymentMethod(
                    "444",
                    PaymentMethodName.PHONE_NUMBER,
                    "+7 (900) 111-22-33",
                    "+79001112233",
                )
            everySuspend { paymentRepo.getPaymentMethodList() } returns
                listOf(
                    cashMethod,
                    cardNumberMethod,
                    cardMethod,
                    phoneNumber,
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
                paymentMethodList,
            )
        }
}
