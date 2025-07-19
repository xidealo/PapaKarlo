package com.bunbeauty.papakarlo.feature.profile.screen.profile

import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.presentation.base.BaseViewState
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class ProfileViewState(
    val lastOrder: LightOrder? = null,
    val state: State,
    val paymentMethodList: ImmutableList<PaymentMethod>,
    val linkList: List<Link>
) : BaseViewState {
    @Immutable
    sealed interface State {
        data object Loading : State
        data object Error : State
        data object Authorized : State
        data object Unauthorized : State
    }
}
