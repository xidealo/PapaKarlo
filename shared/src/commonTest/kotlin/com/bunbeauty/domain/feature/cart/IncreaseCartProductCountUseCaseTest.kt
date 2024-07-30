//package com.bunbeauty.domain.feature.cart
//
//import com.bunbeauty.getCartProduct
//import com.bunbeauty.shared.domain.exeptions.CartProductLimitReachedException
//import com.bunbeauty.shared.domain.exeptions.CartProductNotFoundException
//import com.bunbeauty.shared.domain.feature.cart.GetCartProductCountUseCase
//import com.bunbeauty.shared.domain.feature.cart.IncreaseCartProductCountUseCase
//import com.bunbeauty.shared.domain.repo.CartProductRepo
//import kotlinx.coroutines.test.runTest
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//import kotlin.test.assertFailsWith
//
//class IncreaseCartProductCountUseCaseTest {
//
//    @MockK(relaxed = true)
//    private lateinit var getCartProductCountUseCase: GetCartProductCountUseCase
//
//    @MockK(relaxed = true)
//    private lateinit var cartProductRepo: CartProductRepo
//
//    @InjectMockKs
//    private lateinit var increaseCartProductCountUseCase: IncreaseCartProductCountUseCase
//
//    @BeforeTest
//    fun setup() {
//        MockKAnnotations.init(this)
//    }
//
//    @Test
//    fun `throws CartProductLimitReachedException when cart is full`() = runTest {
//        // Given
//        val cartProductUuid = "cartProductUuid"
//        coEvery {
//            getCartProductCountUseCase()
//        } returns 99
//
//        // When-Then
//        assertFailsWith(CartProductLimitReachedException::class) {
//            increaseCartProductCountUseCase(cartProductUuid = cartProductUuid)
//        }
//        coVerify(exactly = 0) {
//            cartProductRepo.getCartProduct(cartProductUuid)
//        }
//        coVerify(exactly = 0) {
//            cartProductRepo.updateCartProductCount(
//                cartProductUuid = cartProductUuid,
//                count = any()
//            )
//        }
//    }
//
//    @Test
//    fun `throw CartProductNotFoundException when cart product is not found`() = runTest {
//        val cartProductUuid = "cartProductUuid"
//        coEvery { cartProductRepo.getCartProduct(cartProductUuid) } returns null
//
//        assertFailsWith(
//            exceptionClass = CartProductNotFoundException::class,
//            block = {
//                increaseCartProductCountUseCase(cartProductUuid)
//            }
//        )
//    }
//
//    @Test
//    fun `update cart product when cart product is found`() = runTest {
//        val cartProductUuid = "cartProductUuid"
//        val cartProduct = getCartProduct(
//            uuid = cartProductUuid,
//            count = 2
//        )
//        coEvery {
//            cartProductRepo.getCartProduct(cartProductUuid = cartProductUuid)
//        } returns cartProduct
//        coEvery {
//            cartProductRepo.updateCartProductCount(
//                cartProductUuid = cartProductUuid,
//                count = 3
//            )
//        } returns Unit
//
//        increaseCartProductCountUseCase(cartProductUuid = cartProductUuid)
//
//        coVerify(exactly = 1) {
//            cartProductRepo.updateCartProductCount(
//                cartProductUuid = cartProductUuid,
//                count = 3
//            )
//        }
//    }
//}
