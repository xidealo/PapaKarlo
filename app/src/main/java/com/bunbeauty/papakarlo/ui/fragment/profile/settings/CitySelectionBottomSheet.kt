package com.bunbeauty.papakarlo.ui.fragment.profile.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetCitySelectionBinding
import com.bunbeauty.papakarlo.databinding.ElementCityBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.profile.settings.CitySelectionViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet
import kotlinx.coroutines.flow.onEach

class CitySelectionBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_city_selection) {

    override val viewModel: CitySelectionViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(BottomSheetCitySelectionBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cityList.onEach { cityList ->
            cityList.onEach { city ->
                ElementCityBinding.inflate(
                    layoutInflater,
                    viewBinding.bottomSheetSelectCityLlCityList,
                    true
                ).apply {
                    elementCityTvName.text = city.name
                    root.cardElevation = 0f
                    root.setOnClickListener {
                        viewModel.onCitySelected(city)
                    }
                }
            }
            viewBinding.bottomSheetSelectCityLlCityList
        }.startedLaunch()
    }
}