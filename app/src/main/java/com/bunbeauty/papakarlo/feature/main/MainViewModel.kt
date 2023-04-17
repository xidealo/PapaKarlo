package com.bunbeauty.papakarlo.feature.main

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.main.network.INetworkUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    private val networkUtil: INetworkUtil
) : BaseViewModel(), DefaultLifecycleObserver {

    private val mutableIsOnline: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = mutableIsOnline.asStateFlow()

    init {
        observeNetworkConnection()
    }

    private fun observeNetworkConnection() {
        networkUtil.observeIsOnline().onEach { isOnline ->
            mutableIsOnline.value = isOnline
        }.launchIn(viewModelScope)
    }
}
