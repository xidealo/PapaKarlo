package com.bunbeauty.papakarlo.presentation.cafe

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.model.CafeOptionUI
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CafeOptionsViewModel @Inject constructor(
    private val resourcesProvider: IResourcesProvider,
    private val cafeInteractor: ICafeInteractor
) : BaseViewModel() {

    private val mutableCafeOptions: MutableStateFlow<CafeOptionUI?> = MutableStateFlow(null)
    val cafeOptions: StateFlow<CafeOptionUI?> = mutableCafeOptions.asStateFlow()

    fun getCafe(cafeUuid: String) {
        viewModelScope.launch {
            cafeInteractor.getCafeByUuid(cafeUuid)?.let { cafe ->
                mutableCafeOptions.value = CafeOptionUI(
                    showOnMap = resourcesProvider.getString(R.string.action_cafe_options_show_map) + cafe.address,
                    callToCafe = resourcesProvider.getString(R.string.action_cafe_options_call) + cafe.phone,
                    phone = cafe.phone,
                    latitude = cafe.latitude,
                    longitude = cafe.longitude
                )
            }
        }
    }
}