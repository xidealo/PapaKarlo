package com.bunbeauty.domain.feature.additions

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.bunbeauty.getAddition
import com.bunbeauty.getAdditionGroup
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GetAdditionPriorityUseCaseTests : StringSpec({

    lateinit var useCase: GetAdditionPriorityUseCase

    beforeTest {
        useCase = GetAdditionPriorityUseCase()
    }

    "calculate correct priority" {
        // Given
        val addition = getAddition(priority = 1)
        val additionGroup = getAdditionGroup(priority = 1)

        // When
        val result = useCase(
            additionGroup = additionGroup,
            addition = addition
        )

        // Then
        result shouldBe 11
    }
})

class GetAdditionPriorityUseCaseTest {
    val useCase = GetAdditionPriorityUseCase()

    @Test
    fun testName() {
        val addition = getAddition(priority = 1)
        val additionGroup = getAdditionGroup(priority = 1)

        val result = useCase(
            additionGroup = additionGroup,
            addition = addition
        )

        assertThat(result).isEqualTo(11)
    }
}