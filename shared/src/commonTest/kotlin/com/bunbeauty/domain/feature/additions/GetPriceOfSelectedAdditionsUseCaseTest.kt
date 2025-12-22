package com.bunbeauty.domain.feature.additions

import com.bunbeauty.getAddition
import com.bunbeauty.shared.domain.feature.addition.GetPriceOfSelectedAdditionsUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

class GetPriceOfSelectedAdditionsUseCaseTest {
    val useCase = GetPriceOfSelectedAdditionsUseCase()

    @Test
    fun `should return zero for empty list of additions`() {
        // When
        val result = useCase.invoke(emptyList())

        // Then
        assertEquals(0, result)
    }

    @Test
    fun `should return zero for non-selected additions`() {
        // Given
        val addition1 =
            getAddition(
                isSelected = false,
                price = 20,
            )

        val addition2 =
            getAddition(
                isSelected = false,
                price = 20,
            )

        // When
        val result = useCase.invoke(listOf(addition1, addition2))

        // Then
        assertEquals(0, result)
    }

    @Test
    fun `should return sum of prices for selected additions`() {
        // Given
        val addition1 =
            getAddition(
                isSelected = true,
                price = 10,
            )
        val addition2 =
            getAddition(
                isSelected = true,
                price = 20,
            )
        val addition3 =
            getAddition(
                isSelected = false,
                price = 30,
            )
        val addition4 =
            getAddition(
                isSelected = true,
                price = null,
            )

        val useCase = GetPriceOfSelectedAdditionsUseCase()

        // When
        val result = useCase.invoke(listOf(addition1, addition2, addition3, addition4))

        // Then
        assertEquals(30, result)
    }
}
