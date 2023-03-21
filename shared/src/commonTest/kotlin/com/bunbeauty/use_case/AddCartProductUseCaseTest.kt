import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AddCartProductUseCaseTest {

    private val cartProductRepo: CartProductRepo = mockk()
    private val menuProductUuid = "menu_product_uuid"

    @Test
    fun `invoke should return false when cart is full`() = runBlocking {
        // Given
        val useCase = AddCartProductUseCase(cartProductRepo)
        coEvery { cartProductRepo.getCartProductList() } returns generateCartProducts(CART_PRODUCT_LIMIT)

        // When
        val result = useCase(menuProductUuid)

        // Then
        assertFalse(result)
    }

    @Test
    fun `invoke should save new cart product when product is not in cart`() = runBlocking {
        // Given
        val useCase = AddCartProductUseCase(cartProductRepo)
        coEvery { cartProductRepo.getCartProductList() } returns emptyList()
        coEvery { cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid) } returns null
        coEvery { cartProductRepo.saveAsCartProduct(menuProductUuid) } returns generateCartProduct()

        // When
        val result = useCase(menuProductUuid)

        // Then
        assertTrue(result)
    }

    @Test
    fun `invoke should update cart product count when product is in cart`() = runBlocking {
        // Given
        val useCase = AddCartProductUseCase(cartProductRepo)
        val initialCount = 2
        val initialCartProduct = generateCartProduct(count = initialCount)
        coEvery { cartProductRepo.getCartProductList() } returns listOf(initialCartProduct)
        coEvery { cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid) } returns initialCartProduct
        coEvery { cartProductRepo.updateCartProductCount(initialCartProduct.uuid, initialCount + 1) } returns initialCartProduct.copy(count = initialCount + 1)

        // When
        val result = useCase(menuProductUuid)

        // Then
        assertTrue(result)
    }

    companion object {
        private const val CART_PRODUCT_LIMIT = 99

        private fun generateCartProducts(count: Int): List<CartProduct> {
            return List(count) {
                generateCartProduct(uuid = "cart_uuid_$it")
            }
        }

        private fun generateCartProduct(uuid: String = "cart_uuid", count: Int = 1): CartProduct {
            return CartProduct(uuid, count, mockk())
        }
    }
}
