package com.bunbeauty.papakarlo.feature.cafe.cafe_options

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CafeOptionsViewModel @Inject constructor(
    private val resourcesProvider: IResourcesProvider,
    private val cafeInteractor: ICafeInteractor
) : BaseViewModel() {

    private val mutableCafeOptions: MutableStateFlow<CafeOptions?> = MutableStateFlow(null)
    val cafeOptions: StateFlow<CafeOptions?> = mutableCafeOptions.asStateFlow()

    fun getCafe(cafeUuid: String) {
        viewModelScope.launch {
            cafeInteractor.getCafeByUuid(cafeUuid)?.let { cafe ->
                mutableCafeOptions.value = CafeOptions(
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