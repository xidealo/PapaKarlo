package com.bunbeauty.domain.feature.payment

import com.bunbeauty.getPaymentMethod
import com.bunbeauty.getSelectablePaymentMethod
import com.bunbeauty.shared.domain.feature.payment.GetSelectedPaymentMethodUseCase
import com.bunbeauty.core.model.payment_method.PaymentMethodName
import com.bunbeauty.core.model.payment_method.SelectablePaymentMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetSelectedPaymentMethodUseCaseTest {
    private val useCase = GetSelectedPaymentMethodUseCase()

    @Test
    fun `invoke should return selected payment method when one exists`() {
        // Arrange
        val expectedMethod =
            getPaymentMethod(
                name = PaymentMethodName.CARD,
            )
        val methods =
            listOf(
                SelectablePaymentMethod(
                    paymentMethod =
                        getPaymentMethod(
                            name = PaymentMethodName.CASH,
                        ),
                    false,
                ),
                SelectablePaymentMethod(
                    expectedMethod,
                    true,
                ),
                SelectablePaymentMethod(
                    getPaymentMethod(
                        name = PaymentMethodName.CARD_NUMBER,
                    ),
                    false,
                ),
            )

        // Act
        val result = useCase(methods)

        // Assert
        assertEquals(expectedMethod, result)
    }

    @Test
    fun `invoke should return null when no payment method is selected`() {
        // Arrange
        val methods =
            listOf(
                SelectablePaymentMethod(
                    paymentMethod =
                        getPaymentMethod(
                            name = PaymentMethodName.CASH,
                        ),
                    false,
                ),
                SelectablePaymentMethod(
                    paymentMethod =
                        getPaymentMethod(
                            name = PaymentMethodName.CARD,
                        ),
                    false,
                ),
            )

        // Act
        val result = useCase(methods)

        // Assert
        assertNull(result)
    }

    @Test
    fun `invoke should return first selected method when multiple are selected`() {
        // Arrange
        val expectedMethod =
            getPaymentMethod(
                name = PaymentMethodName.CARD,
            )
        val methods =
            listOf(
                getSelectablePaymentMethod(
                    paymentMethod =
                        getPaymentMethod(
                            name = PaymentMethodName.CASH,
                        ),
                    false,
                ),
                getSelectablePaymentMethod(
                    expectedMethod,
                    true,
                ),
                getSelectablePaymentMethod(
                    paymentMethod =
                        getPaymentMethod(
                            name = PaymentMethodName.CASH,
                        ),
                    true,
                ),
            )

        // Act
        val result = useCase(methods)

        // Assert
        assertEquals(expectedMethod, result)
    }

    @Test
    fun `invoke should return null for empty list`() {
        // Act
        val result = useCase(emptyList())

        // Assert
        assertNull(result)
    }

    @Test
    fun `invoke should handle single item selected`() {
        // Arrange
        val expectedMethod =
            getPaymentMethod(
                name = PaymentMethodName.CARD,
            )

        val methods =
            listOf(
                getSelectablePaymentMethod(expectedMethod, true),
            )

        // Act
        val result = useCase(methods)

        // Assert
        assertEquals(expectedMethod, result)
    }

    @Test
    fun `invoke should handle single item not selected`() {
        // Arrange
        val methods =
            listOf(
                getSelectablePaymentMethod(getPaymentMethod(), false),
            )

        // Act
        val result = useCase(methods)

        // Assert
        assertNull(result)
    }
}
