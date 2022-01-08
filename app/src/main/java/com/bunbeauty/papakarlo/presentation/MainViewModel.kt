package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.domain.interactor.main.IMainInteractor
import com.bunbeauty.papakarlo.network.INetworkUtil
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cartProductInteractor: ICartProductInteractor,
    private val mainInteractor: IMainInteractor,
    private val stringUtil: IStringUtil,
    private val networkUtil: INetworkUtil
) : BaseViewModel() {

    private val mutableCartCost: MutableStateFlow<String> = MutableStateFlow("")
    val cartCost: StateFlow<String> = mutableCartCost.asStateFlow()

    private val mutableCartProductCount: MutableStateFlow<String> = MutableStateFlow("")
    val cartProductCount: StateFlow<String> = mutableCartProductCount.asStateFlow()

    private val mutableIsOnline: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = mutableIsOnline.asStateFlow()

    init {
        refreshData()
        observeTotalCartCount()
        observeTotalCartCost()
        observeNetworkConnection()
        checkOrderUpdates()
    }

    private fun refreshData() {
        viewModelScope.launch {
            mainInteractor.refreshData()
        }
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

    private fun observeNetworkConnection() {
        networkUtil.observeIsOnline().onEach { isOnline ->
            mutableIsOnline.value = isOnline
        }.launchIn(viewModelScope)
    }

    private fun checkOrderUpdates() {
        mainInteractor.checkOrderUpdates()
    }
}