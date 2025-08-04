package com.bunbeauty.domain.feature.order

import com.bunbeauty.shared.domain.feature.order.ExtendedComment
import com.bunbeauty.shared.domain.feature.order.GetExtendedCommentUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

class GetExtendedCommentUseCaseTest {

    private val useCase = GetExtendedCommentUseCase()
    private val rubleCurrency = "₽"

    @Test
    fun `invoke should return just comment when no additional info`() {
        // Arrange
        val extendedComment = ExtendedComment(
            comment = "Test comment",
            change = ExtendedComment.Change(
                paymentByCash = false,
                withoutChangeChecked = false,
                withoutChange = "",
                changeFrom = "",
                change = ""
            ),
            additionalUtensils = ExtendedComment.AdditionalUtensils(
                isAdditionalUtensils = false,
                count = "",
                name = ""
            )
        )

        // Act
        val result = useCase(extendedComment)

        // Assert
        assertEquals("Test comment", result)
    }

    @Test
    fun `invoke should include change info when payment by cash`() {
        // Arrange
        val extendedComment = ExtendedComment(
            comment = "Test comment",
            change = ExtendedComment.Change(
                paymentByCash = true,
                withoutChangeChecked = false,
                withoutChange = "Without change",
                changeFrom = "Сдача с",
                change = "500",
            ),
            additionalUtensils = ExtendedComment.AdditionalUtensils(
                isAdditionalUtensils = false,
                count = "",
                name = ""
            )
        )

        // Act
        val result = useCase(extendedComment)

        // Assert
        assertEquals("Test comment (Сдача с 500 $rubleCurrency)", result)
    }

    @Test
    fun `invoke should include without change when checked`() {
        // Arrange
        val extendedComment = ExtendedComment(
            comment = "Test comment",
            change = ExtendedComment.Change(
                paymentByCash = true,
                withoutChangeChecked = true,
                withoutChange = "Без сдачи",
                changeFrom = "",
                change = "",
            ),
            additionalUtensils = ExtendedComment.AdditionalUtensils(
                isAdditionalUtensils = false,
                count = "",
                name = ""
            )
        )

        // Act
        val result = useCase(extendedComment)

        // Assert
        assertEquals("Test comment (Без сдачи)", result)
    }

    @Test
    fun `invoke should include utensils info when present`() {
        // Arrange
        val extendedComment = ExtendedComment(
            comment = "Test comment",
            change = ExtendedComment.Change(
                paymentByCash = false,
                withoutChangeChecked = false,
                withoutChange = "",
                changeFrom = "",
                change = "",
            ),
            additionalUtensils = ExtendedComment.AdditionalUtensils(
                isAdditionalUtensils = true,
                count = "2",
                name = "Вилки"
            )
        )

        // Act
        val result = useCase(extendedComment)

        // Assert
        assertEquals("Test comment (Вилки 2)", result)
    }

    @Test
    fun `invoke should combine all info when all present`() {
        // Arrange
        val extendedComment = ExtendedComment(
            comment = "Test comment",
            change = ExtendedComment.Change(
                paymentByCash = true,
                withoutChangeChecked = false,
                withoutChange = "Без сдачи",
                changeFrom = "Сдача с",
                change = "1000",
            ),
            additionalUtensils = ExtendedComment.AdditionalUtensils(
                isAdditionalUtensils = true,
                count = "3",
                name = "Ложки"
            )
        )

        // Act
        val result = useCase(extendedComment)

        // Assert
        assertEquals("Test comment (Сдача с 1000 $rubleCurrency) (Ложки 3)", result)
    }

    @Test
    fun `invoke should handle empty comment`() {
        // Arrange
        val extendedComment = ExtendedComment(
            comment = "",
            change = ExtendedComment.Change(
                paymentByCash = true,
                withoutChangeChecked = false,
                withoutChange = "",
                changeFrom = "Сдача с",
                change = "200",
            ),
            additionalUtensils = ExtendedComment.AdditionalUtensils(
                isAdditionalUtensils = true,
                count = "1",
                name = "Нож"
            )
        )

        // Act
        val result = useCase(extendedComment)

        // Assert
        assertEquals("(Сдача с 200 $rubleCurrency) (Нож 1)", result)
    }

    @Test
    fun `invoke should trim whitespace`() {
        // Arrange
        val extendedComment = ExtendedComment(
            comment = "  Test comment  ",
            change = ExtendedComment.Change(
                paymentByCash = false,
                withoutChangeChecked = false,
                withoutChange = "",
                changeFrom = "",
                change = "",
            ),
            additionalUtensils = ExtendedComment.AdditionalUtensils(
                isAdditionalUtensils = false,
                count = "",
                name = ""
            )
        )

        // Act
        val result = useCase(extendedComment)

        // Assert
        assertEquals("Test comment", result)
    }
}