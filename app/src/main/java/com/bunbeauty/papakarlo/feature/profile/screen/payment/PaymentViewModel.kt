package com.bunbeauty.papakarlo.feature.profile.screen.payment

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.shared.domain.feature.payment.GetPaymentInfoUseCase
import com.bunbeauty.shared.domain.interactor.payment.PaymentInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val paymentInteractor: PaymentInteractor,
    getPaymentInfoUseCase: GetPaymentInfoUseCase,
) : BaseViewModel() {

    private val mutablePaymentState: MutableStateFlow<PaymentState> = MutableStateFlow(
        PaymentState(paymentInfo = getPaymentInfoUseCase())
    )
    val paymentState: StateFlow<PaymentState> = mutablePaymentState.asStateFlow()

    init {
        getPayment()
    }

    private fun getPayment() {
        viewModelScope.launch {
            mutablePaymentState.update { state ->
                state.copy(payment = paymentInteractor.getPayment())
            }
        }
    }
}
