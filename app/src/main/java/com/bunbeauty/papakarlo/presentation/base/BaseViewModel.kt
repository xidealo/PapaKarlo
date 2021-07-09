package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bunbeauty.data.mapper.adapter.CartProductAdapterMapper
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    @Inject
    lateinit var cartProductRepo: CartProductRepo

    @Inject
    lateinit var menuProductRepo: MenuProductRepo

    @Inject
    lateinit var router: Router

    val messageSharedFlow = MutableSharedFlow<String>()
    val errorSharedFlow = MutableSharedFlow<String>()

    val cartProductListFlow by lazy {
        cartProductRepo.getCartProductListFlow().map { productList ->
            productList.sortedBy { it.menuProduct.name }
        }
    }

    fun addProductToCart(menuProductUuid: String) = launch(Dispatchers.IO) {
        val menuProduct = menuProductRepo.getMenuProduct(menuProductUuid) ?: return@launch

        val cartProduct = cartProductRepo.getCartProductList().find { cartProduct ->
            cartProduct.menuProduct == menuProduct
        }

        if (cartProduct == null) {
            cartProductRepo.insertToLocal(
                CartProduct(
                    uuid = UUID.randomUUID().toString(),
                    menuProduct = menuProduct
                )
            )
        } else {
            cartProduct.count++
            cartProductRepo.update(cartProduct)
        }
        messageSharedFlow.emit("Вы добавили ${menuProduct.name} в корзину")
    }
}