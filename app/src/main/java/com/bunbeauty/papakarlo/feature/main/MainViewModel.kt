package com.bunbeauty.papakarlo.feature.main

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.bottom_bar.NavigationBarItem
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.main.network.INetworkUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val networkUtil: INetworkUtil
) : BaseViewModel(), DefaultLifecycleObserver {

    private val mutableMainState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = mutableMainState.asStateFlow()

    init {
        observeNetworkConnection()
    }

    fun onNavDestinationUpdated(destinationId: Int, navController: NavController) {
        val navigationBarOptions = when (destinationId) {
            R.id.cafeListFragment -> NavigationBarOptions.Visible(
                selectedItem = NavigationBarItem.CAFE_LIST,
                navController = navController
            )
            R.id.menuFragment -> NavigationBarOptions.Visible(
                selectedItem = NavigationBarItem.MENU,
                navController = navController
            )
            R.id.profileFragment -> NavigationBarOptions.Visible(
                selectedItem = NavigationBarItem.PROFILE,
                navController = navController
            )
            else -> NavigationBarOptions.Hidden
        }
        mutableMainState.update { state ->
            state.copy(navigationBarOptions = navigationBarOptions)
        }
    }

    private fun observeNetworkConnection() {
        networkUtil.observeIsOnline().onEach { isOnline ->
            mutableMainState.update { state ->
                state.copy(connectionLost = !isOnline)
            }
        }.launchIn(viewModelScope)
    }
}
