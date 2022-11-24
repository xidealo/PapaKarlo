package com.bunbeauty.papakarlo.feature.profile.screen.payment

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.shared.domain.interactor.payment.PaymentInteractor
import com.bunbeauty.shared.domain.model.Payment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val paymentInteractor: PaymentInteractor
) : BaseViewModel() {

    private val mutablePayment: MutableStateFlow<Payment?> = MutableStateFlow(null)
    val payment: StateFlow<Payment?> = mutablePayment.asStateFlow()

    init {
        getPayment()
    }

    private fun getPayment() {
        viewModelScope.launch {
            mutablePayment.value = paymentInteractor.getPayment()
        }
    }
}
