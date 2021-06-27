package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bunbeauty.domain.model.CartProduct
import com.bunbeauty.domain.model.MenuProduct
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.domain.repo.CartProductRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    @Inject
    lateinit var cartProductRepo: CartProductRepo

    @Inject
    lateinit var router: Router

    val messageSharedFlow = MutableSharedFlow<String>()
    val errorSharedFlow = MutableSharedFlow<String>()

    val isCartEmptyLiveData = MutableLiveData(false)

    val cartProductListLiveData by lazy {
        map(cartProductRepo.getCartProductListFlow().asLiveData()) { productList ->
            isCartEmptyLiveData.value = productList.isEmpty()
            productList
        }
    }

    fun addProductToCart(menuProduct: MenuProduct) = launch(Dispatchers.IO) {
        val cartProduct = cartProductRepo.getCartProductList().find { cartProduct ->
            cartProduct.menuProduct == menuProduct
        }

        if (cartProduct == null) {
            cartProductRepo.insertToLocal(CartProduct(menuProduct = menuProduct))
        } else {
            cartProduct.count++
            cartProductRepo.update(cartProduct)
        }
        messageSharedFlow.emit("Вы добавили ${menuProduct.name} в корзину")
    }

}