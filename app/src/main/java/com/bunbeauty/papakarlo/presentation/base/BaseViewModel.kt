package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.Router
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var router: Router

    private val mutableMessage: MutableSharedFlow<String> = MutableSharedFlow(replay = 0)
    val message: SharedFlow<String> = mutableMessage.asSharedFlow()

    private val mutableError: MutableSharedFlow<String> = MutableSharedFlow(replay = 0)
    val error: SharedFlow<String> = mutableError.asSharedFlow()

    protected fun showMessage(message: String) {
        viewModelScope.launch {
            mutableMessage.emit(message)
        }
    }

    protected fun showError(error: String) {
        viewModelScope.launch {
            mutableError.emit(error)
        }
    }
}