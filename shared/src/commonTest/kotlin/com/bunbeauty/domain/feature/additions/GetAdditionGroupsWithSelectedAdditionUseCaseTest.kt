//package com.bunbeauty.domain.feature.additions
//
//import com.bunbeauty.getAddition
//import com.bunbeauty.getAdditionGroup
//import com.bunbeauty.shared.domain.feature.addition.GetAdditionGroupsWithSelectedAdditionUseCase
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//class GetAdditionGroupsWithSelectedAdditionUseCaseTest {
//
//    val useCase = GetAdditionGroupsWithSelectedAdditionUseCase()
//
//    @Test
//    fun `should select only one addition in a single-choice group`() {
//        // Given
//        val addition1 = getAddition(
//            uuid = "uuid1",
//            isSelected = true
//        )
//
//        val addition2 = getAddition(
//            uuid = "uuid2",
//            isSelected = false
//        )
//
//        val additionGroup = getAdditionGroup(
//            uuid = "groupUuid",
//            singleChoice = true,
//            additions = listOf(addition1, addition2)
//        )
//
//        // When
//        val result = useCase.invoke(
//            listOf(additionGroup),
//            "groupUuid",
//            "uuid2"
//        )
//
//        // Then
//        assertEquals(false, result.first().additionList[0].isSelected)
//        assertEquals(true, result.first().additionList[1].isSelected)
//    }
//
//    @Test
//    fun `should select addition in a multiple-choice group`() {
//        // Given
//        val addition1 = getAddition(
//            uuid = "uuid1",
//            isSelected = true
//        )
//
//        val addition2 = getAddition(
//            uuid = "uuid2",
//            isSelected = false
//        )
//
//        val additionGroup = getAdditionGroup(
//            uuid = "groupUuid",
//            singleChoice = false,
//            additions = listOf(addition1, addition2)
//        )
//
//        // When
//        val result = useCase.invoke(
//            listOf(additionGroup),
//            "groupUuid",
//            "uuid2"
//        )
//
//        // Then
//        assertEquals(true, result.first().additionList[0].isSelected)
//        assertEquals(true, result.first().additionList[1].isSelected)
//    }
//
//    @Test
//    fun `should not modify other groups`() {
//        // Arrange
//        val addition1 = getAddition(
//            uuid = "uuid1",
//            isSelected = false
//        )
//
//        val additionGroup1 = getAdditionGroup(
//            uuid = "groupUuid1",
//            singleChoice = true,
//            additions = listOf(addition1)
//        )
//
//        val additionGroup2 = getAdditionGroup(
//            uuid = "groupUuid2",
//            singleChoice = false,
//            additions = listOf(addition1)
//        )
//
//        // Act
//        val result = useCase.invoke(
//            listOf(additionGroup1, additionGroup2),
//            "groupUuid1",
//            "uuid1"
//        )
//
//        // Assert
//        assertEquals(true, result.first().additionList[0].isSelected)
//        assertEquals(false, result[1].additionList[0].isSelected)
//    }
//}
