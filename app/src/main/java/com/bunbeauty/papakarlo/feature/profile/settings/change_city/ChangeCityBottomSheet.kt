package com.bunbeauty.papakarlo.feature.profile.settings.change_city

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.databinding.BottomSheetChangeCityBinding
import com.bunbeauty.papakarlo.databinding.ElementCityBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent

class ChangeCityBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_change_city) {

    override val viewModel: ChangeCityViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(BottomSheetChangeCityBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cityList.startedLaunch { cityList ->
            cityList.onEach { city ->
                ElementCityBinding.inflate(
                    layoutInflater,
                    viewBinding.bottomSheetChangeCityLlCityList,
                    true
                ).apply {
                    elementCityTvName.text = city.name
                    root.cardElevation = 0f
                    root.setOnClickListener {
                        viewModel.onCitySelected(city)
                    }
                }
            }
            viewBinding.bottomSheetChangeCityLlCityList
        }
    }
}