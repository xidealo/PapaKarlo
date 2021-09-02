package com.bunbeauty.papakarlo.presentation.cafe

import com.bunbeauty.common.Constants.CAFE_ARG_KEY
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.view_model.base.adapter.CafeItem
import javax.inject.Inject

class CafeOptionsViewModel @Inject constructor(private val resourcesProvider: IResourcesProvider) :
    BaseViewModel() {

    val cafe: CafeItem by lazy {
        checkNotNull(getNavArg(CAFE_ARG_KEY))
    }

    val showOnMap: String by lazy {
        resourcesProvider.getString(R.string.action_cafe_options_show_map) + cafe.address
    }

    val callToCafe: String by lazy {
        resourcesProvider.getString(R.string.action_cafe_options_call) + cafe.phone
    }

    val phone: String by lazy {
        cafe.phone
    }

    val latitude: Double by lazy {
        cafe.latitude
    }

    val longitude: Double by lazy {
        cafe.longitude
    }

}