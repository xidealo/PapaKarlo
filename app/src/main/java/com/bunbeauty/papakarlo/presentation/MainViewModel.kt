package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.main.IMainInteractor
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cartProductInteractor: ICartProductInteractor,
    private val mainInteractor: IMainInteractor,
    private val stringUtil: IStringUtil,
) : BaseViewModel() {

    private val mutableCartCost: MutableStateFlow<String> = MutableStateFlow("")
    val cartCost: StateFlow<String> = mutableCartCost.asStateFlow()

    private val mutableCartProductCount: MutableStateFlow<String> = MutableStateFlow("")
    val cartProductCount: StateFlow<String> = mutableCartProductCount.asStateFlow()

    init {
        refreshData()
        observeTotalCartCount()
        observeTotalCartCost()
    }

    private fun observeTotalCartCount() {
        cartProductInteractor.observeTotalCartCount().onEach { count ->
            mutableCartProductCount.value = count.toString()
        }.launchIn(viewModelScope)
    }

    private fun observeTotalCartCost() {
        cartProductInteractor.observeNewTotalCartCost().onEach { cost ->
            mutableCartCost.value = stringUtil.getCostString(cost)
        }.launchIn(viewModelScope)
    }

    private fun refreshData() {
        mainInteractor.refreshData()
    }
}