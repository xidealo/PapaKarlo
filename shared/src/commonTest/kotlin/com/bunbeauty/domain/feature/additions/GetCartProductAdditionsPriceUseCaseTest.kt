//package com.bunbeauty.domain.feature.additions
//
//import com.bunbeauty.getCartProductAddition
//import com.bunbeauty.shared.domain.feature.addition.GetCartProductAdditionsPriceUseCase
//import kotlinx.coroutines.test.runTest
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//class GetCartProductAdditionsPriceUseCaseTest {
//
//    private lateinit var useCase: GetCartProductAdditionsPriceUseCase
//
//    @BeforeTest
//    fun setup() {
//        useCase = GetCartProductAdditionsPriceUseCase()
//    }
//
//    @Test
//    fun `calculate correct price`() = runTest {
//        // Given
//        val additionList = listOf(
//            getCartProductAddition(price = 10),
//            getCartProductAddition(price = 10),
//            getCartProductAddition(price = null)
//        )
//
//        // When
//        val result = useCase(
//            additionList = additionList
//        )
//
//        // Then
//        assertEquals(20, result)
//    }
//}
