package com.bunbeauty.papakarlo.presentation.cafe

import com.bunbeauty.domain.model.ui.Cafe
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.model.CafeOptionUI
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import javax.inject.Inject

class CafeOptionsViewModel @Inject constructor(
    private val resourcesProvider: IResourcesProvider,
    private val stringUtil: IStringUtil
) : BaseViewModel() {

    fun getCafeOptions(cafe: Cafe): CafeOptionUI {
        return CafeOptionUI(
            showOnMap = resourcesProvider.getString(R.string.action_cafe_options_show_map) +
                    stringUtil.getCafeAddressString(cafe.cafeAddress),
            callToCafe = resourcesProvider.getString(R.string.action_cafe_options_call) + cafe.phone,
            phone = cafe.phone,
            latitude = cafe.latitude,
            longitude = cafe.longitude
        )
    }

}