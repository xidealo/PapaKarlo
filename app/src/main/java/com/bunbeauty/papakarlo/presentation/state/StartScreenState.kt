package com.bunbeauty.papakarlo.presentation.state

import com.bunbeauty.domain.model.City

sealed class StartScreenState {
    object Loading : StartScreenState()
    object NotUpdated : StartScreenState()
    data class CitiesLoaded(val citiList: List<City>) : StartScreenState()
}
