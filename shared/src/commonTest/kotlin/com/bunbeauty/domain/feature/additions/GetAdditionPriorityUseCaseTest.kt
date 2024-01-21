package com.bunbeauty.domain.feature.additions

import com.bunbeauty.getAddition
import com.bunbeauty.getAdditionGroup
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAdditionPriorityUseCaseTest {

    private lateinit var useCase: GetAdditionPriorityUseCase

    @BeforeTest
    fun setup() {
        useCase = GetAdditionPriorityUseCase()
    }

    @Test
    fun `calculate correct priority`() = runTest {
        // Given
        val addition = getAddition(priority = 1)
        val additionGroup = getAdditionGroup(priority = 1)

        // When
        val result = useCase(
            additionGroup = additionGroup,
            addition = addition
        )

        // Then
        assertEquals(11, result)
    }
}