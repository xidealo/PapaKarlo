package com.bunbeauty.domain.feature.additions

import com.bunbeauty.getAddition
import com.bunbeauty.getAdditionGroup
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class GetAdditionPriorityUseCaseTest : StringSpec({

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