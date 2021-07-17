package com.bunbeauty.papakarlo.presentation.cafe

import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.view_model.base.adapter.CafeAdapterModel
import com.bunbeauty.presentation.view_model.base.adapter.CafeOptionModel
import javax.inject.Inject

class CafeOptionsViewModel @Inject constructor(private val resourcesProvider: IResourcesProvider) :
    BaseViewModel() {

    fun getCafeOptionModel(cafeAdapterModel: CafeAdapterModel): CafeOptionModel {
        return CafeOptionModel(
            address = "${resourcesProvider.getString(R.string.title_cafe_options_show_map)} ${cafeAdapterModel.address}",
            phone = cafeAdapterModel.phone,
            phoneWithText = "${resourcesProvider.getString(R.string.title_cafe_options_call)} ${cafeAdapterModel.phone}",
            coordinate = cafeAdapterModel.coordinate,
        )
    }

}