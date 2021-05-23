package com.bunbeauty.papakarlo.ui.fragment.profile.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.BottomSheetCitySelectionBinding
import com.bunbeauty.papakarlo.databinding.ElementCityBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.profile.settings.CitySelectionViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet
import kotlinx.coroutines.flow.onEach

class CitySelectionBottomSheet : BaseBottomSheet<BottomSheetCitySelectionBinding>() {

    override val viewModel: CitySelectionViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cityList.onEach { cityList ->
            cityList.onEach { city ->
                ElementCityBinding.inflate(
                    layoutInflater,
                    viewDataBinding.bottomSheetSelectCityLlCityList,
                    true
                ).apply {
                    elementCityTvName.text = city.name
                    root.cardElevation = 0f
                    root.setOnClickListener {
                        viewModel.onCitySelected(city)
                    }
                }
            }
            viewDataBinding.bottomSheetSelectCityLlCityList
        }.startedLaunch()
    }
}