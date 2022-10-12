package com.bunbeauty.papakarlo.feature.main

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.main.IMainInteractor
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.main.network.INetworkUtil
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val cartProductInteractor: ICartProductInteractor,
    private val mainInteractor: IMainInteractor,
    private val stringUtil: IStringUtil,
    private val networkUtil: INetworkUtil
) : BaseViewModel(), DefaultLifecycleObserver {

    private val mutableCartCost: MutableStateFlow<String> = MutableStateFlow("")
    val cartCost: StateFlow<String> = mutableCartCost.asStateFlow()

    private val mutableCartProductCount: MutableStateFlow<String> = MutableStateFlow("")
    val cartProductCount: StateFlow<String> = mutableCartProductCount.asStateFlow()

    private val mutableIsOnline: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = mutableIsOnline.asStateFlow()

    init {
        observeTotalCartCount()
        observeTotalCartCost()
        observeNetworkConnection()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        viewModelScope.launch() {
            mainInteractor.startCheckOrderUpdates()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        viewModelScope.launch() {
            mainInteractor.stopCheckOrderUpdates()
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
}